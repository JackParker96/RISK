package edu.duke.ece651.team14.client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapTextView;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;

public class TextClientPlayer extends ClientPlayer {
  /**
   * Constructor
   * 
   * @param hostName:       hostname of server
   * @param serverPort:     port number if server
   * @param inputSource:    the source to read interactive input, e.g. System.in.
   * @param outPrintStream: e.g. System.out
   * @throws IOException
   */

  /*
  public TextClientPlayer(String hostName, int serverPort, BufferedReader inputSource, PrintStream outPrintStream)
      throws IOException {
    super(hostName, serverPort, inputSource, outPrintStream);
  }
  */

  /**
   * Constructor, which is convenient for mock object.
   */
  public TextClientPlayer(Socket clientSocket, Communicator communicator, BufferedReader inputSource,
      PrintStream outPrintStream) {
    super(clientSocket, communicator, inputSource, outPrintStream);
  }

  /**
   * Let Player know that a Player has won the game
   *
   * @param m is the Map of the game
   * @throw IllegalArgumentException if Map m doesn't have a winner
   */
  public void displayWinInfo(Map m) throws IllegalArgumentException {
    if (m.getWinner() == null) {
      throw new IllegalArgumentException("Error: Nobody has won the game yet");
    }
    sendMsg(m.getWinner() + " has won the game!");
  }

  /**
   * Let this Player know that they have lost the game
   *
   * @param m is the Map of the game
   * @throw IllegalArgumentException is this player hasn't actually lost
   */
  public void displayLossInfo(Map m) throws IllegalArgumentException {
    if (!myPlayer.hasLost(m)) {
      throw new IllegalArgumentException("Error: This player has not lost the game");
    }
    sendMsg(
        "You have lost! You may cotinue to watch the rest of the game, or you may choose to disconnect at any time");
  }

  /**
   * Receive the player object from server to identify the player's color.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void whoAmIPhase() throws IOException, ClassNotFoundException {
    myPlayer = communicator.recvBasicPlayer();
  }

  /**
   * Unit placement phase, receive the empty placement order from server, fill it,
   * and send back to the server.
   * And then it receives the server acknowledge (the updated map state), which
   * indicates this phase finishes.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void placeUnitsPhase() throws IOException, ClassNotFoundException {
    Map m = recvMap();
    displayMap(m);
    out.println("You are the " + myPlayer + " player, please add some units to your territory");
    UnitPlacementOrder upo = m.getUnitsPlacementOrder(myPlayer);
    placeUnits(upo, 30);
    communicator.sendObject(upo);
    // wait for other to finish
    out.println("Wait for other players to finish placing units...");
  }

  /**
   * Displays given map to client output
   *
   * @param m is the map to display
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  protected void displayMap(Map m) throws IOException, ClassNotFoundException {
    MapTextView view = new MapTextView(m);
    out.print(view.displayMap());
  }

  /**
   * Place units on all territory belongs to this player. If exception happens
   * (did not place it correctly)
   * it resets all the placements and starts again.
   * 
   * @param upo
   * @param totalUnits
   * @throws IOException
   */
  protected void placeUnits(UnitPlacementOrder upo, int totalUnits) throws IOException {
    while (true) {
      try {
        int remainingUnits = totalUnits;
        upo.resetUnits();
        for (int i = 0; i < upo.size(); i++) {
          int num_placed = placeOneTerr(upo, remainingUnits, i);
          remainingUnits -= num_placed;
        }
        return;
      } catch (IllegalArgumentException ex) {
        out.println(ex.getMessage());
      }
    }
  }

  /**
   * Read an int, check the num of units is valid or not, if valid, add this to
   * order.
   * 
   * @param upo
   * @param remainingUnits
   * @param index:         the index at
   * @return the unit placed
   * @throws IOException
   */
  protected int placeOneTerr(UnitPlacementOrder upo, int remainingUnits, int index) throws IOException {
    out.println("You have " + remainingUnits + " Units left");
    int inputInt = readInt("Please enter the number of units you want to put in " + upo.getName(index)
        + GetProgressStr(index + 1, upo.size()));
    if (index < upo.size() - 1 && (inputInt >= remainingUnits)) {
      throw new IllegalArgumentException("Make sure all your territories have Units");
    }
    if (index == upo.size() - 1 && inputInt != remainingUnits) {
      throw new IllegalArgumentException("You need to place all units!");
    }
    upo.setNumUnits(index, inputInt);
    return inputInt;
  }

  /**
   * Return string of format (cur/total)
   * 
   * @param cur
   * @param total
   * @return
   */
  protected String GetProgressStr(int cur, int total) {
    return "(" + Integer.toString(cur) + "/" + Integer.toString(total) + ")";
  }

  /**
   * Read an int from the input source
   * 
   * @param prompt
   * @return the read int
   * @throws IOException
   */
  protected int readInt(String prompt) throws IOException {
    while (true) {
      try {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
          throw new EOFException("reach end of file");
        }
        int ans = Integer.parseInt(s);
        if (ans <= 0) {
          throw new IllegalArgumentException("Unit number cannot be less than 1");
        }
        return ans;
      } catch (NumberFormatException ex) {
        out.println("Wrong int format, try again");
      } catch (IllegalArgumentException ex) {
        out.println(ex.getMessage());
      }
    }
  }

  /**
   * For this client, prompt the user to place all move and attack orders, send to
   * server.
   * 
   * @param recv_map
   * @throws IOException
   * @throws ClassNotFoundException
   */
  protected void playOneTurn() throws IOException, ClassNotFoundException {
    Map recv_map = recvMap();
    displayMap(recv_map);
    ArrayList<Order> allOrders = new ArrayList<>();
    if (!this.myPlayer.hasLost(recv_map)) {//has not lost yet
      ClientMoveOrderProcessor moveProc = new ClientMoveOrderProcessor(this, recv_map);
      allOrders.addAll(moveProc.processAllOrdersForOneTurn("MOVE"));
      displayMap(recv_map);
      ClientAttackOrderProcessor attackProc = new ClientAttackOrderProcessor(this, recv_map);
      allOrders.addAll(attackProc.processAllOrdersForOneTurn("ATTACK"));
    }else{
      displayLossInfo(recv_map);
      //TODO:can also choose to disconnect
    }
    this.communicator.sendObject(allOrders);
    out.println("Wait for other players to commit move/attack orders...");
    String oneTurnResult = this.communicator.recvString();
    out.println(oneTurnResult);
  }

  public void playGamePhase() throws IOException, ClassNotFoundException {
    while (true) {
      playOneTurn();
      String game_info = this.communicator.recvString();
      if (game_info.equals("Gameover")) {// one global winner occurs, exit game.
        Map finalMap = recvMap();
        displayMap(finalMap);
        displayWinInfo(finalMap);
        break;
      }
    }
  }
}

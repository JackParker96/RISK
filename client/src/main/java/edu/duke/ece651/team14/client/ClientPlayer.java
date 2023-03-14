package edu.duke.ece651.team14.client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;

public class ClientPlayer {
  final Socket clientSocket;
  final Communicator communicator;
  final BufferedReader inputReader;
  final PrintStream out;
  private Player myPlayer;

  /**
   * Constructor
   * 
   * @param hostName:       hostname of server
   * @param serverPort:     port number if server
   * @param inputSource:    the source to read interactive input, e.g. System.in.
   * @param outPrintStream: e.g. System.out
   * @throws IOException
   */
  public ClientPlayer(String hostName, int serverPort, BufferedReader inputSource, PrintStream outPrintStream)
      throws IOException {
    this.clientSocket = new Socket(hostName, serverPort);
    this.communicator = new Communicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
    this.inputReader = inputSource;
    this.out = outPrintStream;
  }

  /**
   * Constructor, which is convenient for mock object.
   */
  public ClientPlayer(Socket clientSocket, Communicator communicator, BufferedReader inputSource,
      PrintStream outPrintStream) {
    this.clientSocket = clientSocket;
    this.communicator = communicator;
    this.inputReader = inputSource;
    this.out = outPrintStream;
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
    recvDisplayMapView();
    out.println("You are the " + myPlayer + " player, please add some units to your territory");
    UnitPlacementOrder upo = communicator.recvUnitPOrder();
    placeUnits(upo, 30);
    communicator.sendObject(upo);
    // wait for other to finish
    recvDisplayMapView();
  }

  /**
   * Whenever needed, it receives the updates of the server on the states of the
   * game map.
   * And displays the map view to the output printstream.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  protected void recvDisplayMapView() throws IOException, ClassNotFoundException {
    String mapview = communicator.recvString();
    out.println(mapview);
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
    int inputInt = readInt("Please enter the number of units you want to put in " + upo.getName(index));
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
        if(ans<=0){
          throw new IllegalArgumentException("Unit number cannot be less than 1");
        }
        return ans;
      } catch (NumberFormatException ex) {
        out.println("Wrong int format, try again");
      }catch(IllegalArgumentException ex){
        out.println(ex.getMessage());
      }
    }
  }

  /**
   * Release the resources the client holds
   * 
   * @throws IOException
   */
  public void release() throws IOException {
    this.communicator.release();
    this.clientSocket.close();
  }
}

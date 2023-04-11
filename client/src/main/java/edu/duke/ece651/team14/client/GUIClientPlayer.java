package edu.duke.ece651.team14.client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.client.controller.ChooseGameController;
import edu.duke.ece651.team14.client.controller.InitUnitsController;
import edu.duke.ece651.team14.client.controller.LoginController;
import edu.duke.ece651.team14.shared.Account;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapTextView;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIClientPlayer extends ClientPlayer {
  private Stage window;
  private HashMap<Class<?>, Object> controller_initializer;
  GameModel model;

  /**
   * Constructor
   * 
   * @param clientSocket:   client socket
   * @param inputSource:    the source to read interactive input, e.g. System.in.
   * @param outPrintStream: e.g. System.out
   */
  public GUIClientPlayer(GameModel model, Socket clientSocket, Communicator communicator, BufferedReader inputSource,
      PrintStream outPrintStream) {
    super(clientSocket, communicator, inputSource, outPrintStream);
    this.model = model;
  }

  public void setStage(Stage window) throws Exception{
    this.window = window;
    this.controller_initializer = new HashMap<>();
    controller_initializer.put(LoginController.class, new LoginController(this));
    controller_initializer.put(ChooseGameController.class, new ChooseGameController(this));
    controller_initializer.put(InitUnitsController.class, new InitUnitsController(this));
  }

  @Override
  /**
   * Sends messages to GUI Game Log
   *
   * @param msg is the message to send
   */
  public void sendMsg(String msg) {
    model.gameLogText.set(msg + "\n");
  }

  public HashMap<Class<?>, Object> getControllerInitializer(){
    return controller_initializer;
  }

  public boolean login(String username, String password) throws IOException, ClassNotFoundException {
    Account account = new Account(username, password);
    this.communicator.sendObject(account);
    String login_result = this.communicator.recvString();
    sendMsg(login_result);// print to terminal
    if (login_result.equals("Login Success")) {
      return true;
    } else {
      return false;
    }
  }

  public ArrayList<ArrayList<Integer>> getGameChoice() throws Exception{
    ArrayList<Integer> unstarted_games = this.communicator.recvIDs();
    ArrayList<Integer> rejoinable_games = this.communicator.recvIDs();
    ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
    ans.add(unstarted_games);
    ans.add(rejoinable_games);
    return ans;
  }

  public void loginPhase() throws IOException, ClassNotFoundException {
    out.println("login phase!");
    window.setTitle("RISC player");
    URL xmlResource = App.class.getResource("/ui/login.fxml");// note App.class instead of getClass()
    FXMLLoader loader = new FXMLLoader(xmlResource);
    loader.setControllerFactory((c) -> {
      return this.controller_initializer.get(c);
    });
    Parent root = loader.load();
    Scene scene = new Scene(root, 500, 500);
    window.setScene(scene);
    window.show();
  }

  public void sendGameChoice(String choice) throws Exception{
    this.communicator.sendObject(choice);
  }

  //The server make a new communicator when player joins the game, so client need to reset here.
  public void resetCommunicator() throws IOException{
    this.communicator = new Communicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
  }

  public Player getPlayer(){
    return myPlayer;
  }

  public Communicator getCommunicator(){
    return communicator;
  }

  // public void placeUnitsPhase() throws IOException, ClassNotFoundException {
  //   Map m = recvMap();
  //   displayMap(m);
  //   out.println("You are the " + myPlayer.toString().toUpperCase()
  //       + " player. Please add units to your territories. You can add 0 or more units to each territory, as long as you do not exceed the total number allowed.");
  //   UnitPlacementOrder upo = m.getUnitsPlacementOrder(myPlayer);
  //   placeUnits(upo, 30);
  //   communicator.sendObject(upo);
  //   // wait for other to finish
  //   out.println("Wait for other players to finish placing units...\n");
  // }



  //**************************************below are the methods that text client uses*********************//
  /**
   * Ask a player if they want to disconnect from the game
   *
   * @return true if the player indicates they want to disconnect, otherwise
   *         return false
   */
  public boolean wantsToDisconnect() throws IOException {
    sendMsg("Type 'D' to disconnect");
    sendMsg("Type anything else to continue watching the game");
    String response = getInput().toLowerCase();
    if (response.equals("d")) {
      return true;
    }
    return false;
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
        "You have lost! You may continue to watch the rest of the game, or you may choose to disconnect at any time");
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
    out.println("You are the " + myPlayer.toString().toUpperCase()
        + " player. Please add units to your territories. You can add 0 or more units to each territory, as long as you do not exceed the total number allowed.");
    UnitPlacementOrder upo = m.getUnitsPlacementOrder(myPlayer);
    placeUnits(upo, 30);
    communicator.sendObject(upo);
    // wait for other to finish
    out.println("Wait for other players to finish placing units...\n");
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
    //out.print(view.displayMap());
    sendMsg(view.displayMap());
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
    if (inputInt > remainingUnits) {
      throw new IllegalArgumentException("You are placing more units than you were allotted!");
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
    return " (" + Integer.toString(cur) + "/" + Integer.toString(total) + ")";
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
        if (ans < 0) {
          throw new IllegalArgumentException("Unit number cannot be less than 0");
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
  protected boolean playOneTurn() throws IOException, ClassNotFoundException {
    Map recv_map = recvMap();
    displayMap(recv_map);
    ArrayList<Order> allOrders = new ArrayList<>();
    if (!this.myPlayer.hasLost(recv_map)) {// has not lost yet
      ClientMoveOrderProcessor moveProc = new ClientMoveOrderProcessor(this, recv_map);
      allOrders.addAll(moveProc.processAllOrdersForOneTurn("MOVE"));
      ClientAttackOrderProcessor attackProc = new ClientAttackOrderProcessor(this, recv_map);
      allOrders.addAll(attackProc.processAllOrdersForOneTurn("ATTACK"));
    } else {
      displayLossInfo(recv_map);
      boolean decision = wantsToDisconnect();
      if (decision) {
        return false;// want to exit
      }
    }
    this.communicator.sendObject(allOrders);
    out.println("Wait for other players to commit move/attack orders...");
    String oneTurnResult = this.communicator.recvString();
    out.println(oneTurnResult);
    return true;// means continue
  }

  public void playGamePhase() throws IOException, ClassNotFoundException {
    while (true) {
      boolean continueGame = playOneTurn();
      if (!continueGame) {
        break;
      }
      String game_info = this.communicator.recvString();
      if (game_info.equals("Gameover")) {// one global winner occurs, exit game.
        Map finalMap = recvMap();
        displayMap(finalMap);
        displayWinInfo(finalMap);
        break;
      }
    }
  }

  // Note: no error checking here.
  public String joinGamePhase() throws IOException, ClassNotFoundException {
    ArrayList<Integer> unstarted_games = this.communicator.recvIDs();
    ArrayList<Integer> rejoinable_games = this.communicator.recvIDs();
    String conn_choice = "";
    while (true) {
      conn_choice = getGameConnectionChoice();
      if (conn_choice.equals("1")) {// reconnect
        sendMsg("You can rejoin games:\n" + displayIDs(rejoinable_games));
        if (rejoinable_games.size() == 0) {
          continue;
        }
        break;
      } else if (conn_choice.equals("2")) {
        sendMsg("You can create a new game by input 0[x], x is the number of players of this game");
        sendMsg("You can join games:\n" + displayIDs(unstarted_games));
        break;
      }
    }
    String game_choice = getInput();
    this.communicator.sendObject(game_choice);
    return conn_choice;
  }

  protected String displayIDs(ArrayList<Integer> ids) {
    StringBuilder sb = new StringBuilder();
    if (ids.size() == 0) {
      sb.append("Not Available");
    } else {
      for (int i : ids) {
        sb.append(i + "\n");
      }
    }
    return sb.toString();
  }

  // Note: no error checking here.
  protected String getGameConnectionChoice() throws IOException {
    sendMsg("Type 1 to reconnect to a game or Type 2 to join/create a game");
    String choice = getInput();
    return choice;
  }

  public void PlayGame() throws IOException, ClassNotFoundException {
    loginPhase();
    String choice = joinGamePhase();
    // the server create a new communicator in the game, so reset it here also.
    this.communicator = new Communicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
    if (choice.equals("2")) {// play a new game.
      whoAmIPhase();
      placeUnitsPhase();
      playGamePhase();
    } else if (choice.equals("1")) {
      whoAmIPhase();
      playGamePhase();
    }
  }
}

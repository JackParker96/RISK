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
  public TextClientPlayer(String hostName, int serverPort, BufferedReader inputSource, PrintStream outPrintStream)
      throws IOException {
    super(hostName, serverPort, inputSource, outPrintStream);
  }

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

  // ALL CODE IN THIS SECTION HAS BEEN RETIRED - OLD IMPLEMENTATION FOR PROCESSING
  // MOVE ORDERS

  /**
   * Prompt the user to enter the name of a territory on the map, then return that
   * territory if it exists. The user will be prompted repeatedly until they enter
   * a valid territory
   *
   * @param prompt is the prompt to display to the player
   * @param m      is the Map you want the player to select a territory from
   * @return the Territory owned by the player
   *         public Territory askForTerritory(String prompt, Map m) throws
   *         IOException {
   *         while (true) {
   *         out.println(prompt);
   *         String terrName = inputReader.readLine();
   *         try {
   *         Territory t = m.getTerritoryByName(terrName);
   *         return t;
   *         } catch (IllegalArgumentException e) {
   *         out.println(e.getMessage());
   *         continue;
   *         }
   *         }
   *         }
   * 
   *         Prompt the user to enter the name of a territory that is (a) on the
   *         map and
   *         (b) owned by the player. The user will be prompted repeatedly until
   *         they
   *         enter a valid territory
   *
   * @param prompt is the prompt to display to the player
   * @param m      is the map you want the p[layer to select a territory from
   * @return the Territory owned by the player
   * 
   *         public Territory askForTerritoryOwnedByPlayer(String prompt, Map m)
   *         throws IOException {
   *         while (true) {
   *         Territory t = askForTerritory(prompt, m);
   *         if (t.getOwner().equals(myPlayer)) {
   *         return t;
   *         }
   *         out.println("You do not own that territory");
   *         }
   *         }
   * 
   *         Prompt the user for the type of order they would like to place (move,
   *         attack,
   *         etc)
   *
   * @return the String "move", "attack", or "commit"
   * 
   *         public String getOrderType() throws IOException {
   *         while (true) {
   *         out.println("Enter order type ('move', 'attack', or 'commit' - to
   *         stop entering orders for this turn):");
   *         String orderType = inputReader.readLine().toLowerCase();
   *         if ((!orderType.equals("move")) && (!orderType.equals("attack")) &&
   *         (!orderType.equals("commit"))) {
   *         out.println("Invalid move type");
   *         continue;
   *         }
   *         return orderType;
   *         }
   *         }
   * 
   *         Prompt the user for the number of units they want to send to another
   *         territory as part of a move or attack order
   *
   * @param fromTerr is the territory the Player wants to send units from
   * @return the number of units the player wants to send (if it's a valid number)
   * @throws IllegalArgumentException if fromTerr is not owned by
   *                                  TextClientPlayer.myPlayer
   * 
   *                                  public int getNumUnitsToSend(Territory
   *                                  fromTerr) throws IOException {
   *                                  // Check that fromTerr is owned by the
   *                                  Player
   *                                  if (!fromTerr.getOwner().equals(myPlayer)) {
   *                                  throw new IllegalArgumentException("You do
   *                                  not own that territory");
   *                                  }
   *                                  int maxCanSend = fromTerr.getNumUnits();
   *                                  while (true) {
   *                                  out.println("Enter the number of units you
   *                                  want to send");
   *                                  String strNum = inputReader.readLine();
   *                                  int numToSend;
   *                                  // Try converting strNum into an int using
   *                                  the parseInt method of class Integer
   *                                  try {
   *                                  numToSend = Integer.parseInt(strNum);
   *                                  } catch (NumberFormatException e) {
   *                                  out.println("Please enter a valid number");
   *                                  continue;
   *                                  }
   *                                  // Check that at least one unit is being
   *                                  sent
   *                                  if (numToSend < 1) {
   *                                  out.println("You must send at least one
   *                                  unit");
   *                                  continue;
   *                                  }
   *                                  // Check that not too many units are being
   *                                  sent
   *                                  if (numToSend > maxCanSend) {
   *                                  out.println("You're trying to send more
   *                                  units than you have");
   *                                  continue;
   *                                  }
   *                                  return numToSend;
   *                                  }
   *                                  }
   * 
   * 
   *                                  Prompt user to enter information to
   *                                  construct a MoveOrder
   *                                  Checks that four conditions are met:
   *                                  (1) Player owns territory to send units from
   *                                  (2) Player owns territory to send units to
   *                                  (3) Path exists connecting origin and
   *                                  destination
   *                                  (4) Number of units to send does not exceed
   *                                  number of units on origin
   *                                  territory
   *
   * @param m       is the map at the end of the previous turn
   * @param checker is used to check that a path exists between the two
   *                territories
   * @return the MoveOrder constructed based on player's responses to prompts (if
   *         the MoveOrder meets the four conditions above). Otherwise, return
   *         null
   * 
   *         public MoveOrder tryCommitMoveOrder(Map m,
   *         MoveOrderPathExistsRuleChecker checker) throws IOException {
   *         String originPrompt = "Territory to move units from:";
   *         String destPrompt = "Territory to move units to:";
   *         Territory origin = askForTerritoryOwnedByPlayer(originPrompt, m);
   *         Territory dest = askForTerritoryOwnedByPlayer(destPrompt, m);
   *         // The following method call automatically checks that the player
   *         isn't trying
   *         // to send more units than were in their territory at the end of the
   *         previous
   *         // turn
   *         int numUnits = getNumUnitsToSend(origin);
   *         MoveOrder order = new MoveOrder(origin, dest, numUnits, myPlayer);
   *         String check = checker.checkMyRule(m, order);
   *         if (check == null) {
   *         return order;
   *         }
   *         return null;
   *         }
   * 
   * 
   *         public AttackOrder tryCommitAttackOrder(Map m, ) throws IOException {
   *         String originPrompt = "Territory to attack from:";
   *         String destPrompt = "Territory you want to attack:";
   *         Territory origin = askForTerritoryOwnedByPlayer(originPrompt, m);
   *         // Keep prompting player for destination territory until they enter a
   *         territory that is
   *         while (true) {
   *         Territory dest = askForTerritoryNotOwnedByPlayer(destPrompt, m);
   *         if (!origin.getAdjacentTerritories().contains(dest)) {
   * 
   *         }
   *         }
   *         }
   * 
   * 
   *         Walk player through creating a single move order.
   *
   *         In order for a MoveOrder to be returned by this method, the following
   *         5
   *         conditions must be met:
   *
   *         (1) Player owns territory to send units from
   *         (2) Player owns territory to send units to
   *         (3) Path exists connecting origin and destination
   *         (4) Number of units to send does not exceed number of units on origin
   *         territory
   *         (5) Considering all the move orders the player has placed this turn,
   *         the sum
   *         of units moved out of any territory does not exceed the number of
   *         units on
   *         that territory at the end of the previous turn
   *
   *         If any of the above 5 conditions are not met, the player is
   *         immediately
   *         prompted to correct their mistake
   *
   * @param m        is the current game map
   * @param verifier is used to check condition #5 (see documentation for the
   *                 OrderVerifier class)
   * @return a MoveOrder object constructed based on the player's responses to
   *         prompts
   * 
   *         public MoveOrder getMoveOrder(Map m, OrderVerifier verifier) throws
   *         IOException {
   *         out.println(
   *         "Type 'D' if you're done committing move orders for this turn. Type
   *         anything else to begin creating a new move order");
   *         String response = inputReader.readLine().toLowerCase();
   *         if (response.equals("d")) {
   *         return null;
   *         }
   *         while (true) {
   *         MoveOrder order = tryCommitMoveOrder(m, new
   *         MoveOrderPathExistsRuleChecker(null));
   *         // order will be null if and only if the path check fails
   *         if (order == null) {
   *         out.println("There is no valid path between origin and destination.
   *         Try again.");
   *         continue;
   *         }
   *         // Check condition #5 from the description of this method
   *         String checkResult = verifier.verifyOrder(order);
   *         return order;
   *         }
   *         }
   * 
   *         public AttackOrder getAttackOrder(Map m, OrderVerifier verifier)
   *         throws IOException {
   *         out.println(
   *         "Type 'D' if you're done committing attack orders for this turn. Type
   *         anything else to begin creating a new attack order");
   *         String response = inputReader.readLine().toLowerCase();
   *         if (response.equals("d")) {
   *         return null;
   *         }
   *         while (true) {
   *         AttackOrder order = tryCommitAttackOrder(m);
   *         if (order == null) {
   *         out.println("You can only attack a territory if it's adjecent to the
   *         territory you're attacking from");
   *         continue;
   *         }
   *         // Check condition #5 from the description of this method
   *         String checkResult = verifier.verifyOrder(order);
   *         return order;
   *         }
   *         }
   * 
   *         Get all the move orders from one player for one turn
   *
   * @param m        is the map returned by the server at the end of the previous
   *                 turn
   * @param verifier isn OrderVerifier used to make sure players aren't sending
   *                 too many units out of a territory over the course of a whole
   *                 turn
   * @return an ArrayList of verified MoveOrders that are all ready to be sent
   *         over to the Server
   *         public ArrayList<MoveOrder> getAllMoveOrders(Map m, OrderVerifier
   *         verifier) throws IOException {
   *         out.println("Time to place move orders! You can enter as many move
   *         orders as you'd like");
   *         ArrayList<MoveOrder> verifiedOrders = new ArrayList<>();
   *         while (true) {
   *         MoveOrder verifiedOrder = getMoveOrder(m, verifier);
   *         if (verifiedOrder == null) {
   *         break;
   *         }
   *         verifiedOrders.add(verifiedOrder);
   *         }
   *         return verifiedOrders;
   *         }
   * 
   * 
   *         public ArrayList<AttackOrder> getAllAttackOrders(Map m, OrderVerifier
   *         verifier) throws IOException {
   *         out.println("Time to place attack orders! You can enter as many
   *         attack orders as you'd like");
   *         ArrayList<AttackOrder> verifiedOrders = new ArrayList<>();
   *         while (true) {
   *         AttackOrder verifiedOrder = getAttackOrder(m, verifier);
   *         if (verifiedOrder == null) {
   *         break;
   *         }
   *         verifiedOrders.add(verifiedOrder);
   *         }
   *         return verifiedOrders;
   *         }
   * 
   * 
   * 
   *         Execute the entire Move Orders phase for one player
   *         Prompt user for move orders, verify all the orders on the client
   *         side, and
   *         send orders to server
   *
   * @param m        is the game map
   * @param verifier is an OrderVerifier
   * 
   *                 public void doMoveOrdersPhase(Map m, OrderVerifier verifier)
   *                 throws IOException {
   *                 ArrayList<MoveOrder> moveOrders = getAllMoveOrders(m,
   *                 verifier);
   *                 communicator.sendObject(moveOrders);
   *                 }
   * 
   */

}

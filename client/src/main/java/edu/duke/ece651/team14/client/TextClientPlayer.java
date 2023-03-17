package edu.duke.ece651.team14.client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapTextView;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.BasicTerritory;

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
    displayMap(recvMap());
    out.println("You are the " + myPlayer + " player, please add some units to your territory");
    UnitPlacementOrder upo = communicator.recvUnitPOrder();
    placeUnits(upo, 30);
    communicator.sendObject(upo);
    // wait for other to finish
    displayMap(recvMap());
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
   * Prompt the user to enter the name of a territory on the map, then return that
   * territory if it exists. The user will be prompted repeatedly until they enter
   * a valid territory
   *
   * @param prompt is the prompt to display to the player
   * @param m      is the Map you want the player to select a territory from
   * @return the Territory owned by the player
   */
  public Territory askForTerritory(String prompt, Map m) throws IOException {
    while (true) {
      out.println(prompt);
      String terrName = inputReader.readLine();
      try {
        Territory t = m.getTerritoryByName(terrName);
        return t;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        continue;
      }
    }
  }

  /**
   * Prompt the user to enter the name of a territory that is (a) on the map and
   * (b) owned by the player. The user will be prompted repeatedly until they
   * enter a valid territory
   *
   * @param prompt is the prompt to display to the player
   * @param m      is the map you want the p[layer to select a territory from
   * @return the Territory owned by the player
   */
  public Territory askForTerritoryOwnedByPlayer(String prompt, Map m) throws IOException {
    while (true) {
      Territory t = askForTerritory(prompt, m);
      if (t.getOwner().equals(myPlayer)) {
        return t;
      }
      out.println("You do not own that territory");
    }
  }

  /**
   * Prompt the user for the type of order they would like to place (move, attack,
   * etc)
   *
   * @return the String "move", "attack", or "commit"
   */
  public String getOrderType() throws IOException {
    while (true) {
      out.println("Enter order type ('move', 'attack', or 'commit' - to stop entering orders for this turn):");
      String orderType = inputReader.readLine().toLowerCase();
      if ((!orderType.equals("move")) && (!orderType.equals("attack")) && (!orderType.equals("commit"))) {
        out.println("Invalid move type");
        continue;
      }
      return orderType;
    }
  }

  /**
   * Prompt the user for the number of units they want to send to another
   * territory as part of a move or attack order
   *
   * @param fromTerr is the territory the Player wants to send units from
   * @return the number of units the player wants to send (if it's a valid number)
   */
  public int getNumUnitsToSend(Territory fromTerr) throws IOException {
    // Check that fromTerr is owned by the Player
    if (!fromTerr.getOwner().equals(myPlayer)) {
      throw new IllegalArgumentException(
          "Player (" + myPlayer.toString() + ") does not own (" + fromTerr.toString() + ")");
    }
    int maxCanSend = fromTerr.getNumUnits();
    while (true) {
      out.println("Enter the number of units you want to send");
      String strNum = inputReader.readLine();
      int numToSend;
      // Try converting strNum into an int using the parseInt method of class Integer
      try {
        numToSend = Integer.parseInt(strNum);
      } catch (NumberFormatException e) {
        out.println("Please enter a valid number");
        continue;
      }
      // Check that at least one unit is being sent
      if (numToSend < 1) {
        out.println("You must send at least one unit");
        continue;
      }
      // Check that not too many units are being sent
      if (numToSend > maxCanSend) {
        out.println("You have " + maxCanSend + " units on " + fromTerr.toString() + " - You cannot send " + numToSend
            + " units");
        continue;
      }
      return numToSend;
    }
  }

  /**
   * Prompt the user to place an order to the server
   *
   * @param m is the Map associated with the current game
   * @return an Order object that another function can send to the server
   */
  public Order getOrder(Map m) throws IOException {
    out.println("You will now enter an Order");
    String orderType = getOrderType();
    if (orderType.equals("commit")) {
      return null;
    }
    String fromPrompt = "Enter the name of the 'from' territory for your order";
    Territory fromTerr = askForTerritoryOwnedByPlayer(fromPrompt, m);
    String toPrompt = "Enter the name of the 'to' territory for your order";
    Territory toTerr = askForTerritoryOwnedByPlayer(toPrompt, m);

    return null;
  }

}

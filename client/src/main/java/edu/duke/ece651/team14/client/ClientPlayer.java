package edu.duke.ece651.team14.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;

public abstract class ClientPlayer {
  final Socket clientSocket;
  final Communicator communicator;
  final BufferedReader inputReader;
  final PrintStream out;
  protected Player myPlayer;

  /**
   * Constructor
   * 
   * @param hostName:       name of host server
   * @param serverPort:     port number of host server
   * @param inputSource:    the source to read interactive input, e.g. System.in.
   * @param outPrintStream: e.g. System.out
   * @throws IOException
   */
  /*
  public ClientPlayer(String hostName, int serverPort, BufferedReader inputSource, PrintStream outPrintStream)
      throws IOException {
    this.clientSocket = new Socket(hostName, serverPort);
    this.communicator = new Communicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
    this.inputReader = inputSource;
    this.out = outPrintStream;
  }
  */

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
   * Read one line of input for the user
   */
  public String getInput() throws IOException {
    return inputReader.readLine();
  }
  
  /**
   * Display a message to the client
   *
   * @param msg is the message to display
   */
  public void sendMsg(String msg) {
    out.println(msg);
  }
  
  /**
   * Receive the player object from server to identify the player's color.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public abstract void whoAmIPhase() throws IOException, ClassNotFoundException;

  /**
   * Unit placement phase, receive the empty placement order from server, fill it,
   * and send back to the server.
   * And then it receives the server acknowledge (the updated map state), which
   * indicates this phase finishes.
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public abstract void placeUnitsPhase() throws IOException, ClassNotFoundException;

  /**
   * Returns map from communicator
   *
   * @throws IOException
   * @throws ClassNotFoundException
   *
   * @return the map
   */
  protected Map recvMap() throws IOException, ClassNotFoundException {
    return communicator.recvMap();
  }

  /**
   * Displays given map to client output
   *
   * @param m is the map to display
   * 
   * @throws IOException
   * @throws ClassNotFoundException
   */
  protected abstract void displayMap(Map m) throws IOException, ClassNotFoundException;

  /**
   * Place units on all territory belongs to this player. If exception happens
   * (did not place it correctly)
   * it resets all the placements and starts again.
   * 
   * @param upo
   * @param totalUnits
   * @throws IOException
   */
  protected abstract void placeUnits(UnitPlacementOrder upo, int totalUnits) throws IOException;

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
  protected abstract int placeOneTerr(UnitPlacementOrder upo, int remainingUnits, int index) throws IOException;

  /**
   * Read an int from the input source
   * 
   * @param prompt
   * @return the read int
   * @throws IOException
   */
  protected abstract int readInt(String prompt) throws IOException;

  //public abstract Order getOrder();

  //public abstract ArrayList<Order> getAllOrders();

  //public abstract void sendOrders();

  //public abstract void doOneTurn();

  protected abstract boolean playOneTurn() throws IOException, ClassNotFoundException;

  public abstract void playGamePhase() throws IOException, ClassNotFoundException;
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

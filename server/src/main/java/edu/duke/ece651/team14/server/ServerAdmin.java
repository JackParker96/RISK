package edu.duke.ece651.team14.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.DiceResolver;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;

public class ServerAdmin {
  ServerSocket serverSocket;
  ArrayList<Socket> clientSockets;
  HashMap<Player, Communicator> playerCommunicators;
  Map map;

  private static final HashMap<Integer, String> colorMap = new HashMap<>();
  static {
    colorMap.put(0, "red");
    colorMap.put(1, "green");
    colorMap.put(2, "blue");
    colorMap.put(3, "yellow");
  }

  /**
   * Constructor
   *
   * @param portNum: server port number
   * @throws IOException
   */
  public ServerAdmin(int portNum) throws IOException {
    this.serverSocket = new ServerSocket(portNum);
    this.clientSockets = new ArrayList<Socket>();
    this.playerCommunicators = new HashMap<Player, Communicator>();
  }

  /**
   * Constructor for mock object
   */
  public ServerAdmin(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    this.clientSockets = new ArrayList<Socket>();
    this.playerCommunicators = new HashMap<Player, Communicator>();
  }

  /**
   * Server accepts all the connections specified by num_players
   * 
   * @param num_players: total num of connections
   * @throws IOException
   */
  public void acceptPlayersPhase(int num_players) throws IOException {
    int acceptedPlayers = 0;
    while (acceptedPlayers < num_players) {
      Socket clientSocket = serverSocket.accept();
      clientSockets.add(clientSocket);
      OutputStream out = clientSocket.getOutputStream();
      InputStream in = clientSocket.getInputStream();
      Communicator clientCommunicator = new Communicator(out, in);
      Player p = new BasicPlayer(new Color(colorMap.get(acceptedPlayers)), colorMap.get(acceptedPlayers));
      playerCommunicators.put(p, clientCommunicator);
      System.out.println("Accepted player connection: " + p);
      acceptedPlayers++;
    }
  }

  /**
   * Server initializes game and sends empty to players to recieve orders
   *
   * @throws IOException, ClassNotFoundException
   */
  public void initializeGamePhase() throws IOException, ClassNotFoundException {
    MapFactory f = new MapFactory();
    //this.map = f.makeMap("Earth", new ArrayList<Player>(this.playerCommunicators.keySet()));   // actual map
    this.map = f.makeMap("test", new ArrayList<Player>(this.playerCommunicators.keySet()));  // test map
    for (Player p : playerCommunicators.keySet()) {
      Communicator c = playerCommunicators.get(p);
      c.sendObject(p);
      c.sendObject(map);
    }
    receivePlacementOrders();
  }

  /**
   * Collects initial unit placements from players
   *
   * @throws IOException, ClassNotFoundException
   */
  protected void receivePlacementOrders() throws IOException, ClassNotFoundException {
    for (Player p : playerCommunicators.keySet()) {
      Communicator c = playerCommunicators.get(p);
      UnitPlacementOrder upo = c.recvUnitPOrder();
      System.out.println("recv unit placement request from " + p);
      map.handleUnitPlacementOrder(upo);
    }
  }

  /** 
   * Runs through all stages of the game
   *
   * @throws IOException, ClassNotFoundException
   */
  public void playGamePhase() throws IOException, ClassNotFoundException {
    while (true) {
      executeOneTurn();
      Player winner = this.map.getWinner();
      if (winner != null) {
        sendResults("Gameover");
        sendMap();
        break; 
      } else {
        sendResults("Continue");
      }
    }
  }

  /**
   * Executes one round of turns in the game
   *
   * @param playerCommunicators is the HashMap of communicators
   * @param map                 is the game map
   *
   * @throws IOException, ClassNotFoundException
   */
  protected void executeOneTurn()
      throws IOException, ClassNotFoundException {
    sendMap();
    HashMap<String, ArrayList<Order>> orders = receiveAllOrders();
    // resolve move
    ServerMoveResolver smr = new ServerMoveResolver(map);
    smr.resolveAllMoveOrders(orders.get("move"));
    // resolve attack
    ServerAttackOrderResolver sar = new ServerAttackOrderResolver(map, new DiceResolver());
    String results = sar.resolveAllAttackOrders(orders.get("attack"));
    sendResults(results);
    this.map.allAddOneUnit();
  }

  /**
   * Send the results of one turn to all players, can be any string to notify the
   * clients
   * 
   * @param results
   * @throws IOException
   */
  protected void sendResults(String results) throws IOException {
    for (Communicator c : playerCommunicators.values()) {
      c.sendObject(results);
    }
  }

  /**
   * Sends the given map through every given Communicator
   *
   * @throws IOException
   */
  protected void sendMap() throws IOException {
    for (Communicator c : playerCommunicators.values()) {
      c.sendObject(map);
    }
  }

  /**
   * Receives orders from every player for a given turn
   *
   *
   * @throw IOException, ClassNotFoundException
   *
   * @return a HashMap of the orders
   */
  protected HashMap<String, ArrayList<Order>> receiveAllOrders() throws IOException, ClassNotFoundException {
    ArrayList<Order> allOrders = new ArrayList<>();
    for (Player p : playerCommunicators.keySet()) {
      Communicator c = playerCommunicators.get(p);
      allOrders.addAll(c.recvOrders());
    }
    System.out.println("receiving all orders for one turn");
    return sortOrders(allOrders);
  }

  /**
   * Takes in an ArrayList of orders and return a HashMap of the orders arranged
   * by order type
   *
   * @param orders is the list of Orders
   *
   * @return the HashMap of Orders arranged by order type
   */
  protected HashMap<String, ArrayList<Order>> sortOrders(ArrayList<Order> orders) {
    HashMap<String, ArrayList<Order>> sortedOrders = new HashMap<>();
    sortedOrders.put("move", new ArrayList<Order>());
    sortedOrders.put("attack", new ArrayList<Order>());
    for (Order o : orders) {
      sortedOrders.get(o.getOrderType()).add(o);
    }
    return sortedOrders;
  }

  /**
   * Release all the resources of this ServerAdmin
   * 
   * @throws IOException
   */
  public void releaseResources() throws IOException {
    for (Communicator c : playerCommunicators.values()) {
      c.release();
    }
    for (Socket s : clientSockets) {
      s.close();
    }
    this.serverSocket.close();
  }

}

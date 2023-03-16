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
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.AttackOrder;
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
  public ServerAdmin(ServerSocket serverSocket, InputStream input, OutputStream output) {
    this.serverSocket = serverSocket;
    this.clientSockets = new ArrayList<Socket>();
    this.playerCommunicators = new HashMap<Player, Communicator>();
  }

  /**
   * Server accept all the connections specified by num_players
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
      // debug message:
      System.out.println("Accepted player #" + acceptedPlayers);
      acceptedPlayers++;
    }
  }

  public void InitializeGamePhase() throws IOException, ClassNotFoundException {
    // System.out.println(view.displayMap());
    // send the player object to client
    for (Player p : playerCommunicators.keySet()) {
      Communicator c = playerCommunicators.get(p);
      c.sendObject(p);
      c.sendObject(map);
      // TODO: change this, doesn't need to send empty placement order
      c.sendObject(map.getUnitsPlacementOrder(p));
    }
    receivePlacementOrders();
  }

  public void receivePlacementOrders() throws IOException, ClassNotFoundException {
    for (Player p : playerCommunicators.keySet()) {
      Communicator c = playerCommunicators.get(p);
      UnitPlacementOrder upo = (UnitPlacementOrder) c.recvObject();
      // might need rule checker here, may not, clients have do a lot rule checking
      System.out.println("recv unit placement request from " + p);
      map.handleUnitPlacementOrder(upo);
    }
    // all placement order received
    for (Player p : playerCommunicators.keySet()) {
      Communicator c = playerCommunicators.get(p);
      System.out.println("send placed map to " + p);
      c.sendObject(map);
    }
  }

  public HashMap<String, ArrayList<Order>> receiveOrdersFromOnePlayer(Player p) throws IOException, ClassNotFoundException {
    Communicator c = playerCommunicators.get(p);
    c.sendObject("Init order phase");
    return sortOrders(c.recvOrders());
  }

  public HashMap<String, ArrayList<Order>> sortOrders(ArrayList<Order> orders) {
    HashMap<String, ArrayList<Order>> sortedOrders = new HashMap<>();
    sortedOrders.put("move", new ArrayList<Order>());
    sortedOrders.put("attack", new ArrayList<Order>());
    for (Order o : orders) {
      sortedOrders.get(o.getOrderType()).add(o);
    }
    return sortedOrders;
  }

  /**
   * Close server socket
   *
   * @throws IOException
   */
  public void closeServer() throws IOException {
    serverSocket.close();
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
    closeServer();
  }

  

}

package edu.duke.ece651.team14.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapTextView;
import edu.duke.ece651.team14.shared.Player;

public class ServerAdmin {
  ServerSocket serverSocket;
  ArrayList<Socket> clientSockets;
  HashMap<Player, Communicator> PlayerCommunicator;
  Map GameMap;
  MapTextView view;
  private static final HashMap<Integer, String> colorMap = new HashMap<>();
  static {
    colorMap.put(0, "red");
    colorMap.put(1, "green");
    colorMap.put(2, "blue");
    colorMap.put(3, "yellow");
  }

  public ServerAdmin(int portNum) throws IOException {
    this.serverSocket = new ServerSocket(portNum);
    this.clientSockets = new ArrayList<Socket>();
    this.PlayerCommunicator = new HashMap<Player, Communicator>();
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
      Communicator clientCommunicator = new Communicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
      Player p = new BasicPlayer(new Color(colorMap.get(acceptedPlayers)), colorMap.get(acceptedPlayers));
      PlayerCommunicator.put(p, clientCommunicator);
      // debug message:
      System.out.println("Accept player connection:" + p);
      acceptedPlayers++;
    }
  }

  public void InitializeGamePhase() throws IOException {
    MapFactory f = new MapFactory();
    this.GameMap = f.makeMap("Earth", new ArrayList<Player>(this.PlayerCommunicator.keySet()));
    this.view = new MapTextView(this.GameMap);
    // System.out.println(view.displayMap());
    // send the player object to client
    for (Player p : PlayerCommunicator.keySet()) {
      Communicator c = this.PlayerCommunicator.get(p);
      c.sendObject(p);
      c.sendObject(view.displayMap());
    }
  }

  /**
   * Release all the resources of this ServerAdmin
   * 
   * @throws IOException
   */
  public void releaseResources() throws IOException {
    for (Communicator c : PlayerCommunicator.values()) {
      c.release();
    }
    for (Socket s : clientSockets) {
      s.close();
    }
    serverSocket.close();
  }

}

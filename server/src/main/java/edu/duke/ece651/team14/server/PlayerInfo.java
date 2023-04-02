package edu.duke.ece651.team14.server;

import java.io.IOException;
import java.net.Socket;

import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Player;


/**
 * This class is used to group the specific information for a player in the game together.
 *
 */
public class PlayerInfo {
  private static final String C = "connected";
  private static final String D = "disconnected";

  private Socket thisSocket;//socket, should be release if disconnected
  private Player player;//player object to play the game
  private Communicator communicator;//communicator to communicate with the client
  private String status;//the connection status

  public PlayerInfo(Socket thisSocket, Player player, Communicator communicator) {
     this.thisSocket = thisSocket;
     this.player = player;
     this.communicator = communicator;
     this.status = C;
   }

  public PlayerInfo(Socket thisSocket,Player player) throws IOException{
    this.thisSocket = thisSocket;
    this.player = player;
    this.communicator = new Communicator(thisSocket.getOutputStream(), thisSocket.getInputStream());
    this.status = C;
  }

  public void setDisconnected(){
    this.status = D;
  }

  public void setConnected(){
    this.status = C;
  }

  public boolean isConnected(){
    return status.equals(C);
  }
  
  public Communicator getCommunicator() {
    return communicator;
  }

  public Player getPlayer() {
    return player;
  }

  public Socket getThisSocket() {
    return thisSocket;
  }
}

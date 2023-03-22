/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team14.server;

import java.io.IOException;
import edu.duke.ece651.team14.shared.MyName;

public class App {
  ServerAdmin serverAdmin;
  int num_players;

  /**
   * Constructor
   *
   * @param portNum:     server port number - must be greater than 1024
   * @param num_players: number of players
   * @throws IOException
   */
  public App(int portNum, int num_players) throws IOException {
    if (num_players < 2) {
      throw new IllegalArgumentException("To play, you need a minimum of 2 players.");
    }
    if (num_players > 4) {
      throw new IllegalArgumentException("To play, you cannot exceed a maximum of 4 players.");
    }
    if (portNum <= 1024 || portNum > 65535) {
      throw new IllegalArgumentException("Invalid port number.");
    }
    this.serverAdmin = new ServerAdmin(portNum);
    this.num_players = num_players;
  }

  public String getMessage() {
    return "Hello from the server for " + MyName.getName();
  }

  public void startGame() throws IOException, ClassNotFoundException {
    try {
      System.out.println("\nStarting game...\n");
      serverAdmin.acceptPlayersPhase(num_players);
      serverAdmin.initializeGamePhase();
      serverAdmin.playGamePhase();
    } finally {
      serverAdmin.releaseResources();// the purpose is when exception happens, these resources are always released.
    }
  }

  // ./gradlew :server:run --args "[portnum] [num_players]"
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    try {
      int port = Integer.parseInt(args[0]);
      int num_players = Integer.parseInt(args[1]);
      App a = new App(port, num_players);
      System.out.println(a.getMessage());
      a.startGame();
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.out.println("Exiting game...");
    }
  }
}

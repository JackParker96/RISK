/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team14.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.MyName;

public class App {
  ClientPlayer client;

  /**
   * Constructor (DELETE THIS ONE)
   *
   * @param hostname: name of host server
   * @param port:     port number of host server
   */
  /*
  public App(String hostName, int port) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    this.client = new TextClientPlayer(hostName, port, input, System.out);
  }
  */

  /**
   * Improved Constructor - can be tested in unit tests!
   *
   * @param clientSocket: socket for player
   * @param comm: Communicator to send/recieve Objects from server
   * @throws IOException
   */
  public App(Socket clientSocket, Communicator comm) throws IOException {
   BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
   this.client = new TextClientPlayer(clientSocket, comm, input, System.out);
  }

  /**
   * Constructor for unit tests
   *
   * @param mockPlayer: mock ClientPlayer class
   * @throws IOException
   */
  public App(ClientPlayer mockPlayer) throws IOException {
    this.client = mockPlayer;
  }

  /**
   * Returns message, verifies that Client App class was created properly
   *
   * @return String
   */
  public String getMessage() {
    return "Hello from the client for " + MyName.getName();
  }

  // ./gradlew :client:run --args "vcm-xxxxx.vm.duke.edu [port_num]"
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    String hostName = args[0];
    int port = Integer.parseInt(args[1]);
    Socket clientSocket = new Socket(hostName, port);
    Communicator comm = new Communicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
    //App a = new App(hostName, port);   // for old constructor
    App a = new App(clientSocket, comm);
    System.out.println("Welcome to the RISC player terminal\n\nInitializing game setup...\n");  
    //System.out.println(a.getMessage());
    try{
      a.client.whoAmIPhase();
      a.client.placeUnitsPhase();
      a.client.playGamePhase();
    } finally{
      a.client.release();
    }
  }
}

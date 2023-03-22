/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team14.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MyName;
import edu.duke.ece651.team14.shared.Order;

public class App {
  ClientPlayer client;

  /**
   * Constructor
   *
   * @param hostname: name of host server
   * @param port:     port number of host server
   */
  public App(String hostName, int port) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    this.client = new TextClientPlayer(hostName, port, input, System.out);
  }

  /**
   * Constructor for mock
   */
  public App(ClientPlayer mockPlayer) throws IOException {
    this.client = mockPlayer;
  }


  public String getMessage() {
    return "Hello from the client for " + MyName.getName();
  }

  // ./gradlew :client:run --args "vcm-xxxxx.vm.duke.edu [port_num]"
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    String hostName = args[0];
    int port = Integer.parseInt(args[1]);
    App a = new App(hostName, port);
    System.out.println(a.getMessage());
    try{
      a.client.whoAmIPhase();
      a.client.placeUnitsPhase();
      a.client.playGamePhase();
    } finally{
      a.client.release();
    }
  }
}

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team14.client;

import java.io.IOException;
import java.net.Socket;

import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.MyName;
import edu.duke.ece651.team14.shared.Territory;

public class App {
  public String getMessage() {
    return "Hello from the client for " + MyName.getName();
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    App a = new App();
    System.out.println(a.getMessage());
    String hostName = args[0];
    int port = Integer.parseInt(args[1]);
    try (Socket clientSocket = new Socket(hostName, port))// hardcoded hostname and port
    {// try-with-resources
      Communicator serverCommunicator = new Communicator(clientSocket.getOutputStream(),clientSocket.getInputStream());
      Territory t = (Territory) serverCommunicator.recvObject();
      System.out.println("Received Territory:"+t);// Territory toString() called
    }
  }
}

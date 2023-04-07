
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team14.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import edu.duke.ece651.team14.client.controller.LoginController;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.MyName;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
  GUIClientPlayer client;

  /**
   * Constructor - can now be tested in unit tests!
   *
   * @param clientSocket: socket for player
   * @param comm:         Communicator to send/recieve Objects from server
   * @throws IOException
   */
  // public App(Socket clientSocket, Communicator comm) throws IOException {
  //   BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
  //   this.client = new TextClientPlayer(clientSocket, comm, input, System.out);
  // }

  /**
   * Constructor for unit tests
   *
   * @param mockPlayer: mock ClientPlayer class
   * @throws IOException
   */
  // public App(ClientPlayer mockPlayer) throws IOException {
  //   this.client = mockPlayer;
  // }

  /**
   * Returns message, verifies that Client App class was created properly
   *
   * @return String
   */
  public String getMessage() {
    return "Hello from the client for " + MyName.getName();
  }

  @Override
  public void init() throws IOException {
    List<String> params = getParameters().getRaw();
    String hostName = params.get(0);
    int port = Integer.parseInt(params.get(1));
    Socket clientSocket = new Socket(hostName, port);
    Communicator comm = new Communicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    this.client = new GUIClientPlayer(clientSocket, comm, input, System.out);
  }

  @Override
  public void start(Stage stage) throws Exception {
    this.client.setStage(stage);
    this.client.loginPhase();
    // try {
    // this.client.PlayGame();
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // } finally {
    // this.client.release();
    // }
  }

  // ./gradlew :client:run --args "vcm-xxxxx.vm.duke.edu [port_num]"
  public static void main(String[] args) {
    launch(args[0], args[1]);

    /*
     * CODE TO SUPPORT TEXT CLIENT PLAYER
     * String hostName = args[0];
     * int port = Integer.parseInt(args[1]);
     * Socket clientSocket = new Socket(hostName, port);
     * Communicator comm = new Communicator(clientSocket.getOutputStream(),
     * clientSocket.getInputStream());
     * App a = new App(clientSocket, comm);
     * System.out.
     * println("Welcome to the RISC player terminal\n\nInitializing game setup...\n"
     * );
     * try{
     * a.client.PlayGame();
     * System.out.println("\nGame Over!");
     * } finally{
     * a.client.release();
     * }
     */
  }
}

package edu.duke.ece651.team14.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import edu.duke.ece651.team14.shared.Account;

public class ServerAdmin {
  ServerSocket serverSocket;
  ClientSockets clientSockets; 
  List<Game> allGames;
  static HashSet<Account> accounts = new HashSet<>();
  static{
    accounts.add(new Account("Jack", "passw0rd"));
    accounts.add(new Account("Evan", "passw0rd"));
    accounts.add(new Account("Maya", "passw0rd"));
    accounts.add(new Account("Xincheng", "passw0rd"));
    accounts.add(new Account("a", "a"));
  }
  /**
   * Constructor
   *
   * @param portNum: server port number
   * @throws IOException
   */
  public ServerAdmin(int portNum) throws IOException {
    this.serverSocket = new ServerSocket(portNum);
    this.clientSockets = new ClientSockets();
    this.allGames = Collections.synchronizedList(new ArrayList<Game>());
  }

  /**
   * Constructor for mock object
   */
  public ServerAdmin(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    this.clientSockets = new ClientSockets();
    this.allGames = Collections.synchronizedList(new ArrayList<Game>());
  }

  public void ServerHandleRequest(){
    while(true){
      try{
        Socket clientSocket = serverSocket.accept();
        clientSockets.addSocket(clientSocket);
        RequestHandler handler = new RequestHandler(clientSockets, clientSocket, allGames, accounts);
        Thread t = new Thread(handler,"handler thread");
        t.start();
      }catch(IOException ioe){
        System.out.println(ioe.getMessage());
      }
    }
  }

  
}


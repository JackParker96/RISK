package edu.duke.ece651.team14.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used to group all client sockets together, and remove it with
 * releasing its resources.
 */
public class ClientSockets {
  List<Socket> sockets;

  public ClientSockets() {
    this.sockets = Collections.synchronizedList(new ArrayList<Socket>());
  }

  public void addSocket(Socket sock) {
    this.sockets.add(sock);
  }

  public void removeSocket(Socket sock) {
    sockets.remove(sock);
    try {
      sock.close();
    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());// log this message.
    }
  }

  public int getSize(){
    return sockets.size();
  }
}

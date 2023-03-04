package edu.duke.ece651.team14.shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class wraps the socket output stream and input steam to send and recv object
 * Reference:
 * https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
 */
public class Communicator {
  private final ObjectOutputStream out;
  private final ObjectInputStream in;

  public Communicator(Socket clientSocket) throws IOException {
    this.out = new ObjectOutputStream(clientSocket.getOutputStream());
    this.in = new ObjectInputStream(clientSocket.getInputStream());
    this.out.flush();
  }

  /**
   * Send an object to other end of the connection.
   * 
   * @param input
   */
  public void sendObject(Object obj) throws IOException{ 
    this.out.writeObject(obj);
    this.out.flush();//send buffered data immediately
  }

  /**
   * Receive message from other end of the connection
   * 
   * @return the object received
   * @throws IOException
   */
  public Object recvObject() throws IOException, ClassNotFoundException{
    return in.readObject();
  }
}

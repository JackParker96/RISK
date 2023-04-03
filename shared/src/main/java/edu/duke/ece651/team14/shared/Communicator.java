package edu.duke.ece651.team14.shared;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * This class wraps the socket output stream and input steam to send and recv
 * object
 * Reference:
 * https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
 */
public class Communicator {
  private final ObjectOutputStream out;
  private final ObjectInputStream in;

  /**
   * Creates a Communicator object with given output and input streams,
   * immediately flushes output
   *
   * @param socketOut is the output socket
   * @param socketIn  is the input socket
   *
   * @throws IOException
   */
  public Communicator(OutputStream socketOut, InputStream socketIn) throws IOException {
    this.out = new ObjectOutputStream(socketOut);
    this.in = new ObjectInputStream(socketIn);
    this.out.flush();
  }

  /**
   * Send an object to other end of the connection.
   * 
   * @param input
   */
  public void sendObject(Object obj) throws IOException {
    this.out.reset();// make it "forget" past objects
    this.out.writeObject(obj);
    this.out.flush();// send buffered data immediately
  }

  /**
   * Receive message from other end of the connection
   * 
   * @return the object received
   * @throws IOException
   */
  public Object recvObject() throws IOException, ClassNotFoundException {
    return in.readObject();
  }

  /**
   * Returns a string from other end of connection
   *
   * @throws IOException
   * @throws ClassNotFoundException
   *
   * @return the string
   */
  public String recvString() throws IOException, ClassNotFoundException {
    return (String) recvObject();
  }

  /**
   * Returns a map object from other end of connection
   *
   * @throws IOException
   * @throws ClassNotFoundException
   *
   * @return the map object
   */
  public Map recvMap() throws IOException, ClassNotFoundException {
    return (Map) recvObject();
  }

  /**
   * Returns a BasicPlayer from other end of connection
   *
   * @throws IOException
   * @throws ClassNotFoundException
   *
   * @return the BasicPlayer
   */
  public BasicPlayer recvBasicPlayer() throws IOException, ClassNotFoundException {
    return (BasicPlayer) recvObject();
  }

  /**
   * Returns an ArrayList of orders from other end of connection
   *
   * @throws IOException
   * @throws ClassNotFoundException
   *
   * @return an ArrayList of orders
   */
  public ArrayList<Order> recvOrders() throws IOException, ClassNotFoundException {
    return (ArrayList<Order>) recvObject();
  }

  /**
   * Receive a list of ids
   * 
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public ArrayList<Integer> recvIDs() throws IOException, ClassNotFoundException {
    return (ArrayList<Integer>) recvObject();
  }

  public Account recvAccount() throws IOException, ClassNotFoundException {
    return (Account) recvObject();
  }

  /**
   * Returns a UnitPlacementOrder from other end of connection
   *
   * @throws IOException
   * @throws ClassNotFoundException
   *
   * @return the UnitPlacementOrder
   */
  public UnitPlacementOrder recvUnitPOrder() throws IOException, ClassNotFoundException {
    return (UnitPlacementOrder) recvObject();
  }

  /**
   * Closes input and output streams
   *
   * @throws IOException
   */
  public void release() throws IOException {
    this.in.close();
    this.out.close();
  }
}

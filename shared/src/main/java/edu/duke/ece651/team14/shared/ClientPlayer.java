package edu.duke.ece651.team14.shared;

/** 
This is the class that the server keeps track of to do communication with clients
*/
public class ClientPlayer extends Player {
  private final Communicator communicator;
  public ClientPlayer(String color, Communicator communicator){
    super(color);
    this.communicator = communicator;
  }

  
  public Communicator getCommunicator(){
    return this.communicator;
  }
}

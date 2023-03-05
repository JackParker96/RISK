package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ClientPlayerTest {
  @Test
  public void test_clientplayer() throws IOException, ClassNotFoundException {
    try (
        FileOutputStream testOut = new FileOutputStream("network.txt");
        FileInputStream testIn = new FileInputStream("network.txt");) {
      Communicator testCommunicator = new Communicator(testOut, testIn);
      ClientPlayer p = new ClientPlayer("yellow", testCommunicator);
      Territory t = new Territory("testTerritory");
      p.getCommunicator().sendObject(t);
      // assume sending to network..
      Territory t_received = (Territory)p.getCommunicator().recvObject();
      assertEquals(t, t_received);
    }
  }

}

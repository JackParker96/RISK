package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CommunicatorTest {
  @Test
  public void test_5() throws IOException, ClassNotFoundException {
    try (
        FileOutputStream testOut = new FileOutputStream("network.txt");
        FileInputStream testIn = new FileInputStream("network.txt");) {
      Communicator testCommunicator = new Communicator(testOut, testIn);
      Territory t = new BasicTerritory("testTerritory");
      testCommunicator.sendObject(t);
      // assume sending to network..
      Territory t_received = (Territory) testCommunicator.recvObject();
      assertEquals(t, t_received);
    }

  }
}

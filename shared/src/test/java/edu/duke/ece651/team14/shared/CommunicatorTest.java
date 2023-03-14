package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CommunicatorTest {
  @Test
  public void test_communicator() throws IOException, ClassNotFoundException {
    Communicator testCommunicator=null;
    try {
      FileOutputStream testOut = new FileOutputStream("network.txt");
      FileInputStream testIn = new FileInputStream("network.txt");
      testCommunicator = new Communicator(testOut, testIn);
      Territory t = new BasicTerritory("testTerritory");
      Player p = new BasicPlayer(new Color("yellow"), "yellow");
      UnitPlacementOrder upo = new UnitPlacementOrder();
      String msg = "ok";
      testCommunicator.sendObject(t);
      testCommunicator.sendObject(p);
      testCommunicator.sendObject(upo);
      testCommunicator.sendObject(msg);
      // assume sending to network..
      Territory t_received = (Territory) testCommunicator.recvObject();
      Player p_recv = testCommunicator.recvBasicPlayer();
      UnitPlacementOrder upo_recv = testCommunicator.recvUnitPOrder();
      String msg_recv = testCommunicator.recvString();
      assertEquals(t, t_received);
      assertEquals(p, p_recv);
      assertEquals(msg_recv, msg);
      assertEquals(0, upo_recv.size());
    } finally {
      testCommunicator.release();
    }
  }
}

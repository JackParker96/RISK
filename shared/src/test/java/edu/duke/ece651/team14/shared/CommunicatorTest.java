package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CommunicatorTest {
  @Test
  public void test_communicator() throws IOException, ClassNotFoundException {
    Communicator testCommunicator = null;
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

  @Test
  void test_recvMap() throws IOException, ClassNotFoundException {
    Communicator testCommunicator = null;
    try {
      FileOutputStream testOut = new FileOutputStream("recvMapTest.txt");
      FileInputStream testIn = new FileInputStream("recvMapTest.txt");
      testCommunicator = new Communicator(testOut, testIn);
      ArrayList<Territory> terrs = new ArrayList<Territory>();
      terrs.add(new BasicTerritory("t1"));
      terrs.add(new BasicTerritory("t2"));
      Map map = new Map(terrs, "m");
      testCommunicator.sendObject(map);
      assertEquals(map, testCommunicator.recvMap());
    } finally {
      testCommunicator.release();
    }
  }

  @Test
  void test_recvOrders() throws IOException, ClassNotFoundException {
    Communicator testCommunicator = null;
    try {
      FileOutputStream testOut = new FileOutputStream("recvTest.txt");
      FileInputStream testIn = new FileInputStream("recvTest.txt");
      testCommunicator = new Communicator(testOut, testIn);
      ArrayList<Order> orders = new ArrayList<>();
      orders.add(new MoveOrder(new BasicTerritory("1"), new BasicTerritory("2"), 3, null));
      orders.add(new AttackOrder(new BasicTerritory("2"), new BasicTerritory("1"), 3, null));
      testCommunicator.sendObject(orders);
      ArrayList<Order> recv_orders = testCommunicator.recvOrders();
      assertEquals(orders.get(0).getOrigin(), recv_orders.get(1).getDestination());
      assertEquals(recv_orders.get(0).getDestination(), recv_orders.get(1).getOrigin());
    } finally {
      testCommunicator.release();
    }
  }

}

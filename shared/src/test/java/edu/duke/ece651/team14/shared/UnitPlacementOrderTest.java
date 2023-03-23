package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UnitPlacementOrderTest {
  @Test
  public void test_placement_order() throws IOException, ClassNotFoundException {
    UnitPlacementOrder upo = new UnitPlacementOrder();
    ArrayList<String> names = new ArrayList<>();
    names.add("t1");
    names.add("t2");
    names.add("t3");
    ArrayList<Integer> nums = new ArrayList<>();
    nums.add(4);
    nums.add(3);
    nums.add(5);
    for (int i = 0; i < names.size(); i++) {
      upo.addOneTerrPlacement(names.get(i), nums.get(i));
    }

    Communicator testCommunicator = null;
    try {
      FileOutputStream testOut = new FileOutputStream("network.txt");
      FileInputStream testIn = new FileInputStream("network.txt");
      testCommunicator = new Communicator(testOut, testIn);
      testCommunicator.sendObject(upo);
      // assume sending to network..
      UnitPlacementOrder upo_recv = (UnitPlacementOrder) testCommunicator.recvObject();
      for (int i = 0; i < upo_recv.size(); i++) {
        assertEquals(upo.getName(i), upo_recv.getName(i));
        assertEquals(upo.getNumUnits(i), upo_recv.getNumUnits(i));
      }
      upo_recv.resetUnits();
      for (int i = 0; i < upo_recv.size(); i++) {
        assertEquals(upo.getName(i), upo_recv.getName(i));
        assertEquals(0, upo_recv.getNumUnits(i));
      }
      upo_recv.setNumUnits(0, 5);
      assertEquals(5, upo_recv.getNumUnits(0));
      assertThrows(IllegalArgumentException.class, () -> upo_recv.setNumUnits(0, -1));
    } finally {
      testCommunicator.release();
    }
  }

}

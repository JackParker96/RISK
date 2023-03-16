package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MoveOrderTest {

  @Test
  // Test order origin
  public void test_getters() {
    BasicTerritory t1 = new BasicTerritory("t1");
    BasicTerritory t2 = new BasicTerritory("t2");
    int numUnits = 2;
    Player player = new BasicPlayer(new Color("red"), "p1");
    MoveOrder m = new MoveOrder(t1, t2, numUnits, player);
    
    assertEquals(m.getOrigin(), t1);
    assertNotEquals(m.getOrigin(), t2);

    assertEquals(m.getDestination(), t2);
    assertNotEquals(m.getDestination(), t1);

    assertEquals(m.getNumUnits(), numUnits);
    assertNotEquals(m.getNumUnits(), 3);

    assertEquals(m.getPlayer(), player);
    assertNotEquals(m.getPlayer(), new BasicPlayer(new Color("blue"), "blue"));

    assertEquals(m.getUnitType(), "basic");
    assertNotEquals(m.getUnitType(), "other");

    MoveOrder m2 = new MoveOrder(t1, t2, numUnits, player, "otherUnitType");

    assertEquals(m2.getUnitType(), "otherUnitType");
    assertNotEquals(m2.getUnitType(), "basic");

    assertEquals(m2.getOrderType(), "move");
  }

}

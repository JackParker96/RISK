package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.Test;

public class BasicTerritoryTest {
  @Test
  public void test_constructor() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals("gondor", gondor.getName());
  }

  @Test
  public void test_getNeighbors() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals(0, gondor.getNeighbors().size());
    BasicTerritory hogwarts = new BasicTerritory("Hogwarts");
    BasicTerritory mordor = new BasicTerritory("Mordor");
    ArrayList<Territory> terr_arr = new ArrayList<Territory>();
    terr_arr.add(hogwarts);
    terr_arr.add(mordor);
    gondor.tryInitializeAllTerr(terr_arr);
    gondor.tryInitializeAdjacentTerr(mordor);
    assertEquals(1, gondor.getNeighbors().size());
    gondor.tryInitializeAdjacentTerr(hogwarts);
    assertEquals(2, gondor.getNeighbors().size());
  }
  
  @Test
  public void test_tryAddUnits_getNumUnits() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals(0, gondor.getNumUnits());
    assertThrows(IllegalArgumentException.class, () -> gondor.tryAddUnits(new ArrayList<Unit>()));
    ArrayList<Unit> units_lst = new ArrayList<Unit>();
    units_lst.add(new BasicUnit());
    assertEquals(true, gondor.tryAddUnits(units_lst));
    assertEquals(1, gondor.getNumUnits());
    ArrayList<Unit> more_units = new ArrayList<Unit>();
    more_units.add(new BasicUnit());
    more_units.add(new BasicUnit());
    assertEquals(true, gondor.tryAddUnits(more_units));
    assertEquals(3, gondor.getNumUnits());
  }

  @Test
  public void test_adjacentTerritories() {
    BasicTerritory roshar = new BasicTerritory("Roshar");
    ArrayList<Territory> map = new ArrayList<Territory>();
    BasicTerritory hogwarts = new BasicTerritory("Hogwarts");
    BasicTerritory mordor = new BasicTerritory("Mordor");
    BasicTerritory narnia = new BasicTerritory("Narnia");
    BasicTerritory duke = new BasicTerritory("Duke");
    map.add(hogwarts);
    map.add(mordor);
    map.add(narnia);
    assertEquals(true, roshar.tryInitializeAllTerr(map));
    assertEquals(true, roshar.tryInitializeAdjacentTerrStr("Hogwarts"));
    assertThrows(IllegalArgumentException.class, () -> roshar.tryInitializeAdjacentTerrStr("Duke"));
    assertThrows(IllegalArgumentException.class, () -> roshar.tryInitializeAdjacentTerr(duke));
    assertEquals(true, roshar.tryInitializeAdjacentTerr(mordor));
    // HashMap<Territory, Boolean> adj = new HashMap<Territory, Boolean>();
    // adj.put(hogwarts, true);
    // adj.put(mordor, true);
    // adj.put(narnia, false);
    // assertEquals(roshar.adjacentTerritories, adj);//dont want class attribute
    // public
    assertTrue(roshar.isAdjacentTo(hogwarts));
    assertTrue(roshar.isAdjacentTo("Mordor"));
    assertFalse(roshar.isAdjacentTo(narnia));
    assertFalse(roshar.isAdjacentTo("Narnia"));
    assertThrows(IllegalArgumentException.class, () -> roshar.isAdjacentTo("UNC"));
    assertThrows(IllegalArgumentException.class, () -> roshar.isAdjacentTo(duke));
  }

  @Test
  public void test_equals() {
    // Tests for when both territories are unowned
    BasicTerritory gondor = new BasicTerritory("Gondor");
    BasicTerritory mordor = new BasicTerritory("Mordor");
    BasicTerritory gondor2 = new BasicTerritory("Gondor");
    assertEquals(gondor, gondor2);
    assertFalse(gondor.equals(mordor));
    String str = "Gondor";
    assertFalse(gondor.equals(str));
    // Tests for when exactly one of the two territories is owned
    Player evan_almighty = new BasicPlayer(new Color("green"), "evan_almighty");
    gondor.setOwner(evan_almighty);
    assertNotEquals(gondor, gondor2);
    assertNotEquals(gondor2, gondor);
    // Test for when both territories are owned
    gondor2.setOwner(evan_almighty);
    mordor.setOwner(evan_almighty);
    assertEquals(gondor, gondor2);
    assertNotEquals(gondor, mordor);
  }

  @Test
  public void test_setOwner_getOwner() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertNull(gondor.getOwner());
    Player evan_almighty = new BasicPlayer(new Color("green"), "evan_almighty");
    gondor.setOwner(evan_almighty);
    assertEquals(evan_almighty, gondor.getOwner());
  }

  @Test
  public void test_toString() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals("gondor", gondor.toString());
  }

  @Test
  public void test_hashCode() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals("gondor".hashCode(), gondor.hashCode());
  }
}

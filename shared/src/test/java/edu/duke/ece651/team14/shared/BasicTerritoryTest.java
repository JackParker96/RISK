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
  public void test_productionRate_getters() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals(0, gondor.getFoodProductionRate());
    assertEquals(0, gondor.getTechProductionRate());
  }

  @Test
  public void test_productionRate_setters() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    gondor.setFoodProductionRate(5);
    assertEquals(5, gondor.getFoodProductionRate());
    assertEquals(0, gondor.getTechProductionRate());
    gondor.setTechProductionRate(10);
    assertEquals(5, gondor.getFoodProductionRate());
    assertEquals(10, gondor.getTechProductionRate());
  }

  @Test
  public void test_getNeighbors() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals(0, gondor.getAdjacentTerritories().size());
    BasicTerritory hogwarts = new BasicTerritory("Hogwarts");
    BasicTerritory mordor = new BasicTerritory("Mordor");
    ArrayList<Territory> terr_arr = new ArrayList<Territory>();
    terr_arr.add(hogwarts);
    terr_arr.add(mordor);
    gondor.addAdjacentTerritories(mordor);
    assertEquals(1, gondor.getAdjacentTerritories().size());
    gondor.addAdjacentTerritories(hogwarts);
    assertEquals(2, gondor.getAdjacentTerritories().size());
  }
  
  @Test
  public void test_addUnits_getNumUnits() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals(0, gondor.getNumUnits());
    gondor.addUnits(new BasicUnit());
    assertEquals(1, gondor.getNumUnits());
    ArrayList<Unit> more_units = new ArrayList<Unit>();
    more_units.add(new BasicUnit());
    more_units.add(new BasicUnit());
    gondor.addUnits(more_units);
    assertEquals(3, gondor.getNumUnits());
  }

  @Test
  public void test_adjacentTerritories() {
    BasicTerritory roshar = new BasicTerritory("Roshar");
    BasicTerritory hogwarts = new BasicTerritory("Hogwarts");
    BasicTerritory mordor = new BasicTerritory("Mordor");
    BasicTerritory narnia = new BasicTerritory("Narnia");
    BasicTerritory duke = new BasicTerritory("Duke");
    ArrayList<Territory> t = new ArrayList<Territory>(Arrays.asList(hogwarts, mordor));
    roshar.addAdjacentTerritories(t);
    assertTrue(roshar.isAdjacentTo(hogwarts));
    assertTrue(roshar.isAdjacentTo("Mordor"));
    assertFalse(roshar.isAdjacentTo(narnia));
    assertFalse(roshar.isAdjacentTo("Narnia"));
    assertFalse(roshar.isAdjacentTo("UNC"));
    assertFalse( roshar.isAdjacentTo(duke));
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
  public void test_getUnits() {
    BasicUnit b1 = new BasicUnit();
    BasicUnit b2 = new BasicUnit();
    ArrayList<Unit> units = new ArrayList<>();
    units.add(b1);
    units.add(b2);

    Territory t = new BasicTerritory("t");
    t.addUnits(b1);
    t.addUnits(b2);

    assertEquals(units, t.getUnits());

    units.add(new BasicUnit());

    assertNotEquals(units, t.getUnits());
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

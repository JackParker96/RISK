package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BasicTerritoryTest {
  @Test
  public void test_constructor() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals("gondor", gondor.getName());
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

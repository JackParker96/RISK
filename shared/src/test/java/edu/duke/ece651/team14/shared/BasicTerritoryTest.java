package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicTerritoryTest {
  @Test
  public void test_constructor() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    assertEquals("gondor", gondor.getName());
  }

  @Test
  public void test_equals() {
    BasicTerritory gondor = new BasicTerritory("Gondor");
    BasicTerritory mordor = new BasicTerritory("Mordor");
    BasicTerritory gondor2 = new BasicTerritory("Gondor");
    assertEquals(gondor, gondor2);
    assertFalse(gondor.equals(mordor));
    String str = "Gondor";
    assertFalse(gondor.equals(str));
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

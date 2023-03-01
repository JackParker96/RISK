package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TerritoryTest {
  @Test
  public void test_constructor() {
    Territory gondor = new Territory("Gondor");
    assertEquals("Gondor", gondor.getName());
  }

  @Test
  public void test_equals() {
    Territory gondor = new Territory("Gondor");
    Territory mordor = new Territory("Mordor");
    Territory gondor2 = new Territory("Gondor");
    assertEquals(gondor, gondor2);
    assertFalse(gondor.equals(mordor));
    String str = "Gondor";
    assertFalse(gondor.equals(str));
  }

  @Test
  public void test_toString() {
    Territory gondor = new Territory("Gondor");
    assertEquals("Gondor", gondor.toString());
  }

  @Test
  public void test_hashCode() {
    Territory gondor = new Territory("Gondor");
    assertEquals(49, gondor.hashCode());
  }
}

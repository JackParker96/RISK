package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicPlayerTest {
  @Test
  public void test_player_constructor() {
    BasicPlayer p1 = new BasicPlayer(new Color("RED"), "p");
    assertEquals(p1.getName(), "p");
    assertEquals(p1.getColor(), Color.colors.get("red"));
  }

  @Test
  public void test_equals() {
    BasicPlayer p1 = new BasicPlayer(new Color("RED"), "p");
    BasicPlayer p2 = new BasicPlayer(new Color("red"), "p");
    assertEquals(p1, p2);
    assertEquals(p2, p1);
    assertEquals(p1, p1);
    BasicPlayer p3 = new BasicPlayer(new Color("blue"), "b");
    assertNotEquals(p1, p3);
    String p4 = "red";
    assertNotEquals(p1, p4);
  }

  @Test
  public void test_hashCode() {
    BasicPlayer p = new BasicPlayer(new Color("yellow"), "p");
    assertEquals(p.hashCode(), "p".hashCode());
  }

  @Test
  public void test_toString() {
    BasicPlayer p = new BasicPlayer(new Color("grEEn"), "p");
    assertEquals("p", p.toString());
  }

}

package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ColorTest {
  @Test
  public void test_color_constructor() {
    Color r = new Color("ReD");
    Color g = new Color("GREEN");
    Color b = new Color("blue");
    Color y = new Color("Yellow");
    assertEquals("red", r.getColor());
    assertEquals("green", g.getColor());
    assertEquals("blue", b.getColor());
    assertEquals("yellow", y.getColor());
    assertThrows(IllegalArgumentException.class, () -> new Color("purple"));
  }

  @Test
  public void test_equals() {
    Color c1 = new Color("red");
    Color c2 = new Color("RED");
    Color c3 = new Color("blue");
    String c4 = "red";
    assertEquals(c1, c1);
    assertEquals(c1, c2);
    assertEquals(c2, c1);
    assertFalse(c1.equals(c3));
    assertFalse(c1.equals(c4));
  }
  
  @Test
  public void test_toString() {
    Color c = new Color("Red");
    assertEquals("red", c.toString());
  }

  @Test
  public void test_hashCode() {
    Color c = new Color("RED");
    String red = "red";
    assertEquals(c.hashCode(), red.hashCode());
  }

}

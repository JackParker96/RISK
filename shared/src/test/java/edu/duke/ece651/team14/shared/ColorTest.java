package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ColorTest {

  @Test
  // Tests color constructor using default string values
  public void test_string_color_constructor() {
    Color r = new Color("ReD");
    Color g = new Color("GREEN");
    Color b = new Color("blue");
    Color y = new Color("Yellow");
    assertEquals(Color.colors.get("red"), r);
    assertEquals(Color.colors.get("green"), g);
    assertEquals(Color.colors.get("blue"), b);
    assertEquals(Color.colors.get("yellow"), y);
    assertThrows(IllegalArgumentException.class, () -> new Color("purple"));
  }

  @Test
  // Tests equals method
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
  // Tests rgb value constructor
  public void test_rgbConstructor() {
    Color white = new Color(255, 255, 255);
    assertEquals(white.r, 255);
    assertEquals(white.g, 255);
    assertEquals(white.b, 255);
    assertEquals(white.a, 1);
    assertThrows(IllegalArgumentException.class, () -> new Color(-1, 20, 20));
  }

  @Test
  // Tests constructor which takes a preexisting color
  public void testOtherColorConstructor() {
    Color white = new Color(new Color(255, 255, 255));
    assertEquals(white.r, 255);
    assertEquals(white.g, 255);
    assertEquals(white.b, 255);
    assertEquals(white.a, 1);
  }
  
  @Test
  // Tests toString() method
  public void test_toString() {
    Color c = new Color("Red");
    assertEquals("r: 255 g: 0 b: 0 a: 1", c.toString());
  }

  @Test
  // Tests hashCode() method
  public void test_hashCode() {
    Color c = new Color("RED");
    assertEquals(Color.colors.get("red").hashCode(), c.hashCode());
  }

}

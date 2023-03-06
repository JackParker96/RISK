package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicUnitTest {
  @Test
  public void test_constructor_isAlive_tryToKill() {
    BasicUnit maya_the_magnificent = new BasicUnit();
    BasicUnit xincheng_the_shrewd = new BasicUnit();
    BasicUnit evan_almighty = new BasicUnit();
    BasicUnit jack_the_jovial = new BasicUnit();
    assertEquals(true, jack_the_jovial.isAlive());
    assertEquals(true, jack_the_jovial.tryToKill());
    assertFalse(jack_the_jovial.isAlive());
    assertThrows(IllegalArgumentException.class, () -> jack_the_jovial.tryToKill());
  }

  @Test
  public void test_equals() {
    BasicUnit u1 = new BasicUnit();
    BasicUnit u2 = new BasicUnit();
    String s = "basic";
    assertEquals(u1, u2);
    assertEquals(u1, u1);
    assertEquals(u2, u1);
    assertNotEquals(u1, s);
    u1.tryToKill();
    assertNotEquals(u1, u2);
    u2.tryToKill();
    assertEquals(u1, u2);
  }

  @Test
  public void test_toString() {
    BasicUnit u = new BasicUnit();
    assertEquals("BasicUnit - basic - alive", u.toString());
    u.tryToKill();
    assertEquals("BasicUnit - basic - dead", u.toString());
  }

  @Test
  public void test_hashCode() {
    BasicUnit u = new BasicUnit();
    assertEquals(u.hashCode(), u.getType().hashCode() + 1);
    u.tryToKill();
    assertEquals(u.hashCode(), u.getType().hashCode());
  }

}

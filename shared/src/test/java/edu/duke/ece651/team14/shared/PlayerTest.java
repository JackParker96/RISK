package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PlayerTest {
  @Test
  public void test_player_constructor() {
    Player p1 = new Player(new Color("RED"));
    Player p2 = new Player("Red");
    assertEquals(p1.getColor(), p2.getColor());
  }

  @Test
  public void test_adding_removing_territories() {
    Player player = new Player("blue");
    assertEquals(true, player.tryAddTerritory(new Territory("Gondor")));
    assertThrows(IllegalArgumentException.class, () -> player.tryAddTerritory(new Territory("Gondor")));
    assertEquals(true, player.tryAddTerritory(new Territory("mordor")));
    assertEquals(true, player.tryAddTerritory(new Territory("theShire")));
    assertEquals(3, player.getNumTerritories());
    assertEquals(true, player.tryRemoveTerritory(new Territory("gondor")));
    assertEquals(true, player.tryAddTerritory(new Territory("gondor")));
    assertThrows(IllegalArgumentException.class, () -> player.tryRemoveTerritory(new Territory("Rohan")));
  }

  @Test
  public void test_equals() {
    Player p1 = new Player(new Color("RED"));
    Player p2 = new Player("red");
    assertEquals(p1, p2);
    assertEquals(p2, p1);
    assertEquals(p1, p1);
    Player p3 = new Player("blue");
    assertNotEquals(p1, p3);
    String p4 = "red";
    assertNotEquals(p1, p4);
  }

  @Test
  public void test_hashCode() {
    Player p = new Player("yellow");
    Color c = new Color("yELLoW");
    String s = "yellow";
    assertEquals(p.hashCode(), c.hashCode());
    assertEquals(p.hashCode(), s.hashCode());
  }

  @Test
  public void test_toString() {
    Player p = new Player(new Color("grEEn"));
    assertEquals("green", p.toString());
  }

}

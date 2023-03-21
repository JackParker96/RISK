package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BasicPlayerTest {

  @Test
  public void test_hasWon() {
    MapFactory f = new MapFactory();
    Player p1 = new BasicPlayer(new Color("blue"), "A");
    Player p2 = new BasicPlayer(new Color("red"), "B");
    ArrayList<Player> players = new ArrayList<>();
    players.add(p1);
    players.add(p2);
    Map map = f.makeMap("test", players);
    assertFalse(p1.hasWon(map));
    assertFalse(p2.hasWon(map));
    assertFalse(p1.hasLost(map));
    assertFalse(p2.hasLost(map));
    map.getTerritoryByName("6").setOwner(p1);
    assertFalse(p1.hasWon(map));
    assertFalse(p2.hasWon(map));
    assertFalse(p1.hasLost(map));
    assertFalse(p2.hasLost(map));
    map.getTerritoryByName("4").setOwner(p1);
    assertFalse(p1.hasWon(map));
    assertFalse(p2.hasWon(map));
    assertFalse(p1.hasLost(map));
    assertFalse(p2.hasLost(map));
    map.getTerritoryByName("5").setOwner(p1);
    assertEquals(true, p1.hasWon(map));
    assertFalse(p2.hasWon(map));
    assertEquals(true, p2.hasLost(map));
    assertFalse(p1.hasLost(map));
  }

  @Test
  public void test_hasLost() {
    
  }
  
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

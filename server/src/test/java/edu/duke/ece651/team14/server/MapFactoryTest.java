package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public class MapFactoryTest {
  @Test
  // Tests making a map
  public void test_makeMap() {
    MapFactory f = new MapFactory();
    Map m = f.makeMap("Earth", makeTwoPlayers());
    assertEquals(m.getName(), "Earth");
    assertEquals(m.getMap().size(), 24);
    for (Territory t : m.getMap().values()) {
      assertNotNull(t.getOwner());
      assertTrue(t.getAdjacentTerritories().size() > 0);
      assertTrue(t.getAdjacentTerritories().size() < 24);
    }
  }

  @Test
  // Tests that addAdjacencies method adds symmetric adjacencies (i.e.
  // m.isAdjacentTo(n) == n.isAdjacentTo(m))
  public void test_symmetricAjacencies() {
    MapFactory f = new MapFactory();
    ArrayList<Territory> terrs = f.makeTerritories();
    f.addAdjacency(terrs);

    for (Territory t : terrs) {
      for (Territory n : t.getAdjacentTerritories()) {
        assertTrue(n.isAdjacentTo(t));
      }
    }
  }

  @Test
  // Tests some of the hard-coded adjacencies
  public void test_someAdjacencies() {
    MapFactory f = new MapFactory();
    ArrayList<Territory> terrs = f.makeTerritories();
    f.addAdjacency(terrs);
    Territory narnia = terrs.get(0);
    Territory midkemia = terrs.get(1);
    Territory oz = terrs.get(2);
    Territory gondor = terrs.get(3);

    assertTrue(narnia.isAdjacentTo("midkemia"));
    assertTrue(midkemia.isAdjacentTo("neverland"));
    assertTrue(oz.isAdjacentTo("elantris"));
    assertTrue(gondor.isAdjacentTo("wakanda"));

    assertFalse(narnia.isAdjacentTo("duke"));
    assertFalse(midkemia.isAdjacentTo("duke"));
    assertFalse(oz.isAdjacentTo("duke"));
    assertFalse(gondor.isAdjacentTo("duke"));
  }

  @Test
  // Tests makeTerritories() method an ArrayList of 24 Territories
 public void test_makeTerritories() {
    ArrayList<Territory> terrs = new MapFactory().makeTerritories();
    assertEquals(24, terrs.size());
    for (Territory t : terrs) {
      assertNotNull(t);
    }
  }

  @Test
  // Tests assigning two players
  public void test_makeTwoGroups() {
    MapFactory f = new MapFactory();
    ArrayList<Territory> terrs = f.makeTerritories();
    Player red = new BasicPlayer(new Color("red"), "red");
    Player blue = new BasicPlayer(new Color("blue"), "blue");
    ArrayList<Player> players = new ArrayList<>();
    players.add(red);
    players.add(blue);
    f.addOwners(terrs, players);
    for (int i = 0; i < terrs.size() / players.size(); i++) {
      assertEquals(terrs.get(i).getOwner(), red);
      assertEquals(terrs.get(i + terrs.size() / players.size()).getOwner(), blue);
    }
  }

  @Test
  // Tests assigning three players
  public void test_makeThreeGroups() {
    MapFactory f = new MapFactory();
    ArrayList<Territory> terrs = f.makeTerritories();
    Player red = new BasicPlayer(new Color("red"), "red");
    Player blue = new BasicPlayer(new Color("blue"), "blue");
    Player green = new BasicPlayer(new Color("green"), "green");
    ArrayList<Player> players = new ArrayList<>();
    players.add(red);
    players.add(blue);
    players.add(green);
    f.addOwners(terrs, players);
    for (int i = 0; i < terrs.size() / players.size(); i++) {
      assertEquals(terrs.get(i).getOwner(), red);
      assertEquals(terrs.get(i + terrs.size() / players.size()).getOwner(), blue);
      assertEquals(terrs.get(i + 2 * terrs.size() / players.size()).getOwner(), green);
    }
  }

  @Test
  // Tests assigning four players
  public void test_makeFourGroups() {
    MapFactory f = new MapFactory();
    ArrayList<Territory> terrs = f.makeTerritories();
    Player red = new BasicPlayer(new Color("red"), "red");
    Player blue = new BasicPlayer(new Color("blue"), "blue");
    Player green = new BasicPlayer(new Color("green"), "green");
    Player yellow = new BasicPlayer(new Color("yellow"), "yellow");
    ArrayList<Player> players = new ArrayList<>();
    players.add(red);
    players.add(blue);
    players.add(green);
    players.add(yellow);
    f.addOwners(terrs, players);
    for (int i = 0; i < terrs.size() / players.size(); i++) {
      assertEquals(terrs.get(i).getOwner(), red);
      assertEquals(terrs.get(i + terrs.size() / players.size()).getOwner(), blue);
      assertEquals(terrs.get(i + 2 * terrs.size() / players.size()).getOwner(), green);
      assertEquals(terrs.get(i + 3 * terrs.size() / players.size()).getOwner(), yellow);
    }
  }

  @Test
  // Tests trying to pass an empty players array to addOwners
  public void test_makeBadGroup() {
    MapFactory f = new MapFactory();
    assertThrows(IllegalArgumentException.class, () -> f.addOwners(f.makeTerritories(), new ArrayList<Player>()));
  }

  /**
   * Private helper method to create two generic players
   *
   * @return an ArrayList<Player> of two players
   */
  private ArrayList<Player> makeTwoPlayers() {
    ArrayList<Player> players = new ArrayList<>();
    players.add(new BasicPlayer(new Color("red"), "red"));
    players.add(new BasicPlayer(new Color("blue"), "blue"));
    return players;
  }
}

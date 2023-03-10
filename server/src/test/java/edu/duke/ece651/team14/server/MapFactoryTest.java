package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;

public class MapFactoryTest {
  @Test
  public void test_map() {
    MapFactory f = new MapFactory();
    ArrayList<Player> players = new ArrayList<>();
    Map m = f.makeMap("Earth",players);
    assertTrue(m.getTerritoryByName("oz").isAdjacentTo("gonDor"));
    assertFalse(m.getTerritoryByName("mt othrys").isAdjacentTo("roshar"));
    assertTrue(m.getTerritoryByName("duke").isAdjacentTo("north pole"));
    assertFalse(m.getTerritoryByName("gondor").isAdjacentTo("gonDor"));
    assertTrue(m.getTerritoryByName("gondor").isAdjacentTo("neverland"));
  }

  @Test
  public void test_createGroups() {
    MapFactory f = new MapFactory();
  }

}

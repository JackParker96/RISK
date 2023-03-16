package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class MoveOrderPathExistsRuleCheckerTest {
  @Test
  // Tests mapDFS method on basic map of five connected territories
  public void test_mapDFS() {
    Player p1 = new BasicPlayer(new Color("red"), "p1");
    Player p2 = new BasicPlayer(new Color("yellow"), "p2");

    ArrayList<Territory> terrs = getTestTerritories(p1, p2);

    MoveOrderPathExistsRuleChecker m = new MoveOrderPathExistsRuleChecker(null);

    ArrayList<Territory> visited = new ArrayList<>();
    visited.add(terrs.get(0));
    
    assertTrue(m.mapDFS(visited, terrs.get(0), terrs.get(0), terrs.get(3)));
    assertFalse(m.mapDFS(visited, terrs.get(0), terrs.get(0), terrs.get(4)));
    
  }

  @Test
  // Tests checkMyRule on basic map of five connected territories
  public void test_checkMyRule() {
    Player p1 = new BasicPlayer(new Color("red"), "p1");
    Player p2 = new BasicPlayer(new Color("yellow"), "p2");

    ArrayList<Territory> terrs = getTestTerritories(p1, p2);

    MoveOrder moveOrder = new MoveOrder(terrs.get(0), terrs.get(3), 1, p1);
    MoveOrder moveOrder2 = new MoveOrder(terrs.get(0), terrs.get(4), 1, p2);

    Map map = new Map(terrs, "map");

    MoveOrderPathExistsRuleChecker m = new MoveOrderPathExistsRuleChecker(null);
    
    assertNull(m.checkMyRule(map, moveOrder));
    assertEquals("Player does not own path from origin to destination", m.checkMyRule(map, moveOrder2));
  }

  /**
   * Helper method to make list of Territories
   *
   * @param p1 is the first player
   * @param p2 is the second player
   *
   * @return the list of Territories
   */
  private ArrayList<Territory> getTestTerritories(Player p1, Player p2) {
    ArrayList<Territory> terrs = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      terrs.add(new BasicTerritory(Integer.toString(i)));
      if (i < 4) {
        terrs.get(i).setOwner(p1);
      } else {
        terrs.get(i).setOwner(p2);
      }
    }

    // Add adjacencies
    terrs.get(0).addAdjacentTerritories(terrs.get(1));
    terrs.get(1).addAdjacentTerritories(terrs.get(2));
    terrs.get(2).addAdjacentTerritories(terrs.get(3));
    terrs.get(2).addAdjacentTerritories(terrs.get(4));

    terrs.get(1).addAdjacentTerritories(terrs.get(0));
    terrs.get(2).addAdjacentTerritories(terrs.get(1));
    terrs.get(3).addAdjacentTerritories(terrs.get(2));
    terrs.get(4).addAdjacentTerritories(terrs.get(2));

    terrs.get(1).addUnits(new BasicUnit());

    return terrs;
  }

}

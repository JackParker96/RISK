package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class AdjacentTerritoryRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    Territory start = new BasicTerritory("start");
    Territory dest1 = new BasicTerritory("dest1");
    Player p1 = new BasicPlayer(new Color("yellow"), "p1");
    
    Territory dest2 = new BasicTerritory("dest2");
    Player p2 = new BasicPlayer(new Color("red"), "p2");

    start.setOwner(p1);
    dest1.setOwner(p1);
    dest2.setOwner(p2);

    start.addAdjacentTerritories(dest1);
    start.addAdjacentTerritories(dest2);
    
    dest1.addAdjacentTerritories(start);

    dest2.addAdjacentTerritories(start);
    
    ArrayList<Territory> terrs = new ArrayList<>();
    terrs.add(start);
    terrs.add(dest1);
    terrs.add(dest2);
    Map map = new Map(terrs, "map");
    AdjacentTerritoryRuleChecker a = new AdjacentTerritoryRuleChecker(null);
    
    Order o1 = new MoveOrder(start, dest1, 5, p1);
    assertNull(a.checkMyRule(map, o1));

    Order o2 = new MoveOrder(dest1, start, 5, p1);
    assertNull(a.checkMyRule(map, o2));

    Order o3 = new AttackOrder(dest1, dest2, 5, p1);
    assertEquals("Territories are not adjacent", a.checkMyRule(map, o3));

    Order o4 = new AttackOrder(dest2, dest1, 5, p2);
    assertEquals("Territories are not adjacent", a.checkMyRule(map, o4));
  }

}

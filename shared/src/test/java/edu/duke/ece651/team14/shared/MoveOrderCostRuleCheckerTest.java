package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class MoveOrderCostRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    Territory start1 = new BasicTerritory("start1");
    Territory dest1 = new BasicTerritory("dest1");
    Player p1 = new BasicPlayer(new Color("yellow"), "p1");
    p1.addFoodResources(100);
    Unit u = new BasicUnit();
    start1.addUnits(u);

    Territory start2 = new BasicTerritory("start2");
    Territory dest2 = new BasicTerritory("dest2");
    Player p2 = new BasicPlayer(new Color("red"), "p2");
    p2.addFoodResources(1);
    start2.addUnits(u);

    start1.setOwner(p1);
    dest1.setOwner(p1);
    start2.setOwner(p2);
    dest2.setOwner(p2);

    start1.addAdjacentTerritories(dest1);
    start2.addAdjacentTerritories(dest2);
    
    dest1.addAdjacentTerritories(start1);
    dest2.addAdjacentTerritories(start2);
    
    ArrayList<Territory> terrs = new ArrayList<>();
    terrs.add(start1);
    terrs.add(start2);
    terrs.add(dest1);
    terrs.add(dest2);
    Map map = new Map(terrs, "map");
    MoveOrderCostRuleChecker m = new MoveOrderCostRuleChecker(null);
    
    MoveOrder o1 = new MoveOrder(start1, dest1, 1, p1);
    assertNull(m.checkMyRule(map, o1));

    MoveOrder o2 = new MoveOrder(start2, dest2, 1, p2);
    assertEquals("Not enough food resources to make this move", m.checkMyRule(map, o2));
  }

}

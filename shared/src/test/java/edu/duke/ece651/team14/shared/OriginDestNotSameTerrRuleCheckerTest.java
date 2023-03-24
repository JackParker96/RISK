package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class OriginDestNotSameTerrRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    Territory start = new BasicTerritory("start");
    Territory dest = new BasicTerritory("dest");
    Player p1 = new BasicPlayer(new Color("yellow"), "p1");

    start.setOwner(p1);
    dest.setOwner(p1);
 
    ArrayList<Territory> terrs = new ArrayList<>();
    terrs.add(start);
    terrs.add(dest);
    Map map = new Map(terrs, "map");

    OriginDestNotSameTerrRuleChecker r = new OriginDestNotSameTerrRuleChecker(null);

    Order o1 = new MoveOrder(start, start, 5, p1);
    assertEquals("Origin territory is the same as the destination territory", r.checkMyRule(map, o1));

    Order o2 = new MoveOrder(dest, dest, 5, p1);
    assertEquals("Origin territory is the same as the destination territory", r.checkMyRule(map, o2));

    Order o3 = new MoveOrder(start, dest, 5, p1);
    assertNull(r.checkMyRule(map, o3));

    Order o4 = new MoveOrder(dest, start, 5, p1);
    assertNull(r.checkMyRule(map, o4));
  }

}

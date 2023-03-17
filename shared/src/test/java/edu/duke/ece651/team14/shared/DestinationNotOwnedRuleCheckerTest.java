package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class DestinationNotOwnedRuleCheckerTest {
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

    ArrayList<Territory> terrs = new ArrayList<>();
    terrs.add(start);
    terrs.add(dest1);
    terrs.add(dest2);
    Map map = new Map(terrs, "map");
    DestinationNotOwnedRuleChecker d = new DestinationNotOwnedRuleChecker(null);
    
    MoveOrder m1 = new MoveOrder(start, dest1, 5, p1);
    assertEquals("Player owns the destination territory", d.checkMyRule(map, m1));

    MoveOrder m2 = new MoveOrder(start, dest2, 5, p1);
    assertNull(d.checkMyRule(map, m2));
  }

}

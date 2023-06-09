package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class OriginOwnershipRuleCheckerTest {
  @Test
  // Tests checkMyRule() method (player owns origin territory)
  public void test_checkMyRule() {
    Territory origin = new BasicTerritory("t1");
    Territory destination = new BasicTerritory("t2");
    Player p = new BasicPlayer(new Color("red"), "p");

    origin.setOwner(p);

    MoveOrder m = new MoveOrder(origin, destination, 2, p);

    ArrayList<Territory> terrs = new ArrayList<>();
    terrs.add(origin);
    terrs.add(destination);

    Map map = new Map(terrs, "map");

    OriginOwnershipRuleChecker o = new OriginOwnershipRuleChecker(null);

    assertNull(o.checkMyRule(map, m));

    Territory origin2 = new BasicTerritory("t3");
    origin2.setOwner(new BasicPlayer(new Color("blue"), "p2"));

    MoveOrder m2 = new MoveOrder(origin2, destination, 2, p);
    assertEquals("Player does not own origin territory", o.checkMyRule(map, m2));

  }
}

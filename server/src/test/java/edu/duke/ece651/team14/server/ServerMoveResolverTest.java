package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.BasicUnit;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public class ServerMoveResolverTest {
  @Test
  public void test_resolvemove() {
    Player p1 = new BasicPlayer(new Color("red"), "p1");
    Player p2 = new BasicPlayer(new Color("blue"), "p2");

    ArrayList<Player> players = new ArrayList<Player>();
    players.add(p1);
    players.add(p2);

    MapFactory m = new MapFactory();
    Map map = m.makeMap("test", players);

    Territory t0 = map.getTerritoryByName("0");
    Territory t1 = map.getTerritoryByName("1");
    Territory t2 = map.getTerritoryByName("2");
    Territory t4 = map.getTerritoryByName("4");
    Territory t5 = map.getTerritoryByName("5");

    addUnits(t0, t1);
    MoveOrder m1 = new MoveOrder(t0, t1, 5, p1);
    MoveOrder m2 = new MoveOrder(t1, t2, 11, p1);
    // MoveOrder m3 = new MoveOrder(t4, t1, 8, p1);

    t4.addUnits(new BasicUnit());
    t4.addUnits(new BasicUnit());

    MoveOrder p2m1 = new MoveOrder(t4, t5, 2, p2);

    ArrayList<Order> moveOrders = new ArrayList<>();
    moveOrders.add(m1);
    moveOrders.add(m2);
    // moveOrders.add(m3);

    moveOrders.add(p2m1);
    ServerMoveResolver smr = new ServerMoveResolver(map);
    smr.resolveAllMoveOrders(moveOrders);

    assertEquals(2,t0.getNumUnits());
    assertEquals(2,t1.getNumUnits());
    assertEquals(11,t2.getNumUnits());
    assertEquals(0,t4.getNumUnits());
    assertEquals(2,t5.getNumUnits());
  }

  // Helper method to add Units to two given Territories
  private void addUnits(Territory t1, Territory t2) {
    for (int i = 0; i < 3; i++) {
      t1.addUnits(new BasicUnit());
      t2.addUnits(new BasicUnit());
    }
    BasicUnit otherUnit = mock(BasicUnit.class);
    Mockito.when(otherUnit.getType()).thenReturn("other");
    t1.addUnits(otherUnit);
    t2.addUnits(otherUnit);
    for (int i = 0; i < 3; i++) {
      t1.addUnits(new BasicUnit());
      t2.addUnits(new BasicUnit());
    }
  }

}

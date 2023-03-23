package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import edu.duke.ece651.team14.shared.Unit;

public class ServerMoveResolverTest {
  @Test
  public void test_resolveMove() {
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

    t4.addUnits(new BasicUnit());
    t4.addUnits(new BasicUnit());

    MoveOrder p2m1 = new MoveOrder(t4, t5, 2, p2);

    ArrayList<Order> moveOrders = new ArrayList<>();
    moveOrders.add(m1);
    moveOrders.add(m2);

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

  @Test
  public void test_throwExceptions() {
    Player p1 = new BasicPlayer(new Color("red"), "p1");
    Player p2 = new BasicPlayer(new Color("blue"), "p2");

    ArrayList<Player> players = new ArrayList<Player>();
    players.add(p1);
    players.add(p2);

    MapFactory m = new MapFactory();
    Map map = m.makeMap("test", players);
    ServerMoveResolver smr = new ServerMoveResolver(map);

    Territory t0 = map.getTerritoryByName("0");
    Territory t1 = map.getTerritoryByName("1");
    Territory t2 = map.getTerritoryByName("2");

    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit();
    
    t1.addUnits(u1);
    t2.addUnits(u2);

    t0.setOwner(p1);
    t1.setOwner(p1);
    t2.setOwner(p2);
    
    MoveOrder m1 = new MoveOrder(t0, t1, 1, p1);   // not enough units in t0 to move into t1
    MoveOrder m2 = new MoveOrder(t0, t2, 1, p1);   // destination t2 is not owned by p1
    MoveOrder m3 = new MoveOrder(t2, t0, 1, p1);   // origin t2 is not owned by p1

    ArrayList<Order> list1 = new ArrayList<Order>();
    list1.add(m1);
    assertThrows(IllegalArgumentException.class, () -> {smr.resolveAllMoveOrders(list1);});

    ArrayList<Order> list2 = new ArrayList<Order>();
    list2.add(m2);
    assertThrows(IllegalArgumentException.class, () -> {smr.resolveAllMoveOrders(list2);});

    ArrayList<Order> list3 = new ArrayList<Order>();
    list3.add(m3);
    assertThrows(IllegalArgumentException.class, () -> {smr.resolveAllMoveOrders(list3);});
  }

}

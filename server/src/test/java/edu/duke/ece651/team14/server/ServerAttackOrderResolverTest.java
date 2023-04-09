package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.BasicUnit;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.CombatResolver;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.Unit;

public class ServerAttackOrderResolverTest {
  @Test
  public void test_resolveattack() {
    Player p1 = new BasicPlayer(new Color("red"), "p1");
    Player p2 = new BasicPlayer(new Color("blue"), "p2");

    ArrayList<Player> players = new ArrayList<Player>();
    players.add(p1);
    players.add(p2);

    MapFactory m = new MapFactory();
    Map map = m.makeMap("test", players);

    Territory t0 = map.getTerritoryByName("0");
    Territory t1 = map.getTerritoryByName("1");
    Territory t2 = map.getTerritoryByName("2");// player1
    Territory t4 = map.getTerritoryByName("4");// player2

    t0.setOwner(p1);
    t1.setOwner(p1);
    t2.setOwner(p1);
    t4.setOwner(p2);

    addUnits(t0, t1);
    addUnits(t2, t4);

    AttackOrder a1 = new AttackOrder(t2, t4, 2, p1);
    AttackOrder a2 = new AttackOrder(t2, t4, 1, p1);
    Order b1 = new AttackOrder(t2, t4, 100, p1); // not enough units in t2 to move into t4
    Order b2 = new AttackOrder(t0, t1, 1, p1); // destination t1 is owned by p1
    Order b3 = new AttackOrder(t4, t0, 1, p1); // origin t1 is not owned by p1

    ArrayList<Order> atkOrders = new ArrayList<>();
    atkOrders.add(a1);
    atkOrders.add(a2);
    atkOrders.add(b1);
    atkOrders.add(b2);
    atkOrders.add(b3);

    // make a mock resolver that always make defender wins
    CombatResolver mockResolver = mock(CombatResolver.class);
    Mockito.when(mockResolver.getCombatResult(new BasicUnit(),new BasicUnit())).thenReturn(true);
    ServerAttackOrderResolver saor = new ServerAttackOrderResolver(map, mockResolver);
    String results = saor.resolveAllAttackOrders(atkOrders);
    assertEquals(
        "\nOn Territory 4: \nThe p2 player defends with 6 units\nThe p1 player attacks with 3 units\nThe p2 player defends Territory 4 successfully!\n",
        results);
  }

  // Helper method to add Units to two given Territories
  private void addUnits(Territory t1, Territory t2) {
    for (int i = 0; i < 3; i++) {
      t1.addUnits(new BasicUnit());
      t2.addUnits(new BasicUnit());
    }
    for (int i = 0; i < 3; i++) {
      t1.addUnits(new BasicUnit());
      t2.addUnits(new BasicUnit());
    }
  }
}

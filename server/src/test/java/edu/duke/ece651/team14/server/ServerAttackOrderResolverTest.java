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
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

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

    Territory t0 = map.getTerritoryByName("0");// player1
    Territory t4 = map.getTerritoryByName("4");// player2

    t0.setOwner(p1);
    t4.setOwner(p2);

    addUnits(t0, t4);
    AttackOrder a1 = new AttackOrder(t0, t4, 2, p1);
    AttackOrder a2 = new AttackOrder(t0, t4, 1, p1);
    ArrayList<Order> atkOrders = new ArrayList<>();
    atkOrders.add(a1);
    atkOrders.add(a2);
    // make a mock resolver that always make defender wins
    CombatResolver mockResolver = mock(CombatResolver.class);
    Mockito.when(mockResolver.getCombatResult()).thenReturn(true);
    ServerAttackOrderResolver saor = new ServerAttackOrderResolver(map, mockResolver);
    String results = saor.resolveAllAttackOrders(atkOrders);
    assertEquals("The p2 player defends the Territory 4 successfully!\n", results);
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

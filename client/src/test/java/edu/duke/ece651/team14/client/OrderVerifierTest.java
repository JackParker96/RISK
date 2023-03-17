package edu.duke.ece651.team14.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.BasicUnit;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public class OrderVerifierTest {

  public Map makeTestMap(ArrayList<Player> players) {
    MapFactory f = new MapFactory();
    Map map = f.makeMap("test", players);
    return map;
  }
  
  @Test
  public void test_verifyOrder() {
    // Make a simple map
    Player p1 = new BasicPlayer(new Color("blue"), "p1");
    Player p2 = new BasicPlayer(new Color("red"), "p2");
    ArrayList<Player> players = new ArrayList<>();
    players.add(p1);
    players.add(p2);
    Map map = makeTestMap(players);
    // Add some units to the territories
    Territory t6 = map.getTerritoryByName("6");
    Territory t4 = map.getTerritoryByName("4");
    for (int i = 0; i < 5; i++) {
      t6.addUnits(new BasicUnit());
    }
    // Construct an OrderVerifier using the test map
    OrderVerifier verifier = new OrderVerifier(map);
    // Make a simple MoveOrder
    MoveOrder o1 = new MoveOrder(t6, t4, 3, p2);
    MoveOrder o2 = new MoveOrder(t6, t4, 2, p2);
    MoveOrder o3 = new MoveOrder(t6, t4, 1, p2);
    assertEquals(null, verifier.verifyOrder(o1));
    assertEquals(1, verifier.getNumVerifiedOrders());
    String errorMessage = "Not enough units of given type in destination territory";
    assertEquals(null, verifier.verifyOrder(o2));
    assertEquals(2, verifier.getNumVerifiedOrders());
    assertEquals(errorMessage, verifier.verifyOrder(o3));
  }
  
}

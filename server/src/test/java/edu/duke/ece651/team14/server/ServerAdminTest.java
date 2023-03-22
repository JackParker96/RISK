package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.BasicUnit;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.DestinationOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.MoveOrderPathExistsRuleChecker;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.OriginOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public class ServerAdminTest {
  @Test
  public void test_constructor() throws IOException {
    ServerAdmin serverAdmin = null;
    try {
      serverAdmin = new ServerAdmin(12345);
      assertEquals(12345, serverAdmin.serverSocket.getLocalPort());
    } finally {
      serverAdmin.releaseResources();
    }
  }

  public void createBasicMocks(ServerSocket mockServerSocket) throws IOException, ClassNotFoundException {
    // create mocks
    Socket mockClientSocket = mock(Socket.class);
    FileOutputStream output = new FileOutputStream("test.txt");
    FileInputStream input = new FileInputStream("test.txt");
    // declare when to use mocks
    Mockito.when(mockServerSocket.accept()).thenReturn(mockClientSocket);
    Mockito.when(mockClientSocket.getInputStream()).thenReturn(input);
    Mockito.when(mockClientSocket.getOutputStream()).thenReturn(output);
  }

  @Test
  public void test_acceptPlayers() throws IOException, ClassNotFoundException {
    ServerSocket mockServerSocket = mock(ServerSocket.class);
    createBasicMocks(mockServerSocket);
    ServerAdmin sa = new ServerAdmin(mockServerSocket);
    sa.acceptPlayersPhase(3);
    assertEquals(3, sa.clientSockets.size());
    sa.releaseResources();
  }

  // TODO: finish writing this test once method is completedp
  @Disabled
  @Test
  public void test_initializeGame() throws IOException, ClassNotFoundException {
    ServerSocket mockServerSocket = mock(ServerSocket.class);
    createBasicMocks(mockServerSocket);
    ServerAdmin sa = new ServerAdmin(mockServerSocket);
    sa.acceptPlayersPhase(2);
    sa.initializeGamePhase();
    sa.releaseResources();
  }

  // TODO: write this test once method is completed
  @Test
  public void test_receivePlacementOrders() throws IOException {
  }

  // @Disabled
  // @Test
  // public void test_resolveAllMoveOrders() throws IOException {
  //   ServerAdmin s = new ServerAdmin(1550);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");

  //   ArrayList<Player> players = new ArrayList<Player>();
  //   players.add(p1);
  //   players.add(p2);

  //   MapFactory m = new MapFactory();
  //   Map map = m.makeMap("test", players);

  //   OrderRuleChecker checker = new OriginOwnershipRuleChecker(
  //       new DestinationOwnershipRuleChecker(new MoveOrderPathExistsRuleChecker(null)));

  //   Territory t0 = map.getTerritoryByName("0");
  //   Territory t1 = map.getTerritoryByName("1");
  //   Territory t2 = map.getTerritoryByName("2");
  //   Territory t4 = map.getTerritoryByName("4");
  //   Territory t5 = map.getTerritoryByName("5");

  //   addUnits(t0, t1);

  //   MoveOrder m1 = new MoveOrder(t0, t1, 5, p1);
  //   MoveOrder m2 = new MoveOrder(t1, t2, 11, p1);
  //   //MoveOrder m3 = new MoveOrder(t4, t1, 8, p1);

  //   t4.addUnits(new BasicUnit());
  //   t4.addUnits(new BasicUnit());

  //   MoveOrder p2m1 = new MoveOrder(t4, t5, 2, p2);

  //   ArrayList<Order> moveOrders = new ArrayList<>();
  //   moveOrders.add(m1);
  //   moveOrders.add(m2);
  //   //moveOrders.add(m3);

  //   ArrayList<Order> p2moveOrders = new ArrayList<>();
  //   p2moveOrders.add(p2m1);

  //   HashMap<String, ArrayList<Order>> orders = new HashMap<>();
  //   orders.put("move", moveOrders);
  //   orders.put("attack", new ArrayList<Order>());

  //   HashMap<String, ArrayList<Order>> p2orders = new HashMap<>();
  //   p2orders.put("move", p2moveOrders);
  //   p2orders.put("attack", new ArrayList<Order>());

  //   HashMap<Player, HashMap<String, ArrayList<Order>>> allOrders = new HashMap<>();
  //   allOrders.put(p1, orders);
  //   allOrders.put(p2, p2orders);

  //   s.resolveAllMoveOrders(allOrders, map);

  //   assertEquals(t2.getUnits().size(), 11);
  //   assertEquals(t1.getUnits().size(), 2);
  //   assertEquals(t4.getUnits().size(), 0);
  //   assertEquals(t5.getUnits().size(), 2);
  // }

  // @Disabled
  // @Test
  // public void test_resolveOnePlayerMoveOrders() throws IOException {
  //   ServerAdmin s = new ServerAdmin(1549);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");

  //   ArrayList<Player> players = new ArrayList<Player>();
  //   players.add(p1);
  //   players.add(p2);

  //   MapFactory m = new MapFactory();
  //   Map map = m.makeMap("test", players);

  //   OrderRuleChecker checker = new OriginOwnershipRuleChecker(
  //       new DestinationOwnershipRuleChecker(new MoveOrderPathExistsRuleChecker(null)));

  //   Territory t0 = map.getTerritoryByName("0");
  //   Territory t1 = map.getTerritoryByName("1");
  //   Territory t2 = map.getTerritoryByName("2");
  //   Territory t4 = map.getTerritoryByName("4");

  //   addUnits(t0, t1);

  //   MoveOrder m1 = new MoveOrder(t0, t1, 5, p1);
  //   MoveOrder m2 = new MoveOrder(t1, t2, 11, p1);
  //   //MoveOrder m3 = new MoveOrder(t4, t1, 8, p1);

  //   ArrayList<Order> moveOrders = new ArrayList<>();
  //   moveOrders.add(m1);
  //   moveOrders.add(m2);
  //   //moveOrders.add(m3);

  //   HashMap<String, ArrayList<Order>> orders = new HashMap<>();
  //   orders.put("move", moveOrders);
  //   orders.put("attack", new ArrayList<Order>());

  //   s.resolveOnePlayerMoveOrders(orders, map, checker);

  //   assertEquals(t2.getUnits().size(), 11);
  //   assertEquals(t1.getUnits().size(), 2);
  //   assertEquals(t4.getUnits().size(), 0);
  // }

  // @Disabled
  // @Test
  // public void test_resolveMoveOrder() throws IOException {
  //   ServerAdmin s = new ServerAdmin(1543);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");

  //   ArrayList<Player> players = new ArrayList<Player>();
  //   players.add(p1);
  //   players.add(p2);

  //   MapFactory m = new MapFactory();
  //   Map map = m.makeMap("test", players);

  //   Territory t0 = map.getTerritoryByName("0");
  //   Territory t1 = map.getTerritoryByName("1");
  //   Territory t2 = map.getTerritoryByName("2");
  //   Territory t4 = map.getTerritoryByName("4");

  //   addUnits(t0, t1);

  //   MoveOrder mO1 = new MoveOrder(t1, t0, 1, p1, "basic");

  //   OrderRuleChecker checker = new OriginOwnershipRuleChecker(
  //       new DestinationOwnershipRuleChecker(new MoveOrderPathExistsRuleChecker(new NumberOfUnitsRuleChecker(null))));

  //   s.resolveMoveOrder(mO1, map, checker);

  //   assertEquals(t1.getUnits().size(), 7);
  //   assertEquals(t0.getUnits().size(), 8);

  //   MoveOrder mO2 = new MoveOrder(t0, t2, 7, p1, "basic");

  //   s.resolveMoveOrder(mO2, map, checker);

  //   assertEquals(t2.getUnits().size(), 7);
  //   assertEquals(t0.getUnits().size(), 1);

  //   MoveOrder mO3 = new MoveOrder(t2, t4, 1, p1, "basic");

  //   assertThrows(IllegalArgumentException.class,()->s.resolveMoveOrder(mO3, map, checker));
  //   assertEquals(t4.getUnits().size(), 0);
  //   assertEquals(t2.getUnits().size(), 7);

  // }

  @Disabled
  @Test
  // Tests sendMap()
  public void test_sendMap() throws IOException {
    ServerAdmin s = new ServerAdmin(1222);

    Communicator c1 = mock(Communicator.class);
    Communicator c2 = mock(Communicator.class);

    ArrayList<Communicator> communicators = new ArrayList<>();
    communicators.add(c1);
    communicators.add(c2);

    Player p1 = new BasicPlayer(new Color("red"), "p1");
    Player p2 = new BasicPlayer(new Color("blue"), "p2");

    ArrayList<Player> players = new ArrayList<>();
    players.add(p1);
    players.add(p2);

    Map map = new Map(new ArrayList<Territory>(), "testMap");

    s.sendMap();

    Mockito.verify(c1).sendObject(map);
    Mockito.verify(c2).sendObject(map);
  }

  @Disabled
  @Test
  // Tests receiveAllOrders()
  public void test_receiveAllOrders() throws IOException, ClassNotFoundException {
    HashMap<Player, HashMap<String, ArrayList<Order>>> expected = new HashMap<>();
    Player p1 = new BasicPlayer(new Color("red"), "p1");
    Player p2 = new BasicPlayer(new Color("blue"), "p2");

    ServerAdmin s = new ServerAdmin(1432);
    ArrayList<Order> ordersList = getTestOrders();
    HashMap<String, ArrayList<Order>> testOrders = s.sortOrders(ordersList);
    expected.put(p1, testOrders);
    expected.put(p2, testOrders);

    Communicator c1 = mock(Communicator.class);
    Communicator c2 = mock(Communicator.class);

    Mockito.when(c1.recvOrders()).thenReturn(ordersList);
    Mockito.when(c2.recvOrders()).thenReturn(ordersList);

    HashMap<Player, Communicator> communicators = new HashMap<>();
    communicators.put(p1, c1);
    communicators.put(p2, c2);

    assertEquals(expected, s.receiveAllOrders());
  }

  
  // @Test
  // // Tests receiveOrdersFromOnePlayer()
  // public void test_receiveOrdersFromOnePlayer() throws IOException, ClassNotFoundException {
  //   Communicator c = mock(Communicator.class);
  //   ArrayList<Order> testOrders = getTestOrders();
  //   Mockito.when(c.recvOrders()).thenReturn(testOrders);

  //   ServerAdmin s = new ServerAdmin(1555);

  //   assertEquals(s.sortOrders(testOrders), s.receiveOrdersFromOnePlayer(c));
  // }

  @Test
  // Tests sortOrders()
  public void test_sortOrders() throws IOException {
    ArrayList<Order> testOrders = getTestOrders();
    HashMap<String, ArrayList<Order>> expected = new HashMap<>();
    expected.put("move", new ArrayList<Order>());
    expected.put("attack", new ArrayList<Order>());
    expected.get("move").add(testOrders.get(0));
    expected.get("move").add(testOrders.get(1));
    expected.get("attack").add(testOrders.get(2));
    expected.get("attack").add(testOrders.get(3));

    ServerAdmin s = new ServerAdmin(1234);

    assertEquals(expected, s.sortOrders(testOrders));
  }

  private ArrayList<Order> getTestOrders() {
    Order o1 = new MoveOrder(null, null, 1, null);
    Order o2 = new MoveOrder(null, null, 2, null);
    Order o3 = new AttackOrder(null, null, 3, null);
    Order o4 = new AttackOrder(null, null, 4, null);
    ArrayList<Order> orders = new ArrayList<>();
    orders.add(o1);
    orders.add(o2);
    orders.add(o3);
    orders.add(o4);
    return orders;
  }

}

package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.Order;
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

  // TODO: finish writing this test once method is completed
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

    s.sendMap(communicators, map);

    Mockito.verify(c1).sendObject(map);
    Mockito.verify(c2).sendObject(map);
  }

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

    assertEquals(expected, s.receiveAllOrders(communicators));
  }

  @Test
  // Tests receiveOrdersFromOnePlayer()
  public void test_receiveOrdersFromOnePlayer() throws IOException, ClassNotFoundException {
    Communicator c = mock(Communicator.class);
    ArrayList<Order> testOrders = getTestOrders();
    Mockito.when(c.recvOrders()).thenReturn(testOrders);

    ServerAdmin s = new ServerAdmin(1555);

    assertEquals(s.sortOrders(testOrders), s.receiveOrdersFromOnePlayer(c));
  }

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

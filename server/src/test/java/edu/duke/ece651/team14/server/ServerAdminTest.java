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

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;

public class ServerAdminTest {
  // @Test
  // public void test_constructor() throws IOException {
  //   ServerAdmin serverAdmin = null;
  //   try {
  //     serverAdmin = new ServerAdmin(12345);
  //     assertEquals(12345, serverAdmin.serverSocket.getLocalPort());
  //   } finally {
  //     serverAdmin.releaseResources();
  //   }
  // }

  // public void createBasicMocks(ServerSocket mockServerSocket) throws IOException, ClassNotFoundException {
  //   // create mocks
  //   Socket mockClientSocket = mock(Socket.class);
  //   FileOutputStream output = new FileOutputStream("test.txt");
  //   FileInputStream input = new FileInputStream("test.txt");
  //   // declare when to use mocks
  //   Mockito.when(mockServerSocket.accept()).thenReturn(mockClientSocket);
  //   Mockito.when(mockClientSocket.getInputStream()).thenReturn(input);
  //   Mockito.when(mockClientSocket.getOutputStream()).thenReturn(output);
  // }

  // @Test
  // public void test_acceptPlayers() throws IOException, ClassNotFoundException {
  //   ServerSocket mockServerSocket = mock(ServerSocket.class);
  //   createBasicMocks(mockServerSocket);
  //   ServerAdmin sa = new ServerAdmin(mockServerSocket);
  //   sa.acceptPlayersPhase(3);
  //   assertEquals(3, sa.clientSockets.size());
  //   sa.releaseResources();
  // }

  // @Test
  // public void test_receivePlacementOrders() throws IOException, ClassNotFoundException {
  //   ServerSocket mockServerSocket = mock(ServerSocket.class);
  //   ServerAdmin sa = new ServerAdmin(mockServerSocket);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");
  //   Communicator c1 = mock(Communicator.class);
  //   Communicator c2 = mock(Communicator.class);
  //   sa.playerCommunicators.put(p1, c1);
  //   sa.playerCommunicators.put(p2, c2);
  //   sa.map = new Map(new ArrayList<Territory>(), "testMap");
  //   Mockito.when(c1.recvUnitPOrder()).thenReturn(mock(UnitPlacementOrder.class));
  //   Mockito.when(c2.recvUnitPOrder()).thenReturn(mock(UnitPlacementOrder.class));
  //   sa.receivePlacementOrders();
  //   Mockito.verify(c1).recvUnitPOrder();
  //   Mockito.verify(c2).recvUnitPOrder();
  // }

  // @Test
  // public void test_initializeGame() throws IOException, ClassNotFoundException {
  //   ServerSocket mockServerSocket = mock(ServerSocket.class);
  //   ServerAdmin sa = new ServerAdmin(mockServerSocket);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");
  //   Communicator c1 = mock(Communicator.class);
  //   Communicator c2 = mock(Communicator.class);
  //   sa.playerCommunicators.put(p1, c1);
  //   sa.playerCommunicators.put(p2, c2);
  //   Mockito.when(c1.recvUnitPOrder()).thenReturn(mock(UnitPlacementOrder.class));
  //   Mockito.when(c2.recvUnitPOrder()).thenReturn(mock(UnitPlacementOrder.class));
  //   sa.initializeGamePhase();
  //   Mockito.verify(c1).sendObject(p1);
  //   Mockito.verify(c2).sendObject(p2);
  //   Mockito.verify(c1).sendObject(sa.map);
  //   Mockito.verify(c2).sendObject(sa.map);
  // }

  // @Test
  // public void test_executeOneTurn() throws IOException, ClassNotFoundException {
  //   ServerSocket mockServerSocket = mock(ServerSocket.class);
  //   ServerAdmin sa = new ServerAdmin(mockServerSocket);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");
  //   Communicator c1 = mock(Communicator.class);
  //   Communicator c2 = mock(Communicator.class);
  //   sa.playerCommunicators.put(p1, c1);
  //   sa.playerCommunicators.put(p2, c2);
  //   Territory t = new BasicTerritory("duke");
  //   ArrayList<Territory> world = new ArrayList<Territory>();
  //   assertEquals(0, t.getNumUnits());
  //   world.add(t);
  //   sa.map = new Map(world, "Earth");
  //   sa.executeOneTurn();
  //   Mockito.verify(c1).sendObject(sa.map);
  //   Mockito.verify(c2).sendObject(sa.map);
  //   assertEquals(1, t.getNumUnits());
  // }

  // @Test
  // public void test_playGamePhaseOneRound() throws IOException, ClassNotFoundException {
  //   ServerSocket mockServerSocket = mock(ServerSocket.class);
  //   ServerAdmin sa = new ServerAdmin(mockServerSocket);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");
  //   Communicator c1 = mock(Communicator.class);
  //   Communicator c2 = mock(Communicator.class);
  //   sa.playerCommunicators.put(p1, c1);
  //   sa.playerCommunicators.put(p2, c2);
  //   Territory t1 = new BasicTerritory("duke");
  //   t1.setOwner(p1);
  //   Territory t2 = new BasicTerritory("unc");
  //   t2.setOwner(p1);
  //   ArrayList<Territory> world = new ArrayList<Territory>();
  //   world.add(t1);
  //   world.add(t2);
  //   sa.map = new Map(world, "Earth");
  //   sa.playGamePhase();
  //   Mockito.verify(c1).sendObject("Gameover");
  //   Mockito.verify(c2).sendObject("Gameover");
  // }

  // @Test
  // public void test_sendMap() throws IOException {
  //   ServerSocket mockServerSocket = mock(ServerSocket.class);
  //   ServerAdmin sa = new ServerAdmin(mockServerSocket);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");
  //   Communicator c1 = mock(Communicator.class);
  //   Communicator c2 = mock(Communicator.class);
  //   sa.playerCommunicators.put(p1, c1);
  //   sa.playerCommunicators.put(p2, c2);
  //   assertEquals(2, sa.playerCommunicators.size());
  //   Map map = new Map(new ArrayList<Territory>(), "testMap");
  //   sa.map = map;
  //   sa.sendMap();
  //   Mockito.verify(c1).sendObject(map);
  //   Mockito.verify(c2).sendObject(map);
  // }

  // @Test
  // public void test_sendResults() throws IOException {
  //   ServerSocket mockServerSocket = mock(ServerSocket.class);
  //   ServerAdmin sa = new ServerAdmin(mockServerSocket);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");
  //   Communicator c1 = mock(Communicator.class);
  //   Communicator c2 = mock(Communicator.class);
  //   sa.playerCommunicators.put(p1, c1);
  //   sa.playerCommunicators.put(p2, c2);
  //   String results = "results";
  //   sa.sendResults(results);
  //   Mockito.verify(c1).sendObject("results");
  //   Mockito.verify(c2).sendObject("results");
  // }

  // @Test
  // public void test_receiveAllOrders() throws IOException, ClassNotFoundException {
  //   ServerSocket mockServerSocket = mock(ServerSocket.class);
  //   ServerAdmin sa = new ServerAdmin(mockServerSocket);
  //   Player p1 = new BasicPlayer(new Color("red"), "p1");
  //   Player p2 = new BasicPlayer(new Color("blue"), "p2");
  //   Communicator c1 = mock(Communicator.class);
  //   Communicator c2 = mock(Communicator.class);
  //   sa.playerCommunicators.put(p1, c1);
  //   sa.playerCommunicators.put(p2, c2);
  //   sa.receiveAllOrders();
  //   Mockito.verify(c1).recvOrders();
  //   Mockito.verify(c2).recvOrders();
  // }

  // @Test
  // public void test_sortOrders() throws IOException {
  //   ArrayList<Order> testOrders = getTestOrders();
  //   HashMap<String, ArrayList<Order>> expected = new HashMap<>();
  //   expected.put("move", new ArrayList<Order>());
  //   expected.put("attack", new ArrayList<Order>());
  //   expected.get("move").add(testOrders.get(0));
  //   expected.get("move").add(testOrders.get(1));
  //   expected.get("attack").add(testOrders.get(2));
  //   expected.get("attack").add(testOrders.get(3));

  //   ServerAdmin s = new ServerAdmin(1234);

  //   assertEquals(expected, s.sortOrders(testOrders));
  // }

  // private ArrayList<Order> getTestOrders() {
  //   Order o1 = new MoveOrder(null, null, 1, null);
  //   Order o2 = new MoveOrder(null, null, 2, null);
  //   Order o3 = new AttackOrder(null, null, 3, null);
  //   Order o4 = new AttackOrder(null, null, 4, null);
  //   ArrayList<Order> orders = new ArrayList<>();
  //   orders.add(o1);
  //   orders.add(o2);
  //   orders.add(o3);
  //   orders.add(o4);
  //   return orders;
  // }

}

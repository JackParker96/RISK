package edu.duke.ece651.team14.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.BasicUnit;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.Unit;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;

public class TextClientPlayerTest {

  // TODO: figure out how to test normal (non-mocked) constructor
  // Breaking on new Communicator line in constructor
  @Disabled
  @Test
  public void test_constructor() throws IOException {
    ServerSocket sock = new ServerSocket(4444);
    BufferedReader in = new BufferedReader(new StringReader("duke\n"));
    PrintStream out = new PrintStream(new ByteArrayOutputStream(), true);
    ClientPlayer cp = new TextClientPlayer("localhost", 4444, in, out);
    sock.close();
  }

  @Test
  public void test_placeUnits() throws FileNotFoundException, IOException, ClassNotFoundException {
    Socket mockClientSocket = mock(Socket.class);
    Communicator mockComuni = mock(Communicator.class);
    ArrayList<Player> players = new ArrayList<>();
    BasicPlayer thisPlayer = new BasicPlayer(new Color("red"), "red");
    BasicPlayer thatPlayer = new BasicPlayer(new Color("blue"), "blue");
    players.add(thisPlayer);
    players.add(thatPlayer);
    MapFactory f = new MapFactory();
    Map gameMap = f.makeMap("Earth", players);
    when(mockComuni.recvMap()).thenReturn(gameMap);
    when(mockComuni.recvBasicPlayer()).thenReturn(thisPlayer);
    BufferedReader input = new BufferedReader(new FileReader("input.txt"));
    ClientPlayer cp = new TextClientPlayer(mockClientSocket, mockComuni, input, System.out);
    cp.whoAmIPhase();
    cp.placeUnitsPhase();
    verify(mockComuni).recvBasicPlayer();
    cp.release();
  }

  @Test
  public void testEOF() throws FileNotFoundException, IOException, ClassNotFoundException {
    Socket mockClientSocket = mock(Socket.class);
    Communicator mockComuni = mock(Communicator.class);
    ArrayList<Player> players = new ArrayList<>();
    BasicPlayer thisPlayer = new BasicPlayer(new Color("red"), "red");
    BasicPlayer thatPlayer = new BasicPlayer(new Color("blue"), "blue");
    players.add(thisPlayer);
    players.add(thatPlayer);
    MapFactory f = new MapFactory();
    Map gameMap = f.makeMap("Earth", players);
    when(mockComuni.recvMap()).thenReturn(gameMap);
    when(mockComuni.recvBasicPlayer()).thenReturn(thisPlayer);
    BufferedReader input = new BufferedReader(new StringReader("3\n"));
    ClientPlayer cp = new TextClientPlayer(mockClientSocket, mockComuni, input, System.out);
    cp.whoAmIPhase();
    assertThrows(EOFException.class, () -> cp.placeUnitsPhase());
    cp.release();
  }

  // ALL COMMENTED CODE BELOW IS TESTS FOR OLD IMPLEMENTATION OF PROCESSING MOVE
  // ORDERS

  /**
   * // Messages sent as output to the user that are useful to be able to call on
   * for
   * // the various tests in this file
   * String moveIntro = "Time to place move orders! You can enter as many move
   * orders as you'd like\n";
   * String intro = "Type 'D' if you're done committing move orders for this turn.
   * Type anything else to begin creating a new move order\n";
   * String from = "Territory to move units from:\n";
   * String to = "Territory to move units to:\n";
   * String num = "Enter the number of units you want to send\n";
   * String badNum = "Please enter a valid number\n";
   * String dontOwn = "You do not own that territory\n";
   * String atLeastOne = "You must send at least one unit\n";
   * String badPath = "There is no valid path between origin and destination. Try
   * again.\n";
   * String tooMany = "You're trying to send more units than you have\n";
   * String tooManyTotal = "During this turn, you have moved too many units out of
   * the origin you specified\n";
   * 
   * 
   * public TextClientPlayer createTextClientPlayer(String inputData, Color c,
   * OutputStream bytes) throws IOException {
   * Player p = new BasicPlayer(c, "A");
   * BufferedReader in = new BufferedReader(new StringReader(inputData));
   * PrintStream out = new PrintStream(bytes, true);
   * TextClientPlayer tcp = new TextClientPlayer(null, null, in, out);
   * tcp.myPlayer = p;
   * return tcp;
   * }
   * 
   * public void putUnitsOnTerr(Territory t, int n) {
   * ArrayList<Unit> units = new ArrayList<>();
   * for (int i = 0; i < n; i++) {
   * units.add(new BasicUnit());
   * }
   * t.addUnits(units);
   * }
   * 
   * public String test_getMoveOrder_helper(ByteArrayOutputStream bytes, String
   * input) throws IOException {
   * MapFactory f = new MapFactory();
   * TextClientPlayer tcp = createTextClientPlayer(input, new Color("blue"),
   * bytes);
   * Player p1 = tcp.myPlayer;
   * Player p2 = new BasicPlayer(new Color("red"), "p2");
   * ArrayList<Player> players = new ArrayList<>();
   * players.add(p1);
   * players.add(p2);
   * Map testMap = f.makeMap("test", players);
   * // I'm going to add one more territory to test map owned by p1 so that I can
   * // test what happens when a player tries to move units along an invalid path
   * // (there are no invalid paths in testMap right now)
   * Territory t = new BasicTerritory("7");
   * assertEquals("7", t.getName());
   * t.addAdjacentTerritories(testMap.getTerritoryByName("5"));
   * testMap.getTerritoryByName("5").addAdjacentTerritories(t);
   * t.setOwner(p1);
   * testMap.getMap().put("7", t);
   * // Put 5 units on each territory of the map
   * for (int i = 0; i < 8; i++) {
   * Territory terr = testMap.getTerritoryByName(String.valueOf(i));
   * putUnitsOnTerr(terr, 5);
   * }
   * OrderVerifier verifier = new OrderVerifier(testMap);
   * MoveOrder order = tcp.getMoveOrder(testMap, verifier);
   * return bytes.toString();
   * }
   * 
   * @Disabled
   * @Test
   *       public void test_doMoveOrdersPhase() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       MapFactory f = new MapFactory();
   *       TextClientPlayer tcp = createTextClientPlayer("c\n0\n1\n5\nd\n", new
   *       Color("blue"), bytes);
   *       Player p1 = tcp.myPlayer;
   *       Player p2 = new BasicPlayer(new Color("red"), "p2");
   *       ArrayList<Player> players = new ArrayList<>();
   *       players.add(p1);
   *       players.add(p2);
   *       Map testMap = f.makeMap("test", players);
   *       putUnitsOnTerr(testMap.getTerritoryByName("0"), 5);
   *       OrderVerifier verifier = new OrderVerifier(testMap);
   *       tcp.doMoveOrdersPhase(testMap, verifier);
   *       }
   * 
   * @Test
   *       public void test_getAllMoveOrders() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       MapFactory f = new MapFactory();
   *       String input1 = "c\n0\n1\n3\n";
   *       String input2 = "c\n0\n1\n3\n2\n";
   *       String input3 = "c\n1\n2\n6\nd\n";
   *       String input = input1 + input2 + input3;
   *       TextClientPlayer tcp = createTextClientPlayer(input, new Color("blue"),
   *       bytes);
   *       Player p1 = tcp.myPlayer;
   *       Player p2 = new BasicPlayer(new Color("red"), "p2");
   *       ArrayList<Player> players = new ArrayList<>();
   *       players.add(p1);
   *       players.add(p2);
   *       Map testMap = f.makeMap("test", players);
   *       Territory t = new BasicTerritory("7");
   *       assertEquals("7", t.getName());
   *       t.addAdjacentTerritories(testMap.getTerritoryByName("5"));
   *       testMap.getTerritoryByName("5").addAdjacentTerritories(t);
   *       t.setOwner(p1);
   *       testMap.getMap().put("7", t);
   *       for (int i = 0; i < 8; i++) {
   *       Territory terr = testMap.getTerritoryByName(String.valueOf(i));
   *       putUnitsOnTerr(terr, 5);
   *       }
   *       OrderVerifier verifier = new OrderVerifier(testMap);
   *       ArrayList<MoveOrder> orders = tcp.getAllMoveOrders(testMap, verifier);
   *       assertEquals(3, orders.size());
   *       String expected1 = moveIntro + intro + from + to + num;
   *       String expected2 = intro + from + to + num + tooMany + num;
   *       String expected3 = intro + from + to + num + intro;
   *       String expected = expected1 + expected2 + expected3;
   *       assertEquals(expected, bytes.toString());
   *       // Check Order 1
   *       assertEquals(testMap.getTerritoryByName("0"),
   *       orders.get(0).getOrigin());
   *       assertEquals(testMap.getTerritoryByName("1"),
   *       orders.get(0).getDestination());
   *       assertEquals(p1, orders.get(0).getPlayer());
   *       assertEquals(3, orders.get(0).getNumUnits());
   *       // Check Order 2
   *       assertEquals(testMap.getTerritoryByName("0"),
   *       orders.get(1).getOrigin());
   *       assertEquals(testMap.getTerritoryByName("1"),
   *       orders.get(1).getDestination());
   *       assertEquals(p1, orders.get(1).getPlayer());
   *       assertEquals(2, orders.get(1).getNumUnits());
   *       // Check Order 3
   *       assertEquals(testMap.getTerritoryByName("1"),
   *       orders.get(2).getOrigin());
   *       assertEquals(testMap.getTerritoryByName("2"),
   *       orders.get(2).getDestination());
   *       assertEquals(p1, orders.get(2).getPlayer());
   *       assertEquals(6, orders.get(2).getNumUnits());
   *       }
   * 
   *       // Testing getMoveOrder when everything entered by user is legal
   * @Test
   *       public void test_getMoveOrder_simple() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       String input = "c\n0\n1\n2\n";
   *       String actual = test_getMoveOrder_helper(bytes, input);
   *       String expected = intro + from + to + num;
   *       assertEquals(expected, actual);
   *       }
   * 
   * @Test
   *       public void test_getMoveOrder_done() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       String input = "d\n";
   *       String actual = test_getMoveOrder_helper(bytes, input);
   *       String expected = intro;
   *       assertEquals(expected, actual);
   *       }
   * 
   * @Test
   *       public void test_getMoveOrder_invalidOrigin() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       String input = "c\n6\n0\n1\n5\n";
   *       String expected = intro + from + dontOwn + from + to + num;
   *       String actual = test_getMoveOrder_helper(bytes, input);
   *       assertEquals(expected, actual);
   *       }
   * 
   * @Test
   *       public void test_getMoveOrder_invalidDest() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       String input = "c\n0\n6\n3\n5\n";
   *       String expected = intro + from + to + dontOwn + to + num;
   *       String actual = test_getMoveOrder_helper(bytes, input);
   *       assertEquals(expected, actual);
   *       }
   * 
   * @Test
   *       public void test_getMoveOrder_tooMany() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       String input = "c\n0\n3\n6\n5\n";
   *       String expected = intro + from + to + num + tooMany + num;
   *       String actual = test_getMoveOrder_helper(bytes, input);
   *       assertEquals(expected, actual);
   *       }
   * 
   * @Test
   *       public void test_getMoveOrder_badPath() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       String input = "c\n2\n7\n3\n2\n0\n5\n";
   *       String expected = intro + from + to + num + badPath + from + to + num;
   *       String actual = test_getMoveOrder_helper(bytes, input);
   *       assertEquals(expected, actual);
   *       }
   * 
   * @Test
   *       public void test_getMoveOrder_tooManyTotal() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       MapFactory f = new MapFactory();
   *       String input = "c\n0\n1\n3\nc\n0\n2\n3\n2\n";
   *       TextClientPlayer tcp = createTextClientPlayer(input, new Color("blue"),
   *       bytes);
   *       Player p1 = tcp.myPlayer;
   *       Player p2 = new BasicPlayer(new Color("red"), "p2");
   *       ArrayList<Player> players = new ArrayList<>();
   *       players.add(p1);
   *       players.add(p2);
   *       Map testMap = f.makeMap("test", players);
   *       Territory t = new BasicTerritory("7");
   *       assertEquals("7", t.getName());
   *       t.addAdjacentTerritories(testMap.getTerritoryByName("5"));
   *       testMap.getTerritoryByName("5").addAdjacentTerritories(t);
   *       t.setOwner(p1);
   *       testMap.getMap().put("7", t);
   *       for (int i = 0; i < 8; i++) {
   *       Territory terr = testMap.getTerritoryByName(String.valueOf(i));
   *       putUnitsOnTerr(terr, 5);
   *       }
   *       OrderVerifier verifier = new OrderVerifier(testMap);
   *       // Order #1
   *       MoveOrder order1 = tcp.getMoveOrder(testMap, verifier);
   *       String expected1 = intro + from + to + num;
   *       assertEquals(expected1, bytes.toString());
   *       assertEquals(testMap.getTerritoryByName("0"), order1.getOrigin());
   *       assertEquals(testMap.getTerritoryByName("1"), order1.getDestination());
   *       assertEquals(3, order1.getNumUnits());
   *       assertEquals(p1, order1.getPlayer());
   *       // Order #2
   *       MoveOrder order2 = tcp.getMoveOrder(testMap, verifier);
   *       String expected2 = expected1 + intro + from + to + num + tooMany + num;
   *       assertEquals(expected2, bytes.toString());
   *       }
   * 
   * @Test
   *       public void test_getOrderType() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       TextClientPlayer tcp =
   *       createTextClientPlayer("ATTack\nmove\ncommit\natack\nmv\ncommit\n", new
   *       Color("blue"),
   *       bytes);
   *       String prompt = "Enter order type ('move', 'attack', or 'commit' - to
   *       stop entering orders for this turn):\n";
   *       String errMessage = "Invalid move type\n";
   *       String expected = prompt + prompt + prompt + prompt + errMessage +
   *       prompt + errMessage + prompt;
   *       assertEquals("attack", tcp.getOrderType());
   *       assertEquals("move", tcp.getOrderType());
   *       assertEquals("commit", tcp.getOrderType());
   *       assertEquals("commit", tcp.getOrderType());
   *       assertEquals(expected, bytes.toString());
   *       }
   * 
   * @Test
   *       public void test_getNumUnitsToSend() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       TextClientPlayer tcp =
   *       createTextClientPlayer("\n2s\ntwo\n0\n-1\n1\n3\n4\n2\n", new
   *       Color("blue"), bytes);
   *       // Make two territores and set their owners
   *       Territory gondor = new BasicTerritory("gondor");
   *       Territory mordor = new BasicTerritory("mordor");
   *       mordor.setOwner(new BasicPlayer(new Color("green"), "p"));
   *       gondor.setOwner(tcp.myPlayer);
   *       // Put some units on Gondor
   *       putUnitsOnTerr(gondor, 3);
   *       // Make sure we throw an IllegalArgumentException if the player doesn't
   *       own the
   *       // territory
   *       assertThrows(IllegalArgumentException.class, () ->
   *       tcp.getNumUnitsToSend(mordor));
   *       // Now let's check our other error handling
   *       String prompt = "Enter the number of units you want to send\n";
   *       String invalidNum = "Please enter a valid number\n";
   *       String negative = "You must send at least one unit\n";
   *       String over = tooMany;
   *       String expected = prompt + invalidNum + prompt + invalidNum + prompt +
   *       invalidNum + prompt + negative + prompt
   *       + negative + prompt + prompt + prompt + over + prompt;
   *       assertEquals(1, tcp.getNumUnitsToSend(gondor));
   *       assertEquals(3, tcp.getNumUnitsToSend(gondor));
   *       assertEquals(2, tcp.getNumUnitsToSend(gondor));
   *       assertEquals(expected, bytes.toString());
   *       }
   * 
   * @Test
   *       public void test_askForTerritory() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       TextClientPlayer tcp = createTextClientPlayer("duke\ngondor\n", new
   *       Color("blue"), bytes);
   *       BasicTerritory gondor = new BasicTerritory("gondor");
   *       ArrayList<Territory> terrs = new ArrayList<>();
   *       terrs.add(gondor);
   *       Map m = new Map(terrs, "map");
   *       String prompt = "Enter the name of a territory on the map";
   *       assertEquals(gondor, tcp.askForTerritory(prompt, m));
   *       String expected = prompt + "\n" + "Territory does not exist in Map\n" +
   *       prompt + "\n";
   *       assertEquals(expected, bytes.toString());
   *       }
   * 
   * @Test
   *       public void test_askForTerritoryOwnedByPlayer() throws IOException {
   *       ByteArrayOutputStream bytes = new ByteArrayOutputStream();
   *       TextClientPlayer tcp = createTextClientPlayer("duke\ngondor\nmordor\n",
   *       new Color("blue"), bytes);
   *       BasicTerritory gondor = new BasicTerritory("gondor");
   *       BasicTerritory mordor = new BasicTerritory("mordor");
   *       mordor.setOwner(tcp.myPlayer);
   *       gondor.setOwner(new BasicPlayer(new Color("red"), "p"));
   *       ArrayList<Territory> terrs = new ArrayList<>();
   *       terrs.add(gondor);
   *       terrs.add(mordor);
   *       Map m = new Map(terrs, "map");
   *       String prompt = "Enter the name of a territory that you own";
   *       assertEquals(mordor, tcp.askForTerritoryOwnedByPlayer(prompt, m));
   *       String expected = prompt + "\n" + "Territory does not exist in Map\n" +
   *       prompt + "\n"
   *       + "You do not own that territory\n" + prompt + "\n";
   *       assertEquals(expected, bytes.toString());
   *       }
   */

}

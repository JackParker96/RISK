package edu.duke.ece651.team14.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.BasicUnit;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.Unit;

public class ClientMoveOrderProcessorTest {

  String intro = "Time to place all your move orders for this turn\n";
  String doneOrNot = "Type 'D' if you're done entering move orders for this turn. Type anything else to continue entering orders\n";
  String originTerr = "Origin territory for order:\n";
  String destTerr = "Destination territory for order:\n";
  String num = "Number of units to send:\n";
  
  public TextClientPlayer createTextClientPlayer(String inputData, Color c, OutputStream bytes) throws IOException {
    Player p = new BasicPlayer(c, "A");
    BufferedReader in = new BufferedReader(new StringReader(inputData));
    PrintStream out = new PrintStream(bytes, true);
    TextClientPlayer tcp = new TextClientPlayer(null, null, in, out);
    tcp.myPlayer = p;
    return tcp;
  }

  public void putUnitsOnTerr(Territory t, int n) {
    ArrayList<Unit> units = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      units.add(new BasicUnit());
    }
    t.addUnits(units);
  }

  public String helper(ByteArrayOutputStream bytes, String input) throws IOException {
    MapFactory f = new MapFactory();
    TextClientPlayer tcp = createTextClientPlayer(input, new Color("blue"), bytes);
    Player p1 = tcp.myPlayer;
    Player p2 = new BasicPlayer(new Color("red"), "p2");
    ArrayList<Player> players = new ArrayList<>();
    players.add(p1);
    players.add(p2);
    Map testMap = f.makeMap("test", players);
    // I'm going to add one more territory to test map owned by p1 so that I can
    // test what happens when a player tries to move units along an invalid path
    // (there are no invalid paths in testMap right now)
    Territory t = new BasicTerritory("7");
    assertEquals("7", t.getName());
    t.addAdjacentTerritories(testMap.getTerritoryByName("5"));
    testMap.getTerritoryByName("5").addAdjacentTerritories(t);
    t.setOwner(p1);
    testMap.getMap().put("7", t);
    // Put 5 units on each territory of the map
    for (int i = 0; i < 8; i++) {
      Territory terr = testMap.getTerritoryByName(String.valueOf(i));
      putUnitsOnTerr(terr, 5);
    }
    ClientMoveOrderProcessor processor = new ClientMoveOrderProcessor(tcp, testMap);
    processor.processAllOrdersForOneTurn("move");
    return bytes.toString();
  }

  @Test
  public void test_processSimpleMoveOrder() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String input = "c\n0\n1\n5\nd\n";
    String expected = intro + doneOrNot + originTerr + "Setting Origin: 0\n"+ destTerr + "Setting Destination: 1\n" + num + "Sending 5 units\n" + doneOrNot;
    String actual = helper(bytes, input);
    assertEquals(expected, actual);
  }

  @Test
  public void test_originErrors() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    String input = "c\ngondor\n1\n";
    String expected = "";
    String actual = helper(bytes, input);
    assertEquals(expected, actual);
  }

}

package edu.duke.ece651.team14.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;

public class TextClientPlayerTest {

  /**
   * Helper method to create a simple TextClientPlayer
   */
  public TextClientPlayer createTextClientPlayer(String inputData, Color c, OutputStream bytes) throws IOException {
    Player p = new BasicPlayer(c, "A");
    BufferedReader in = new BufferedReader(new StringReader(inputData));
    PrintStream out = new PrintStream(bytes, true);
    TextClientPlayer tcp = new TextClientPlayer(null, null, in, out);
    tcp.myPlayer = p;
    return tcp;
  }

  // TODO: figure out how to test normal (non-mocked) constructor
  /*
   * @Test
   * public void test_constructor() throws IOException {
   * ServerSocket sock = new ServerSocket(4444);
   * BufferedReader in = new BufferedReader(new StringReader("duke\n"));
   * PrintStream out = new PrintStream(new ByteArrayOutputStream(), true);
   * ClientPlayer cp = new TextClientPlayer("localhost", 4444, in, out);
   * sock.close();
   * }
   */

  @Test
  public void test_getOrderType() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextClientPlayer tcp = createTextClientPlayer("ATTack\nmove\ncommit\natack\nmv\ncommit\n", new Color("blue"),
        bytes);
    String prompt = "Enter order type ('move', 'attack', or 'commit' - to stop entering orders for this turn):\n";
    String errMessage = "Invalid move type\n";
    String expected = prompt + prompt + prompt + prompt + errMessage + prompt + errMessage + prompt;
    assertEquals("attack", tcp.getOrderType());
    assertEquals("move", tcp.getOrderType());
    assertEquals("commit", tcp.getOrderType());
    assertEquals("commit", tcp.getOrderType());
    assertEquals(expected, bytes.toString());
  }

  @Test
  public void test_askForTerritory() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextClientPlayer tcp = createTextClientPlayer("duke\ngondor\n", new Color("blue"), bytes);
    BasicTerritory gondor = new BasicTerritory("gondor");
    ArrayList<Territory> terrs = new ArrayList<>();
    terrs.add(gondor);
    Map m = new Map(terrs, "map");
    String prompt = "Enter the name of a territory on the map";
    assertEquals(gondor, tcp.askForTerritory(prompt, m));
    String expected = prompt + "\n" + "Territory does not exist in Map\n" + prompt + "\n";
    assertEquals(expected, bytes.toString());
  }

  @Test
  public void test_askForTerritoryOwnedByPlayer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextClientPlayer tcp = createTextClientPlayer("duke\ngondor\nmordor\n", new Color("blue"), bytes);
    BasicTerritory gondor = new BasicTerritory("gondor");
    BasicTerritory mordor = new BasicTerritory("mordor");
    mordor.setOwner(tcp.myPlayer);
    gondor.setOwner(new BasicPlayer(new Color("red"), "p"));
    ArrayList<Territory> terrs = new ArrayList<>();
    terrs.add(gondor);
    terrs.add(mordor);
    Map m = new Map(terrs, "map");
    String prompt = "Enter the name of a territory that you own";
    assertEquals(mordor, tcp.askForTerritoryOwnedByPlayer(prompt, m));
    String expected = prompt + "\n" + "Territory does not exist in Map\n" + prompt + "\n"
        + "You do not own that territory\n" + prompt + "\n";
    assertEquals(expected, bytes.toString());
  }

  @Test
  public void test_placeUnits() throws FileNotFoundException, IOException, ClassNotFoundException {
    Socket mockClientSocket = mock(Socket.class);
    Communicator mockComuni = mock(Communicator.class);
    ArrayList<Territory> terrs = new ArrayList<>();
    Territory t = new BasicTerritory("t");
    t.setOwner(new BasicPlayer(new Color("yellow"), "yellow"));
    terrs.add(t);
    when(mockComuni.recvMap()).thenReturn(new Map(terrs, "Earth"));
    when(mockComuni.recvBasicPlayer()).thenReturn(new BasicPlayer(new Color("yellow"), "yellow"));
    UnitPlacementOrder upo = new UnitPlacementOrder();
    for (int i = 0; i < 12; i++) {// the input file expects to fill 12 territories
      upo.addOneTerrPlacement("testT", 0);
    }
    when(mockComuni.recvUnitPOrder()).thenReturn(upo);
    BufferedReader input = new BufferedReader(new FileReader("input.txt"));
    ClientPlayer cp = new TextClientPlayer(mockClientSocket, mockComuni, input, System.out);
    cp.placeUnitsPhase();
    cp.whoAmIPhase();
    verify(mockComuni).recvBasicPlayer();
    cp.release();
  }

  @Test
  public void testEOF() throws FileNotFoundException, IOException, ClassNotFoundException {
    Socket mockClientSocket = mock(Socket.class);
    Communicator mockComuni = mock(Communicator.class);
    ArrayList<Territory> terrs = new ArrayList<>();
    Territory t = new BasicTerritory("t");
    t.setOwner(new BasicPlayer(new Color("yellow"), "yellow"));
    terrs.add(t);
    when(mockComuni.recvMap()).thenReturn(new Map(terrs, "Earth"));
    when(mockComuni.recvBasicPlayer()).thenReturn(new BasicPlayer(new Color("yellow"), "yellow"));
    UnitPlacementOrder upo = new UnitPlacementOrder();
    for (int i = 0; i < 12; i++) {// the input file expects to fill 12 territories
      upo.addOneTerrPlacement("testT", 0);
    }
    when(mockComuni.recvUnitPOrder()).thenReturn(upo);
    BufferedReader input = new BufferedReader(new StringReader("3\n"));
    ClientPlayer cp = new TextClientPlayer(mockClientSocket, mockComuni, input, System.out);
    assertThrows(EOFException.class, () -> cp.placeUnitsPhase());
  }

}

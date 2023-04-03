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

  @Test
  public void test_wantsToDisconnect() throws IOException {
    Player p1 = new BasicPlayer(new Color("blue"), "Xincheng");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    BufferedReader in = new BufferedReader(new StringReader("d\nD\ne\n"));
    TextClientPlayer tcp = new TextClientPlayer(null, null, in, out);
    tcp.myPlayer = p1;
    assertEquals(true, tcp.wantsToDisconnect());
    assertEquals(true, tcp.wantsToDisconnect());
    assertEquals(false, tcp.wantsToDisconnect());
  }
  
  @Test
  public void test_winIO() {
    MapFactory f = new MapFactory();
    Player p1 = new BasicPlayer(new Color("blue"), "Xincheng");
    Player p2 = new BasicPlayer(new Color("red"), "Maya");
    ArrayList<Player> players = new ArrayList<>();
    players.add(p1);
    players.add(p2);
    Map map = f.makeMap("test", players);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    BufferedReader in = new BufferedReader(new StringReader("test\n"));
    TextClientPlayer tcp = new TextClientPlayer(null, null, in, out);
    tcp.myPlayer = p1;
    assertThrows(IllegalArgumentException.class, () -> tcp.displayLossInfo(map));
    assertThrows(IllegalArgumentException.class, () -> tcp.displayWinInfo(map));
    map.getTerritoryByName("6").setOwner(p1);
    assertThrows(IllegalArgumentException.class, () -> tcp.displayLossInfo(map));
    assertThrows(IllegalArgumentException.class, () -> tcp.displayWinInfo(map));
    map.getTerritoryByName("4").setOwner(p1);
    assertThrows(IllegalArgumentException.class, () -> tcp.displayLossInfo(map));
    assertThrows(IllegalArgumentException.class, () -> tcp.displayWinInfo(map));
    map.getTerritoryByName("5").setOwner(p1);
    assertThrows(IllegalArgumentException.class, () -> tcp.displayLossInfo(map));
    tcp.displayWinInfo(map);
    String expected = "Xincheng has won the game!\n";
    assertEquals(expected, bytes.toString());
    map.getTerritoryByName("0").setOwner(p2);
    map.getTerritoryByName("1").setOwner(p2);
    map.getTerritoryByName("2").setOwner(p2);
    map.getTerritoryByName("3").setOwner(p2);
    map.getTerritoryByName("4").setOwner(p2);
    map.getTerritoryByName("5").setOwner(p2);
    map.getTerritoryByName("6").setOwner(p2);
    tcp.displayLossInfo(map);
    String expected2 = expected
        + "You have lost! You may continue to watch the rest of the game, or you may choose to disconnect at any time\n";
    assertEquals(expected2, bytes.toString());
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
    //cp.whoAmIPhase();
    cp.myPlayer = thisPlayer;
    cp.placeUnitsPhase();
    //verify(mockComuni).recvBasicPlayer();
    cp.release();
  }

  // @Disabled
  // @Test
  // public void test_EOF() throws FileNotFoundException, IOException, ClassNotFoundException {
  //   Socket mockClientSocket = mock(Socket.class);
  //   Communicator mockComuni = mock(Communicator.class);
  //   ArrayList<Player> players = new ArrayList<>();
  //   BasicPlayer thisPlayer = new BasicPlayer(new Color("red"), "red");
  //   BasicPlayer thatPlayer = new BasicPlayer(new Color("blue"), "blue");
  //   players.add(thisPlayer);
  //   players.add(thatPlayer);
  //   MapFactory f = new MapFactory();
  //   Map gameMap = f.makeMap("Earth", players);
  //   when(mockComuni.recvMap()).thenReturn(gameMap);
  //   when(mockComuni.recvBasicPlayer()).thenReturn(thisPlayer);
  //   BufferedReader input = new BufferedReader(new StringReader("3\n"));
  //   ClientPlayer cp = new TextClientPlayer(mockClientSocket, mockComuni, input, System.out);
  //   cp.whoAmIPhase();
  //   assertThrows(EOFException.class, () -> cp.placeUnitsPhase());
  //   cp.release();
  // }  
}

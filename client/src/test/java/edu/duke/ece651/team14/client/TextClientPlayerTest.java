package edu.duke.ece651.team14.client;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.BasicTerritory;

public class TextClientPlayerTest {
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

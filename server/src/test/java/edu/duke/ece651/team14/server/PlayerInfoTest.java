package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Player;

public class PlayerInfoTest {
  Socket mockSocket;
  Communicator mockCommun;
  Player p;
  OutputStream out;
  InputStream in;

  @BeforeEach
  public void init() throws IOException{
    this.mockSocket = mock(Socket.class);
    this.mockCommun = mock(Communicator.class);
    this.p = new BasicPlayer(new Color("yellow"), "text-yellow-player");
    this.out = new FileOutputStream("test.txt"); 
    this.in = new FileInputStream("test.txt");
    when(mockSocket.getInputStream()).thenReturn(in);
    when(mockSocket.getOutputStream()).thenReturn(out);
  }

  @Test
  public void test_player_info() {
    PlayerInfo pinfo = new PlayerInfo(mockSocket, p, mockCommun);
    assertTrue(pinfo.isConnected());
    pinfo.setDisconnected();
    assertFalse(pinfo.isConnected());
    pinfo.setConnected();
    assertTrue(pinfo.isConnected());
    assertEquals(p, pinfo.getPlayer());
    assertEquals(mockCommun, pinfo.getCommunicator());
    assertSame(mockSocket, pinfo.getThisSocket());
  }

  @Test
  public void test_player_info2() throws IOException{
    PlayerInfo pinfo = new PlayerInfo(mockSocket, p);
    assertSame(mockSocket, pinfo.getThisSocket());
  }

}

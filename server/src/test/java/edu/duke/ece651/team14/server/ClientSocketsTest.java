package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ClientSocketsTest {
  Socket s1 = mock(Socket.class);
  Socket s2 = mock(Socket.class);
  Socket s3 = mock(Socket.class);
  @BeforeEach
  public void init() throws IOException{
    doThrow(IOException.class).when(s1).close();
  }
  @Test
  public void test_clientsockets() {
    ClientSockets cs = new ClientSockets();
    cs.addSocket(s1);
    cs.addSocket(s2);
    cs.addSocket(s3);
    assertEquals(3, cs.getSize());
    cs.removeSocket(s1);
    cs.removeSocket(s2);
    cs.removeSocket(s3);
    assertEquals(0, cs.getSize());
  }

}

package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

  @Test
  public void test_accecptPlayers() throws IOException {
    Socket mockClientSocket = mock(Socket.class);
    InputStream mockIn = mock(InputStream.class);
    OutputStream mockOut = mock(OutputStream.class);
    ServerSocket mockServerSocket = mock(ServerSocket.class);    
    Mockito.when(mockServerSocket.accept()).thenReturn(mockClientSocket);
    Mockito.when(mockClientSocket.getInputStream()).thenReturn(mockIn);
    Mockito.when(mockClientSocket.getOutputStream()).thenReturn(mockOut);
    ServerAdmin sa = new ServerAdmin(4444);
    sa.serverSocket = mockServerSocket;
    //sa.acceptPlayersPhase(2);
    //does not complie, don't know why
  }
}

package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.duke.ece651.team14.shared.Communicator;

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
  public void test_acceptPlayers() throws IOException {
    // create mocks
    ServerSocket mockServerSocket = mock(ServerSocket.class);
    Socket mockClientSocket = mock(Socket.class);
    //InputStream mockIn = mock(InputStream.class);
    OutputStream mockOut = mock(OutputStream.class);
    // declare when to use mocks
    Mockito.when(mockServerSocket.accept()).thenReturn(mockClientSocket);
    //Mockito.when(mockClientSocket.getInputStream()).thenReturn(mockIn);
    Mockito.when(mockClientSocket.getOutputStream()).thenReturn(mockOut);

    InputStream input = new ByteArrayInputStream("test".getBytes());
    ServerAdmin sa = new ServerAdmin(mockServerSocket, input, mockOut);
    sa.acceptPlayersPhase(2); // does not compile
  }
}

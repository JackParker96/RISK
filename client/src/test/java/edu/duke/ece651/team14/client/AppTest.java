package edu.duke.ece651.team14.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.ServerSocket;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Disabled;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {
  @Test
  void test_getMessage() throws IOException {
    ClientPlayer mockPlayer = mock(TextClientPlayer.class);
    App a = new App(mockPlayer);
    assertEquals("Hello from the client for team14", a.getMessage());
  }

}

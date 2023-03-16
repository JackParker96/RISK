package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {
  @Test
  void test_getMessage() throws IOException {
    App a = new App(4444, 2); // need to use port number greater than 1024
    assertEquals("Hello from the server for team14", a.getMessage());
    a.serverAdmin.serverSocket.close();
  }

  @Test
  void test_incorrectPlayerNum() throws IOException, ClassNotFoundException {
    assertThrows(IllegalArgumentException.class, () -> new App(4444, 1));
    assertThrows(IllegalArgumentException.class, () -> new App(4444, 5));
  }

  @Test
  void test_mock() {
    @SuppressWarnings("unchecked")
    ArrayList<String> mockedList = mock(ArrayList.class);
    mockedList.add("one");
    mockedList.clear();

    verify(mockedList).add("one");
    verify(mockedList).clear();
  }

  //not sure how to make input and output files with server and clients
  @Disabled
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException, ClassNotFoundException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);

    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);

    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;

    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }

    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);
  }

}

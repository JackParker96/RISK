package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class AppTest {
  // @Test
  // void test_getMessage() throws IOException {
  //   App a = new App(4444, 2); // need to use port number greater than 1024
  //   assertEquals("Hello from the server for team14", a.getMessage());
  //   a.serverAdmin.serverSocket.close();
  // }

  // @Test
  // void test_incorrectPlayerNum() throws IOException, ClassNotFoundException {
  //   assertThrows(IllegalArgumentException.class, () -> new App(4444, 1));
  //   assertThrows(IllegalArgumentException.class, () -> new App(4444, 5));
  //   assertThrows(IllegalArgumentException.class, () -> new App(4443, 3));
  //   assertThrows(IllegalArgumentException.class, () -> new App(7000000, 5));
  //   assertThrows(IllegalArgumentException.class, () -> new App(1, 2));
  // }

  // @Test
  // void test_mock() {
  //   @SuppressWarnings("unchecked")
  //   ArrayList<String> mockedList = mock(ArrayList.class);
  //   mockedList.add("one");
  //   mockedList.clear();

  //   verify(mockedList).add("one");
  //   verify(mockedList).clear();
  // }
}

package edu.duke.ece651.team14.server;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import java.util.ArrayList;


import  org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AppTest {
  @Disabled
  @Test
  void test_getMessage() throws IOException{
    App a = new App(1);
    assertEquals("Hello from the server for team14", a.getMessage());
  }

  @Test
  void test_mock(){
    ArrayList mockedList = mock(ArrayList.class);
    mockedList.add("one");
    mockedList.clear();

    verify(mockedList).add("one");
    verify(mockedList).clear();
  }

  
}


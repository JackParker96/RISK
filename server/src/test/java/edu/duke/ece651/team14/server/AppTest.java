package edu.duke.ece651.team14.server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import  org.junit.jupiter.api.Disabled;

class AppTest {
  @Disabled
  @Test
  void test_getMessage() throws IOException{
    App a = new App(1);
    assertEquals("Hello from the server for team14", a.getMessage());
  }
}


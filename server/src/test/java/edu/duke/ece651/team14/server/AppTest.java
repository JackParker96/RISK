package edu.duke.ece651.team14.server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import  org.junit.jupiter.api.Disabled;

class AppTest {
  @Test
  void test_GetMessage() {
    App a = new App();
    assertEquals("Hello from the server for team14", a.getMessage());
  }
}


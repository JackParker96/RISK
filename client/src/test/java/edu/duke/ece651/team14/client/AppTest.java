package edu.duke.ece651.team14.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;

class AppTest {
  @Disabled
  @Test
  void test_GetMessage() throws IOException{
    App a = new App("host",1234);
    assertEquals("Hello from the client for team14", a.getMessage());
  }
}

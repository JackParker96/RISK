package edu.duke.ece651.teamX.server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Disabled

class AppTest {
    @Test
    @Disabled
    void test_GetMessage() {
      App a = new App();
      assertEquals("Hello from the server for teamX", a.getMessage());
    }
}


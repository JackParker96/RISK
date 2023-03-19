package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UnitsFactoryTest {
  @Test
  public void test_UnitsFactory() {
    AbstractUnitsFactory uf = new UnitsFactory();
    ArrayList<Unit> expected = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      expected.add(new BasicUnit());
    }
    assertEquals(expected, uf.makeUnits(5, "basic"));
    assertThrows(IllegalArgumentException.class, ()->uf.makeUnits(5, "whatever"));
  }

}

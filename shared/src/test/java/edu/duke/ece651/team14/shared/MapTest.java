package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class MapTest {
  @Test
  // Tests getName() method
  public void test_name() {
    Map m = new Map(new ArrayList<>(), "Earth");
    assertEquals(m.getName(), "Earth");
  }

  @Test
  // Tests getMap() method
  public void test_map() {
    ArrayList<Territory> t = new ArrayList<>();
    t.add(new Territory("Narnia"));
    Map m = new Map(t, "Earth");

    HashMap<String, Territory> expectedMap = new HashMap<>();
    expectedMap.put("narnia", t.get(0));
    
    assertEquals(m.getMap(), expectedMap);
  }
  

}

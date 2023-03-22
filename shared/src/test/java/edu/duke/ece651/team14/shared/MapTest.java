package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class MapTest {
  @Test
  public void test_addoneUnit(){
    MapFactory f = new MapFactory();
    Player p1 = new BasicPlayer(new Color("blue"), "p1");
    Player p2 = new BasicPlayer(new Color("red"), "p2");
    ArrayList<Player> players = new ArrayList<>();
    players.add(p1);
    players.add(p2);
    Map map = f.makeMap("test", players);
    map.allAddOneUnit();
    for(Territory t:map.getMap().values()){
      if(t.getName().equals("1")){
        assertEquals(2, t.getNumUnits());
      }else{
        assertEquals(1,t.getNumUnits());
      }
    }
  }

  @Test
  public void test_getWinner() {
    MapFactory f = new MapFactory();
    Player p1 = new BasicPlayer(new Color("blue"), "p1");
    Player p2 = new BasicPlayer(new Color("red"), "p2");
    ArrayList<Player> players = new ArrayList<>();
    players.add(p1);
    players.add(p2);
    Map map = f.makeMap("test", players);
    assertNull(map.getWinner());
    map.getTerritoryByName("6").setOwner(p1);
    assertNull(map.getWinner());
    map.getTerritoryByName("4").setOwner(p1);
    assertNull(map.getWinner());
    map.getTerritoryByName("5").setOwner(p1);
    assertEquals(p1, map.getWinner());
  }
  
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
    t.add(new BasicTerritory("Narnia"));
    Map m = new Map(t, "Earth");

    HashMap<String, Territory> expectedMap = new HashMap<>();
    expectedMap.put("narnia", t.get(0));
    
    assertEquals(m.getMap(), expectedMap);
  }

  @Test
  public void test_get_territory(){
    ArrayList<Territory> t = new ArrayList<>();
    BasicTerritory n = new BasicTerritory("Narnia");
    BasicTerritory o = new BasicTerritory("Oz");
    t.add(n);
    t.add(o);
    Map m = new Map(t, "Earth");
    assertEquals(o, m.getTerritoryByName("Oz"));
    assertEquals(n, m.getTerritoryByName("Narnia"));
    assertThrows(IllegalArgumentException.class, ()->m.getTerritoryByName("heaven"));
  }

  @Test
  // Tests equals() method
  public void test_equals() {
    ArrayList<Territory> t1 = new ArrayList<>();
    ArrayList<Territory> t2 = new ArrayList<>();
    BasicTerritory n = new BasicTerritory("Narnia");
    BasicTerritory o = new BasicTerritory("Oz");
    t1.add(n);
    t1.add(o);
    t2.add(o);
    t2.add(n);
    Map m1 = new Map(t1, "Earth");
    Map m2 = new Map(t2, "Earth");
    Map m3 = null;
    assertEquals(m1, m2);
    assertNotEquals(m1, m3);
  }

}

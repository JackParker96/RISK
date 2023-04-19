package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.AgressionPointMsg;
import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.MaxTechLevelException;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public class AgressionPointResolverTest {
  Map m;
  Player p1;
  Player p2;

  @BeforeEach
  public void init() {
    MapFactory f = new MapFactory();
    p1 = new BasicPlayer(new Color("yellow"), "yellow");
    p2 = new BasicPlayer(new Color("blue"), "blue");
    ArrayList<Player> players = new ArrayList<>();
    players.add(p1);
    players.add(p2);
    this.m = f.makeMap("test", players);
  }

  @Test
  public void test_agression_points() throws MaxTechLevelException{
    AgressionPointResolver r = new AgressionPointResolver(m);
    m.getMap().get("0").setOwner(p2);
    m.getMap().get("1").setOwner(p2);
    m.getMap().get("5").setOwner(p1);
    AgressionPointMsg msg = r.resolveAgressionPoint();
    assertEquals(1, msg.getPoint("yellow"));
    assertEquals(1, msg.getPoint("blue"));
  }

  @Test
  public void test_agression_points2() throws MaxTechLevelException{
    AgressionPointResolver r = new AgressionPointResolver(m);
    m.getMap().get("0").setOwner(p2);
    m.getMap().get("1").setOwner(p2);
    AgressionPointMsg msg = r.resolveAgressionPoint();
    assertEquals(0, msg.getPoint("yellow"));
    assertEquals(1, msg.getPoint("blue"));
  }

  @Test
  public void test_agression_points3() throws MaxTechLevelException{
    AgressionPointResolver r = new AgressionPointResolver(m);
    AgressionPointMsg msg = r.resolveAgressionPoint();
    assertEquals(0, msg.getPoint("yellow"));
    assertEquals(0, msg.getPoint("blue"));
  }

  @Test
  public void test_agression_points4() throws MaxTechLevelException{
    AgressionPointResolver r = new AgressionPointResolver(m);
    for (Territory t : m.getMap().values()) {
      t.setOwner(p1);
    }
    AgressionPointMsg msg = r.resolveAgressionPoint();
    assertEquals(1, msg.getPoint("yellow"));
    assertEquals(0, msg.getPoint("blue"));
  }

}

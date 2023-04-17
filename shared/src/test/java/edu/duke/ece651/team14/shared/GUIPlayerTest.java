package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class GUIPlayerTest {
  @Test
  public void test_aggPts() throws MaxTechLevelException {
    GameModel gm = new GameModel(null, 0, 0, 0, 0);
    GUIPlayer p = new GUIPlayer(new Color("blue"), "p", gm);
    assertEquals(0, p.model.getAggPts());
    p.addAggPt();
    assertEquals(1, p.model.getAggPts());
    p.addAggPt();
    assertEquals(2, p.model.getAggPts());
    p.resetAggPts();
    assertEquals(0, p.model.getAggPts());
  }

}

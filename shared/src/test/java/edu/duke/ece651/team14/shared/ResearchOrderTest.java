package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ResearchOrderTest {
  @Test
  public void test_researchOrder() throws MaxTechLevelException {
    Player p = new BasicPlayer(new Color("red"), "A");
    p.addTechResources(200);
    ResearchOrder order1 = new ResearchOrder(p);
    assertEquals(20, order1.calculateCost());
    assertEquals(1, p.getMaxTechLevel());
    MaxTechLevelRuleChecker c1 = new MaxTechLevelRuleChecker(null);
    ResearchOrderCostRuleChecker c2 = new ResearchOrderCostRuleChecker(null);
    assertEquals(null, c1.checkMyRule(null, order1));
    assertEquals(null, c2.checkMyRule(null, order1));
    p.increaseMaxTechLevel();
    assertEquals(2, p.getMaxTechLevel());
    ResearchOrder order2 = new ResearchOrder(p);
    assertEquals(40, order2.calculateCost());
    assertEquals(null, c1.checkMyRule(null, order1));
    assertEquals(null, c2.checkMyRule(null, order1));
    p.increaseMaxTechLevel();
    ResearchOrder order3 = new ResearchOrder(p);
    assertEquals(80, order3.calculateCost());
    assertEquals(null, c1.checkMyRule(null, order1));
    assertEquals(null, c2.checkMyRule(null, order1));
    p.increaseMaxTechLevel();
    ResearchOrder order4 = new ResearchOrder(p);
    assertEquals(160, order4.calculateCost());
    assertEquals(null, c1.checkMyRule(null, order1));
    assertEquals(null, c2.checkMyRule(null, order1));
    p.increaseMaxTechLevel();
    ResearchOrder order5 = new ResearchOrder(p);
    assertEquals(320, order5.calculateCost());
    assertEquals(null, c1.checkMyRule(null, order1));
    assertEquals("Not enough tech resources to upgrade max tech level", c2.checkMyRule(null, order1));
    p.increaseMaxTechLevel();
    assertThrows(MaxTechLevelException.class, () -> p.increaseMaxTechLevel());
    ResearchOrder order6 = new ResearchOrder(p);
    assertEquals("Tech level is already maxed out at 6", c1.checkMyRule(null, order6));
  }

}


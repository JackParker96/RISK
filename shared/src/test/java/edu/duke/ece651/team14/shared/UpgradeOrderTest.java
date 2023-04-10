package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UpgradeOrderTest {
  @Test
  public void test_upgradeOrder() throws MaxTechLevelException {
    Player p = new BasicPlayer(new Color("red"), "p");
    Player p2 = new BasicPlayer(new Color("green"), "p2");
    p.addTechResources(50);
    ArrayList<Player> players = new ArrayList<>();
    players.add(p);
    players.add(p2);
    MapFactory f = new MapFactory();
    Map m = f.makeMap("test", players);
    // Put some units on Territory 3
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit();
    Unit u3 = new BasicUnit();
    ArrayList<Unit> units = new ArrayList<>();
    units.add(u1);
    units.add(u2);
    units.add(u3);
    m.getTerritoryByName("3").addUnits(units);
    // Upgrade a single unit from level 0 to level 1
    UpgradeOrder order1 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 1, p, 0, 1);
    NumUnitsInTechLevelRuleChecker c1 = new NumUnitsInTechLevelRuleChecker(null);
    UpgradeOrderCostRuleChecker c2 = new UpgradeOrderCostRuleChecker(null);
    TechLevelRuleChecker c3 = new TechLevelRuleChecker(null);
    HasNecessaryMaxTechLevelRuleChecker c4 = new HasNecessaryMaxTechLevelRuleChecker(null);
    assertEquals(null, c1.checkMyRule(m, order1));
    assertEquals(null, c2.checkMyRule(m, order1));
    assertEquals(null, c3.checkMyRule(m, order1));
    assertEquals(null, c4.checkMyRule(m, order1));
    // Upgrade 2 units from level 3 to level 5
    u1.increaseTechLevel(3);
    u2.increaseTechLevel(3);
    UpgradeOrder order2 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 2, p, 3, 5);
    assertEquals(120, order2.calculateCost());
    assertEquals(null, c1.checkMyRule(m, order2));
    assertEquals("Not enough tech resources to upgrade units", c2.checkMyRule(m, order2));
    assertEquals(null, c3.checkMyRule(m, order2));
    assertEquals("Error: Player has max tech level 1 but is trying to upgrade unit(s) to level 5", c4.checkMyRule(m, order2));
    // Try to upgrade a unit past level 6
    u1.increaseTechLevel(3);
    UpgradeOrder order3 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 1, p, 6, 7);
    assertEquals("Tech levels are not valid", c3.checkMyRule(m, order3));
    // Try to upgrade too many units
    UpgradeOrder order4 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 4, p, 0, 1);
    assertEquals("Not enough units in specified tech level", c1.checkMyRule(m, order4));
    // Try to downgrade a unit
    UpgradeOrder order5 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 1, p, "basic", 1, 0);
    UpgradeOrder order6 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 1, p, 8, 9);
    UpgradeOrder order7 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 1, p, -1, 0);
    UpgradeOrder order8 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 1, p, 1, -1);
    UpgradeOrder order9 = new UpgradeOrder(m.getTerritoryByName("3"), new BasicTerritory("test"), 1, p, 1, 1);
    assertEquals("Tech levels are not valid", c3.checkMyRule(m, order5));
    assertEquals("Tech levels are not valid", c3.checkMyRule(m, order6));
    assertEquals("Tech levels are not valid", c3.checkMyRule(m, order7));
    assertEquals("Tech levels are not valid", c3.checkMyRule(m, order8));
    assertEquals("Tech levels are not valid", c3.checkMyRule(m, order9));
  }

}


package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/**
 * Class that checks to make sure there are enough units in specified tech level
 */
public class NumUnitsInTechLevelRuleChecker extends OrderRuleChecker {
  public NumUnitsInTechLevelRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that there are enough units in specified tech level
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if there are enough units in specified tech level, a String
   *         explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    UpgradeOrder upgradeOrder = (UpgradeOrder) order;
    int numUnitsInOrder = upgradeOrder.getNumUnits();
    ArrayList<Unit> unitsInTerr = upgradeOrder.getOrigin().getUnits();

    int maxUnitsAtLevel = 0;
    for (Unit u : unitsInTerr) {
      BasicUnit b = (BasicUnit) u;
      if (b.getTechLevel() == upgradeOrder.getCurrTechLevel()) {
        maxUnitsAtLevel++;
      }
    }
    
    if (numUnitsInOrder <= maxUnitsAtLevel) {
      return null;
    } else {
      return "Not enough units in specified tech level";
    }
    
  }

}

package edu.duke.ece651.team14.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Checks that number of units in given territory exceeds number request in
 * order
 */
public class NumberOfUnitsRuleChecker extends OrderRuleChecker {

  static HashMap<String, Unit> unitTypes;

  static {
    unitTypes = new HashMap<>();

    BasicUnit b = new BasicUnit();

    unitTypes.put("basic", b);
  }

  public NumberOfUnitsRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that destination territory has enough units of given type to complete
   * order
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if there are enough units, a String explaining broken rule
   *         otherwise
   */
  public String checkMyRule(Map map, Order order) {
    ArrayList<Unit> units = order.getDestination().getUnits();
    int numSameType = 0;
    for (Unit u : units) {
      if (u.getClass().equals(unitTypes.get(order.getUnitType()).getClass())) {
        numSameType++;
      }
    }
    if (numSameType <= order.getNumUnits()) {
      return null;
    } else {
      return "Not enough units of given type in destination territory";
    }
  }

}

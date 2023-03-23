package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/**
 * Checks that number of units in given territory exceeds number request in
 * order
 */
public class NumberOfUnitsRuleChecker extends OrderRuleChecker {

  /**
   * Creats a NumberOfUnitsRuleChecker with given next OrderRuleChecker in chain
   *
   * @param next is the next OrderRuleChecker in the chain
   */
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
    if (order.getNumUnits() < 0) {
      return "Order must have positive value for numUnits";
    }
    ArrayList<Unit> units = order.getOrigin().getUnits();
    int numSameType = 0;
    for (Unit u : units) {
      if (u.getType().equals(order.getUnitType())) {
        numSameType++;
      }
    }
    if (numSameType >= order.getNumUnits()) {
      return null;
    } else {
      return "Not enough units of given type in origin territory";
    }
  }

}

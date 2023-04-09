package edu.duke.ece651.team14.shared;

/**
 * Class that checks to make sure new tech level is greater than current tech
 * level
 */
public class TechLevelRuleChecker extends OrderRuleChecker {
  public TechLevelRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that new tech level is greater than current tech level
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the new tech level is greater than the current tech level, a String
   *         explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    UpgradeOrder upgradeOrder = (UpgradeOrder) order;
    if (upgradeOrder.getNewTechLevel() > upgradeOrder.getCurrTechLevel()) {
      return null;
    } else {
      return "New tech level is not greater than current tech level";
    }
  }
}

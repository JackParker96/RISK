package edu.duke.ece651.team14.shared;

/**
 * Class that checks to make sure new tech level is greater than current tech
 * level and that both are non-negative and less than 7
 */
public class TechLevelRuleChecker extends OrderRuleChecker {
  public TechLevelRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that new tech level is greater than current tech level and that both
   * are non-negative and less than 7
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the new tech level is greater than the current tech level and
   *         both are non-negative and less than 7, a String explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    UpgradeOrder upgradeOrder = (UpgradeOrder) order;
    if ((upgradeOrder.getNewTechLevel() > upgradeOrder.getCurrTechLevel()) && (upgradeOrder.getNewTechLevel() > -1) && (upgradeOrder.getNewTechLevel() < 7) && (upgradeOrder.getCurrTechLevel() > -1) && (upgradeOrder.getCurrTechLevel() < 7)) {
      return null;
    } else {
      return "Tech levels are not valid";
    }
  }
}

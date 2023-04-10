package edu.duke.ece651.team14.shared;

/**
 * Class that checks to make sure player can afford upgrade order
 */
public class UpgradeOrderCostRuleChecker extends OrderRuleChecker {
  public UpgradeOrderCostRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that player can afford upgrade order
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the player's total tech resources is greater or equal to cost, a
   *         String explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    UpgradeOrder ugOrder = (UpgradeOrder) order;
    int cost = ugOrder.calculateCost();
    int resources = ugOrder.getPlayer().getTechAmt();
    if (resources >= cost) {
      return null;
    } else {
      return "Not enough tech resources to upgrade units";
    }
  }
}

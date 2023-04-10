package edu.duke.ece651.team14.shared;

/**
 * Class that checks to make sure player can afford attack order
 */
public class AttackOrderCostRuleChecker extends OrderRuleChecker {
  public AttackOrderCostRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that player can afford attack order
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the player's total food resources is greater or equal to cost, a
   *         String explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    AttackOrder attackOrder = (AttackOrder) order;
    int cost = attackOrder.calculateCost();
    int resources = attackOrder.getPlayer().getFoodAmt();
    if (resources >= cost) {
      return null;
    } else {
      return "Not enough food resources to make this attack";
    }
  }
}

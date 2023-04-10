package edu.duke.ece651.team14.shared;

/**
 * Class that checks to make sure player can afford move order
 */
public class MoveOrderCostRuleChecker extends OrderRuleChecker {
  public MoveOrderCostRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that player can afford move order
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the player's total food resources is greater or equal to cost, a
   *         String explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    MoveOrder moveOrder = (MoveOrder) order;
    int cost = moveOrder.calculateCost();
    int resources = moveOrder.getPlayer().getFoodAmt();
    if (resources >= cost) {
      return null;
    } else {
      return "Not enough food resources to make this move";
    }
  }
}

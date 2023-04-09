package edu.duke.ece651.team14.shared;

public class ResearchOrderCostRuleChecker extends OrderRuleChecker {
  public ResearchOrderCostRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Check that player has enough tech resources to complete a research order
   */
  public String checkMyRule(Map map, Order order) {
    ResearchOrder resOrder = (ResearchOrder) order;
    int cost = resOrder.calculateCost();
    int resources = resOrder.getPlayer().getTechAmt();
    if (resources >= cost) {
      return null;
    } else {
      return "Not enough tech resources to upgrade max tech level";
    }
  }
}

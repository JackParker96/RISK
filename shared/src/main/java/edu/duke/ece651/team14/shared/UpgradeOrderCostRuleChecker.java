package edu.duke.ece651.team14.shared;

public class UpgradeOrderCostRuleChecker extends OrderRuleChecker {
  public UpgradeOrderCostRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  public String checkMyRule(Map map, Order order) {
    UpgradeOrder ugOrder = (UpgradeOrder) order;
    int cost = ugOrder.calculateCost();
    int resources = ugOrder.getPlayer().getTechAmt();
    if (resources >= cost) {
      return null;
    }
    else {
      return "Not enought tech resources to upgrade unit";
    }
  }
}

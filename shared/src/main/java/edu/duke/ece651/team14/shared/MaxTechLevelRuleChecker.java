package edu.duke.ece651.team14.shared;

public class MaxTechLevelRuleChecker extends OrderRuleChecker {
  public MaxTechLevelRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  public String checkMyRule(Map map, Order order) {
    if (order.getPlayer().getMaxTechLevel() < 6) {
      return null;
    } else {
      return "Tech level is already maxed out at 6";
    }
  }
}

package edu.duke.ece651.team14.shared;

public class AllianceWithSelfRuleChecker extends OrderRuleChecker {
  public AllianceWithSelfRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  // Check if the player is trying to form an alliance with themselves
  public String checkMyRule(Map map, Order order) {
    AllianceOrder allianceOrder = (AllianceOrder) order;
    if (!allianceOrder.getNewAlly().equals(allianceOrder.getPlayer())) {
      return null;
    } else {
      return "Player cannot form an alliance with themselves";
    }
  }
}

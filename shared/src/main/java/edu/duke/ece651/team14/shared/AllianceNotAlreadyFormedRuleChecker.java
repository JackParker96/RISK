package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

public class AllianceNotAlreadyFormedRuleChecker extends OrderRuleChecker {
  public AllianceNotAlreadyFormedRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  // Check if alliance has already been formed
  public String checkMyRule(Map map, Order order) {
    AllianceOrder allianceOrder = (AllianceOrder) order;
    Player p = allianceOrder.getPlayer();
    Player ally = allianceOrder.getNewAlly();
    ArrayList<Player> allies = p.getAllies();
    if (!allies.contains(ally)) {
      return null;
    } else {
      return "Alliance has already been formed";
    }
  }
}

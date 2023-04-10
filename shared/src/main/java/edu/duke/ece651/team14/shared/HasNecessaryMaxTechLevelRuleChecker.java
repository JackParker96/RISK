package edu.duke.ece651.team14.shared;

public class HasNecessaryMaxTechLevelRuleChecker extends OrderRuleChecker {
  public HasNecessaryMaxTechLevelRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that player issuing upgrade order has necessary max tech level
   */
  public String checkMyRule(Map map, Order order) {
    UpgradeOrder ugOrder = (UpgradeOrder) order;
    int playerLevel = ugOrder.getPlayer().getMaxTechLevel();
    int desiredLevel = ugOrder.getNewTechLevel();
    if (playerLevel >= desiredLevel) {
      return null;
    }
    else {
      return "Error: Player has max tech level " + playerLevel + " but is trying to upgrade unit(s) to level " + desiredLevel;
    }
  }
}

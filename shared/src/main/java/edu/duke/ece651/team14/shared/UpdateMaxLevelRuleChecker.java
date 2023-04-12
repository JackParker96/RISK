package edu.duke.ece651.team14.shared;

/**
 * To check the user can only upgrade the units to max tech level
 */
public class UpdateMaxLevelRuleChecker extends OrderRuleChecker {
  public UpdateMaxLevelRuleChecker(OrderRuleChecker next){
    super(next);
  }
  @Override
  protected String checkMyRule(Map map, Order order) {
    UpgradeOrder uo = (UpgradeOrder) order;
    int max_level = uo.getPlayer().getMaxTechLevel();
    int new_level = uo.getNewTechLevel();
    if(new_level > max_level){
      return "Upgrade level exceeds player's max tech level";
    }
    return null;
  }

}

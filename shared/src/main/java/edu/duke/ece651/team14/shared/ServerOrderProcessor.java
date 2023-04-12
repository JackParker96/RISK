package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

public class ServerOrderProcessor{
  protected OrderRuleChecker moveChecker;
  protected OrderRuleChecker attackChecker;
  protected OrderRuleChecker upgradeChecker;
  protected OrderRuleChecker researchChecker;
  public ServerOrderProcessor(Map map) {
    this.moveChecker = new OriginDestNotSameTerrRuleChecker(new OriginOwnershipRuleChecker(
        new DestinationOwnershipRuleChecker(new MoveOrderPathExistsRuleChecker(
            new NumberOfUnitsRuleChecker(null)))));
    this.attackChecker = new OriginOwnershipRuleChecker(new DestinationNotOwnedRuleChecker(
        new NumberOfUnitsRuleChecker(new AdjacentTerritoryRuleChecker(null))));
    this.upgradeChecker = new OriginOwnershipRuleChecker(new NumberOfUnitsRuleChecker(
        new TechLevelRuleChecker(new UpdateMaxLevelRuleChecker(new NumUnitsInTechLevelRuleChecker(null)))));
    this.researchChecker = new MaxTechLevelRuleChecker(null);
  }
  
  public void processUpgrade(Map map, Order o, Player p)  throws MaxTechLevelException{
    if (o instanceof UpgradeOrder) {
      String checkResult = upgradeChecker.checkOrder(map, o);
      if (checkResult != null) {
        throw new IllegalArgumentException(checkResult);
      }
      UpgradeOrder upgradeOrder = (UpgradeOrder) o;
      Territory t = upgradeOrder.getOrigin();
      int num_to_upgrade = upgradeOrder.getNumUnits();
      for (Unit u : t.getUnits()) {
        if (u.getTechLevel() == upgradeOrder.getCurrTechLevel()) {
          u.increaseTechLevel(upgradeOrder.getNewTechLevel() - upgradeOrder.getCurrTechLevel());
          num_to_upgrade--;
        }
        if (num_to_upgrade == 0) {
          break;
        }
      }
    } else {
      throw new IllegalArgumentException("Order type not supported");
    }
  }
  
  public void processMove(Map map, Order o, Player p) {
    if (o instanceof MoveOrder) {
      String checkResult = moveChecker.checkOrder(map, o);
      if (checkResult != null) {
        throw new IllegalArgumentException("In move: " + checkResult);
      }
      UnitMover.moveUnits(o.getOrigin(), o.getDestination(), o.getNumUnits(), "basic");
    } else {
      throw new IllegalArgumentException("Order type not supported");
    }
  }
}

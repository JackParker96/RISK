package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

public class GUIOrderprocessor {
  private ArrayList<Order> verifiedOrders;
  protected OrderRuleChecker moveChecker;
  protected OrderRuleChecker attackChecker;
  protected OrderRuleChecker upgradeChecker;
  protected OrderRuleChecker researchChecker;

  public GUIOrderprocessor(Map map) {
    this.verifiedOrders = new ArrayList<>();
    this.moveChecker = new OriginDestNotSameTerrRuleChecker(new OriginOwnershipRuleChecker(
        new DestinationOwnershipRuleChecker(new MoveOrderPathExistsRuleChecker(
            new NumberOfUnitsRuleChecker(new MoveOrderCostRuleChecker(null))))));
    this.attackChecker = new OriginOwnershipRuleChecker(new DestinationNotOwnedRuleChecker(
        new NumberOfUnitsRuleChecker(new AdjacentTerritoryRuleChecker(
            new AttackOrderCostRuleChecker(null)))));
    this.upgradeChecker = new OriginOwnershipRuleChecker(new NumberOfUnitsRuleChecker(
        new TechLevelRuleChecker(new UpdateMaxLevelRuleChecker(new NumUnitsInTechLevelRuleChecker(
            new UpgradeOrderCostRuleChecker(null))))));
    this.researchChecker = new MaxTechLevelRuleChecker(new ResearchOrderCostRuleChecker(null));
  }

  public int processResearch(Map map, Order o, Player p) throws MaxTechLevelException{
    if (o instanceof ResearchOrder) {
      String checkResult = researchChecker.checkOrder(map, o);
      if (checkResult != null) {
        throw new IllegalArgumentException("In research: " + checkResult);
      }
      ResearchOrder researchOrder = (ResearchOrder) o;
      int cost = researchOrder.calculateCost();
      p.useTechResources(cost);
      p.increaseMaxTechLevel();
      return cost;
    } else {
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  public int processUpgrade(Map map, Order o, Player p) throws MaxTechLevelException {
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
      int cost = upgradeOrder.calculateCost();
      p.useTechResources(cost);
      verifiedOrders.add(upgradeOrder);
      return cost;
    } else {
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  public int processAttack(Map map, Order o, Player p) {
    if (o instanceof AttackOrder) {
      String checkResult = attackChecker.checkOrder(map, o);
      if (checkResult != null) {
        throw new IllegalArgumentException("In attack: " + checkResult);
      }
      AttackOrder attackOrder = (AttackOrder) o;
      int cost = attackOrder.calculateCost();
      p.useFoodResources(cost);
      UnitMover.moveUnits(o.getOrigin(),new BasicTerritory("battlefield"), o.getNumUnits(), "basic");
      verifiedOrders.add(attackOrder);
      return cost;
    } else {
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  public int processMove(Map map, Order o, Player p) {
    if (o instanceof MoveOrder) {
      String checkResult = moveChecker.checkOrder(map, o);
      if (checkResult != null) {
        throw new IllegalArgumentException("In move: " + checkResult);
      }
      MoveOrder moveOrder = (MoveOrder) o;
      int cost = moveOrder.calculateCost();
      p.useFoodResources(cost);
      UnitMover.moveUnits(o.getOrigin(), o.getDestination(), o.getNumUnits(), "basic");
      verifiedOrders.add(moveOrder);
      return cost;
    } else {
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  public ArrayList<Order> getVerifiedOrders() {
    return this.verifiedOrders;
  }

  public void clearVerified() {
    this.verifiedOrders.clear();
  }
}

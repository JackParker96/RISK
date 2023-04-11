package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

public class GUIOrderprocessor {
  private ArrayList<Order> verifiedOrders;
  private OrderRuleChecker moveChecker = new OriginDestNotSameTerrRuleChecker(new OriginOwnershipRuleChecker(
      new DestinationOwnershipRuleChecker(new MoveOrderPathExistsRuleChecker(
          new NumberOfUnitsRuleChecker(new MoveOrderCostRuleChecker(null))))));
  private OrderRuleChecker attackChecker = new OriginOwnershipRuleChecker(new DestinationNotOwnedRuleChecker(
      new NumberOfUnitsRuleChecker(new AdjacentTerritoryRuleChecker(
          new AttackOrderCostRuleChecker(null)))));
  private OrderRuleChecker upgradeChecker = new OriginOwnershipRuleChecker(new NumberOfUnitsRuleChecker(
      new TechLevelRuleChecker(
          new NumUnitsInTechLevelRuleChecker(
              new UpgradeOrderCostRuleChecker(null)))));
  private OrderRuleChecker researchChecker = new MaxTechLevelRuleChecker(new ResearchOrderCostRuleChecker(null));

  public GUIOrderprocessor(Map map) {
    this.verifiedOrders = new ArrayList<>();
  }

  public void processResearch(Map map, Order o, Player p) {
    if (o instanceof ResearchOrder) {
      String checkResult = researchChecker.checkOrder(map, o);
      if (checkResult != null) {
        throw new IllegalArgumentException(checkResult);
      }
      ResearchOrder researchOrder = (ResearchOrder) o;
      int cost = researchOrder.calculateCost();
      p.useTechResources(cost);
      this.verifiedOrders.add(researchOrder);
    } else {
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  public void processUpgrade(Map map, Order o, Player p) throws MaxTechLevelException{
    if(o instanceof UpgradeOrder){
      String checkResult = upgradeChecker.checkOrder(map, o);
      if(checkResult != null){
        throw new IllegalArgumentException(checkResult);
      }
      UpgradeOrder upgradeOrder = (UpgradeOrder)o;
      Territory t = upgradeOrder.getOrigin();
      for(Unit u:t.getUnits()){
        if(u.getTechLevel()==upgradeOrder.getCurrTechLevel()){
          u.increaseTechLevel(upgradeOrder.getNewTechLevel()-upgradeOrder.getCurrTechLevel());
        }
      }
      verifiedOrders.add(upgradeOrder);
    }else{
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  public void processAttack(Map map,Order o,Player p){
    if(o instanceof AttackOrder){
      String checkResult = attackChecker.checkOrder(map, o);
      if(checkResult!=null){
        throw new IllegalArgumentException(checkResult);
      }
      AttackOrder attackOrder = (AttackOrder) o;
      int cost = attackOrder.calculateCost();
      p.useFoodResources(cost);
      ArrayList<Unit> unitsToSend = attackOrder.getUnitsPicked();
      UnitMover.sendUnitArray(o.getOrigin(), o.getDestination(), unitsToSend);
      verifiedOrders.add(attackOrder);
    }else{
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  public void processMove(Map map, Order o, Player p){
    if(o instanceof MoveOrder){
      String checkResult = moveChecker.checkOrder(map, o);
      if(checkResult !=null){
        throw new IllegalArgumentException(checkResult);
      }
      MoveOrder moveOrder = (MoveOrder)o;
      int cost = moveOrder.calculateCost();
      p.useFoodResources(cost);
      ArrayList<Unit> unitsToSend = moveOrder.getUnitsPicked();
      UnitMover.sendUnitArray(o.getOrigin(), o.getDestination(), unitsToSend);
      verifiedOrders.add(moveOrder);
    }else{
      throw new IllegalArgumentException("Order type not supported");
    }
  }

  public ArrayList<Order> getVerifiedOrders(){
    return this.verifiedOrders;
  }
}

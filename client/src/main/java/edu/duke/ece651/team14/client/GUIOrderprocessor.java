package edu.duke.ece651.team14.client;

import java.util.ArrayList;

import edu.duke.ece651.team14.shared.AdjacentTerritoryRuleChecker;
import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.AttackOrderCostRuleChecker;
import edu.duke.ece651.team14.shared.DestinationNotOwnedRuleChecker;
import edu.duke.ece651.team14.shared.DestinationOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MaxTechLevelRuleChecker;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.MoveOrderCostRuleChecker;
import edu.duke.ece651.team14.shared.MoveOrderPathExistsRuleChecker;
import edu.duke.ece651.team14.shared.NumUnitsInTechLevelRuleChecker;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.OriginDestNotSameTerrRuleChecker;
import edu.duke.ece651.team14.shared.OriginOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.ResearchOrder;
import edu.duke.ece651.team14.shared.ResearchOrderCostRuleChecker;
import edu.duke.ece651.team14.shared.TechLevelRuleChecker;
import edu.duke.ece651.team14.shared.Unit;
import edu.duke.ece651.team14.shared.UnitMover;
import edu.duke.ece651.team14.shared.UpgradeOrder;
import edu.duke.ece651.team14.shared.UpgradeOrderCostRuleChecker;

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

  public void processUpgrade(Map map, Order o, Player p){
    if(o instanceof UpgradeOrder){
      String checkResult = upgradeChecker.checkOrder(map, o);
      if(checkResult != null){
        throw new IllegalArgumentException(checkResult);
      }
      UpgradeOrder upgradeOrder = (UpgradeOrder)o;
      //TODO:take effects
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

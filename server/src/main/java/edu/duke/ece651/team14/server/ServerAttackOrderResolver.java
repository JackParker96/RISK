package edu.duke.ece651.team14.server;

import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.AdjacentTerritoryRuleChecker;
import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.BattleField;
import edu.duke.ece651.team14.shared.CombatResolver;
import edu.duke.ece651.team14.shared.DestinationNotOwnedRuleChecker;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.OriginOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UnitMover;

public class ServerAttackOrderResolver {
  private final Map map;
  private final CombatResolver resolver;

  /*
   * Constructor to resolve attack orders sent to server
   */
  public ServerAttackOrderResolver(Map map, CombatResolver resolver) {
    this.map = map;
    this.resolver = resolver;
  }

  /*
   * Resolves all attack orders for one turn
   *
   * @param orders: stores move and attack orders
   */
  public String resolveAllAttackOrders(ArrayList<Order> orders) {
    // run attack order checker again
    ArrayList<Order> badOrders = new ArrayList<Order>();
    OrderRuleChecker checker = new OriginOwnershipRuleChecker(new DestinationNotOwnedRuleChecker(new AdjacentTerritoryRuleChecker(new NumberOfUnitsRuleChecker(null))));
    for (Order o : orders) {
      String checkerResult = checker.checkOrder(this.map, o);
      if (checkerResult != null) {
        System.out.println("Bad Attack Order detected:"+o+checkerResult);
        badOrders.add(o);
      }
    }
    for (Order b : badOrders) {
      orders.remove(b);
    }
    // initialize all battlefields
    HashMap<String, BattleField> battleFields = new HashMap<>();
    for (Order o : orders) {
      String locationName = o.getDestination().getName();
      if (!battleFields.containsKey(locationName)) {// battle field not created yet
        Territory combatLocation = map.getTerritoryByName(locationName);
        BattleField field = new BattleField(resolver, combatLocation);
        battleFields.put(locationName, field);
      }
    }
    // process attackers
    for (Order o : orders) {
      String locationName = o.getDestination().getName();
      String attackerTerrName = o.getOrigin().getName();
      Territory attackerTerritory = map.getTerritoryByName(attackerTerrName);
      Territory blackHole = new BasicTerritory("black hole");
      //this territory is used to receive removed units, acts like an adapter for moveUnits
      //units are actually "moved" to battlefield
      UnitMover.moveUnits(attackerTerritory,blackHole, o.getNumUnits(), o.getUnitType());
      battleFields.get(locationName).addAttackerArmy((AttackOrder) o);
    }
    // combat start
    StringBuilder sb = new StringBuilder();
    for(BattleField field:battleFields.values()){
      field.resolve();
      sb.append(field.getResult());
      sb.append("\n");
    }
    return sb.toString();
  }
}

package edu.duke.ece651.team14.server;

import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.BattleField;
import edu.duke.ece651.team14.shared.CombatResolver;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UnitMover;

public class ServerAttackOrderResolver {
  private final Map map;
  private final CombatResolver resolver;
  
  public ServerAttackOrderResolver(Map map, CombatResolver resolver) {
    this.map = map;
    this.resolver = resolver;
  }

  public String resolveAllAttackOrders(ArrayList<Order> orders) {
    HashMap<String, BattleField> battleFields = new HashMap<>();
    // initialize all battlefields
    for (Order o : orders) {
      String locationName = o.getDestination().getName();
      if (!battleFields.containsKey(locationName)) {// battle field not created yet
        Territory combatLocation = map.getTerritoryByName(locationName);
        BattleField field = new BattleField(resolver, combatLocation);
        battleFields.put(locationName, field);
      }
    }
    // process attackers.
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
    // combat start.
    StringBuilder sb = new StringBuilder();
    for(BattleField field:battleFields.values()){
      field.resolve();
      sb.append(field.getResult());
      sb.append("\n");
    }
    return sb.toString();
  }
}

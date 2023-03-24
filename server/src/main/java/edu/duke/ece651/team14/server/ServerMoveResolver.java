package edu.duke.ece651.team14.server;

import java.util.ArrayList;

import edu.duke.ece651.team14.shared.DestinationOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MoveOrderPathExistsRuleChecker;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.OriginDestNotSameTerrRuleChecker;
import edu.duke.ece651.team14.shared.OriginOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UnitMover;

public class ServerMoveResolver {
  private final Map map;

  /**
   * Constructor to resolve move orders sent to server
   *
   * @param map: map of game
   */
  public ServerMoveResolver(Map map) {
    this.map = map;
  }

  /**
   * Resolves all move orders for one turn
   *
   * @param orders: stores move and attack orders
   *
   */
  public void resolveAllMoveOrders(ArrayList<Order> orders) {
    OrderRuleChecker checker = new OriginDestNotSameTerrRuleChecker(new OriginOwnershipRuleChecker(
        new DestinationOwnershipRuleChecker(new MoveOrderPathExistsRuleChecker(new NumberOfUnitsRuleChecker(null)))));
    for (Order o : orders) {
      resolveMoveOrder(o, checker);
    }
  }

  /**
   * Resolves a single move order
   * 
   * @param order:   a move order
   * @param checker: checks whether move order is legal
   * 
   * @throws IllegalArgumentException if something wrong with the order
   */
  public void resolveMoveOrder(Order order, OrderRuleChecker checker) {
    String checkerResult = checker.checkOrder(this.map, order);
    if (checkerResult == null) {
      Territory origin = map.getTerritoryByName(order.getOrigin().getName());
      Territory destination = map.getTerritoryByName(order.getDestination().getName());
      // received order with new territory object, not the one in the server's map
      UnitMover.moveUnits(origin, destination, order.getNumUnits(), order.getUnitType());
    } else {
      System.out.println("Bad move order: " + order + checkerResult);
    }
  }

}

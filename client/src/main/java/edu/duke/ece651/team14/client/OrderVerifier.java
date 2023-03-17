package edu.duke.ece651.team14.client;

import java.util.ArrayList;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;

/**
 * The OrderVerifier class can be used for verifying both attack and move orders
 * It is used to check that a player hasn't entered multiple orders in a single
 * turn that result in a single unit being in two places at once
 * For example, if a player has 5 units in a territory and makes two move orders
 * each moving 3 units out of that territory, this class can catch that
 */
public class OrderVerifier {
  private Map map;
  private NumberOfUnitsRuleChecker checker;
  private ArrayList<Order> verifiedOrders;

  // Construct an OrderVerifier
  public OrderVerifier(Map map) {
    this.map = map;
    this.checker = new NumberOfUnitsRuleChecker(null);
    this.verifiedOrders = new ArrayList<>();
  }

  /**
   * Checks that a player hasn't entered multiple orders in a single turn that
   * involve a single unit being in two places at once
   * For example, if a player has 5 units in one territory and makes two move
   * orders each moving 3 units out of that territory, this method will catch that
   */
  public String verifyOrder(Order order) {
    String checkResult = checker.checkMyRule(map, order);
    // If the order is valid, actually move those units on the map
    if (checkResult == null) {
      // UnitMover mover = new UnitMover();
      // UnitMover.moveUnits(map, order.getOrigin(), order.getDestination(),
      // order.getNumUnits());
      verifiedOrders.add(order);
    }
    return checkResult;
  }

}

package edu.duke.ece651.team14.shared;

/**
 * Class that checks to make sure two territories mentioned in an order are adjacent
 */
public class AdjacentTerritoryRuleChecker extends OrderRuleChecker {
  public AdjacentTerritoryRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that two territories mentioned in an order are adjacent to each other
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the two territories are adjacent, a String
   *         explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    Territory origin = map.getTerritoryByName(order.getOrigin().getName());
    Territory destination = map.getTerritoryByName(order.getDestination().getName());
    if (origin.isAdjacentTo(destination)) {
      return null;
    } else {
      return "Territories are not adjacent";
    }
  }
}

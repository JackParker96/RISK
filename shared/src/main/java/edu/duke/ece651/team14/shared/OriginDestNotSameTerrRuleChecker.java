package edu.duke.ece651.team14.shared;

/**
 * Class that checks to make sure the origin territory is not the same as the destination territory
 */
public class OriginDestNotSameTerrRuleChecker extends OrderRuleChecker {
  public OriginDestNotSameTerrRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that origin territory is not the same as the destination territory
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the origin territory is not the same as the destination territory, a String
   *         explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    if (!(order.getOrigin().equals(order.getDestination()))) {
      return null;
    } else {
      return "Origin territory is the same as the destination territory";
    }
  }
}

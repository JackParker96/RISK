package edu.duke.ece651.team14.shared;

/**
 * Class that checks to make sure player owns destination territory in order
 */
public class DestinationNotOwnedRuleChecker extends OrderRuleChecker {
  public DestinationNotOwnedRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that player does not own destination territory
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the player does not own the destination territory, a String
   *         explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    if (!(order.getPlayer().equals(order.getDestination().getOwner()))) {
      return null;
    } else {
      return "Player owns the destination territory";
    }
  }
}

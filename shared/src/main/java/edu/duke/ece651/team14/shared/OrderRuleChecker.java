package edu.duke.ece651.team14.shared;

/**
 * Abstract class to represent a single rule checker that can form a chain of
 * rules
 */
public abstract class OrderRuleChecker {

  private final OrderRuleChecker next;

  /**
   * Creates a MoveOrderRuleChecker with given next checker
   *
   * @param next is the next rule checker
   */
  public OrderRuleChecker(OrderRuleChecker next) {
    this.next = next;
  }

  /**
   * Method to check own rule. Returns a string if breaks rule, null otherwise.
   *
   * @param map   is the game map
   * @param order is the MoveOrder
   *
   * @return String if breaks rule, null otherwise
   */
  protected abstract String checkMyRule(Map map, Order order);

  /**
   * Checks every member of MoveOrderChecker chain
   *
   * @param map   is the game map
   * @param order is the MoveOrder
   *
   * @return String if breaks one of the rule checkers' rules, null otherwise
   */
  public String checkOrder(Map map, Order order) {
    String result = checkMyRule(map, order);
    if (result != null) {
      return result;
    }

    if (next != null) {
      return next.checkOrder(map, order);
    }

    return null;
  }

}

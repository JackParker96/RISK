package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/**
 * Class to check whether player owns Territories in some path from origin to destination on move order
 */
public class MoveOrderPathExistsRuleChecker extends OrderRuleChecker {

  /**
   * Creates a MoveOrderPathExistsRuleChecker with given next OrderRuleChecker
   */
  public MoveOrderPathExistsRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /**
   * Checks that player owns origin territory
   *
   * @param map   is the game map
   * @param order is the Order
   *
   * @return null if the player owns the destination territory, a String
   *         explaining broken rule otherwise
   */
  public String checkMyRule(Map map, Order order) {
    //Territory origin = order.getOrigin();
    //Territory destination = order.getDestination();
    Territory origin = map.getTerritoryByName(order.getOrigin().getName());
    Territory destination = map.getTerritoryByName(order.getDestination().getName());
    ArrayList<Territory> visited = new ArrayList<>();
    visited.add(origin);
    if (mapDFS(visited, origin, origin, destination)) {
      return null;
    } else {
      return "Player does not own path from origin to destination";
    }
  }

  /**
   * Helper method to run a DFS on the map through the player's territories
   *
   * @param visited     is a list of the visited territories owned by the player
   * @param origin      is the starting territory
   * @param current     is the current territory in the search
   * @param destination is the target of the search
   *
   * @return true if player owns a path from origin to destination, false
   *         otherwise
   */
  protected boolean mapDFS(ArrayList<Territory> visited, Territory origin, Territory current, Territory destination) {
    // For debugging
    // System.out.println(current.getName());
    if (current.equals(destination)) {
      return true;
    } else {
      // For debuggin
      // System.out.print(current.getName() + " " + destination.getName() + " ");
    }
    for (Territory t : current.getAdjacentTerritories()) {
      // System.out.println("Checking " + t);
      if (t.getOwner().equals(origin.getOwner()) && !visited.contains(t)) {
        visited.add(t);
        if (mapDFS(visited, origin, t, destination)) return true;
      }
    }
    return false;
  }

}

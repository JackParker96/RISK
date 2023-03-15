package edu.duke.ece651.team14.shared;

/**
 * Class to represent move order
 */
public class MoveOrder extends Order {

  public MoveOrder(Territory origin, Territory destination, Player player, int numUnits, String unitType) {
    super(origin, destination, player, numUnits, unitType);
  }

  public MoveOrder(Territory origin, Territory destination, int numUnits, Player player) {
    this(origin, destination, player, numUnits, "basic");
  }

}

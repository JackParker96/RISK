package edu.duke.ece651.team14.shared;

/**
 * Class to represent an attack order
 */
public class AttackOrder extends Order {

  public AttackOrder(Territory origin, Territory destination, Player player, int numUnits, String unitType) {
    super(origin, destination, player, numUnits, unitType);
  }

  public AttackOrder(Territory origin, Territory destination, int numUnits, Player player) {
    this(origin, destination, player, numUnits, "basic");
  }

}

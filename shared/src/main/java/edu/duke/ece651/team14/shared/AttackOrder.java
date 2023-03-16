package edu.duke.ece651.team14.shared;

/**
 * Class to represent an attack order
 */
public class AttackOrder extends Order {

  /**
   * Creates an AttackOrder
   *
   * @param origin      is the origin Territory
   * @param destination is the destination Territory
   * @param numUnits    is the number of units to send
   * @param player      is the player making the order
   * @param unitType    is the type of unit to send
   */
  public AttackOrder(Territory origin, Territory destination, int numUnits, Player player, String unitType) {
    super(origin, destination, numUnits, player, unitType, "attack");
  }

  /**
   * Creates an AttackOrder with numUnits of basic units
   *
   * @param origin      is the origin Territory
   * @param destination is the destination Territory
   * @param numUnits    is the number of units to send
   * @param player      is the player making the order
   */
  public AttackOrder(Territory origin, Territory destination, int numUnits, Player player) {
    super(origin, destination, numUnits, player, "attack");
  }

}

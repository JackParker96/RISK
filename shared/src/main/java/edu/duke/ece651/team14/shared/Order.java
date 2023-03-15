package edu.duke.ece651.team14.shared;

import java.io.Serializable;

/**
 * Abstract class to represent a generic order as part of a player's turn
 */
public abstract class Order implements Serializable {

  private Territory origin;
  private Territory destination;
  private int numUnits;
  private String unitType;
  private Player player;

  /**
   * Creates an Order
   *
   * @param origin      is the origin Territory
   * @param destination is the destination Territory
   * @param numUnits    is the number of units to send
   * @param player      is the player making the order
   * @param unitType    is the type of unit to send
   */
  public Order(Territory origin, Territory destination, int numUnits, Player player, String unitType) {
    this.origin = origin;
    this.destination = destination;
    this.numUnits = numUnits;
    this.player = player;
    this.unitType = unitType;
  }

  /**
   * Creates an Order with numUnits of basic units
   *
   * @param origin      is the origin Territory
   * @param destination is the destination Territory
   * @param numUnits    is the number of units to send
   * @param player      is the player making the order
   */
  public Order(Territory origin, Territory destination, int numUnits, Player player) {
    this(origin, destination, numUnits, player, "basic");
  }

  /**
   * Returns origin Territory
   *
   * @return origin Territory
   */
  public Territory getOrigin() {
    return origin;
  }

  /**
   * Returns destination Territory
   *
   * @return destination Territory
   */
  public Territory getDestination() {
    return destination;
  }

  /**
   * Returns number of units to send
   *
   * @return numUnits
   */
  public int getNumUnits() {
    return numUnits;
  }

  /**
   * Returns unit type to send
   *
   * @return unitType
   */
  public String getUnitType() {
    return unitType;
  }

  /**
   * Returns player making the order
   *
   * @return player
   */
  public Player getPlayer() {
    return player;
  }
}

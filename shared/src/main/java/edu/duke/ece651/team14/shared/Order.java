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

  public Order(Territory origin, Territory destination, Player player, int numUnits, String unitType) {
    this.origin = origin;
    this.destination = destination;
    this.numUnits = numUnits;
    this.player = player;
    this.unitType = unitType;
  }

  public Order(Territory origin, Territory destination, int numUnits, Player player) {
    this(origin, destination, player, numUnits, "basic");
  }

  public Territory getOrigin() {
    return origin;
  }

  public Territory getDestination() {
    return destination;
  }

  public int getNumUnits() {
    return numUnits;
  }

  public String getUnitType() {
    return unitType;
  }

  public Player getPlayer() {
    return player;
  }
}

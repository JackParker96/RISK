package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/**
 * A class to represent a player in the RISC game
 */
public class Player {
  // The color associated with the player
  private final Color color;
  // The territories currently owned by the player
  private ArrayList<Territory> territories;

  // Constructor for a Player that takes in a Color object
  public Player(Color color) {
    this.color = color;
    this.territories = new ArrayList<Territory>();
  }

  // Constructor for a Player that takes in a String
  public Player(String c) {
    Color color = new Color(c);
    this.color = color;
    this.territories = new ArrayList<Territory>();
  }

  /**
   * Try to add a territory to the list of territories controlled by the Player
   *
   * @param t is the territory we want to add
   * @return true if the territory is successfully added, return false otherwise
   * @throws IllegalArgumentException if the Player already contorls t
   */
  public boolean tryAddTerritory(Territory t) {
    if (territories.contains(t)) {
      throw new IllegalArgumentException(toString() + " already controls " + t.toString());
    }
    territories.add(t);
    return true;
  }

  /**
   * Try to remove a territory from the list of territories controlled by the
   * player
   *
   * @param t is the territory we want to remove
   * @return true if the territory is successfully removed, return false otherwise
   * @throws IllegalArgumentException if the player doesn't control t
   */
  public boolean tryRemoveTerritory(Territory t) {
    if (!territories.contains(t)) {
      throw new IllegalArgumentException(toString() + " does not control " + t.toString());
    }
    territories.remove(t);
    return true;
  }

  // Get the number of territories controlled by the player
  public int getNumTerritories() {
    return territories.size();
  }

  public Color getColor() {
    return color;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Player otherPlayer = (Player) other;
      return color.equals(otherPlayer.getColor());
    }
    return false;
  }

  @Override
  public String toString() {
    return color.toString();
  }

  @Override
  public int hashCode() {
    return color.hashCode();
  }
}
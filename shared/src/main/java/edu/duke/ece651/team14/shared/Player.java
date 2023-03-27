package edu.duke.ece651.team14.shared;

import java.io.Serializable;

import java.util.HashSet;

/**
 * A class to represent a player in the RISC game
 */
public abstract class Player implements Serializable {
  // Player color
  private final Color color;
  // Player name
  private final String name;
  // Number of food resources the player currently has
  private int foodAmt;
  // Number of tech resources the player currently has
  private int techAmt;
  // Current max tech level of the player
  private int maxTechLevel;

  /**
   * Creates a Player object from given color
   *
   * @param color is the player's color
   */
  public Player(Color color, String name) {
    this.color = color;
    this.name = name;
    this.foodAmt = 0;
    this.techAmt = 0;
    this.maxTechLevel = 1;
  }

  /**
   * Check if this Player has won the game
   *
   * @param m is the Map corresponding to the game
   * @return true if the Player has won, false if not
   */
  public boolean hasWon(Map m) {
    if (this.equals(m.getWinner())) {
      return true;
    }
    return false;
  }

  /**
   * Check if this Player has lost the game
   *
   * @param m is ther Map corresponding to the game
   * @return true if the Player has lost, false if not
   */
  public boolean hasLost(Map m) {
    HashSet<Player> nonLosers = new HashSet<>();
    for (Territory t : m.getMap().values()) {
      nonLosers.add(t.getOwner());
    }
    if (nonLosers.contains(this)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the player color
   *
   * @return Player's color
   */
  public Color getColor() {
    return color;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Player otherPlayer = (Player) other;
      return name.equals(otherPlayer.getName());
    }
    return false;
  }

  /**
   * Returns player name
   *
   * @return player name
   */
  public String getName() {
    return name;
  }

  public void addFoodResources(int toAdd) {
    if (toAdd < 0) {
      throw new IllegalArgumentException("Can't add a negative number of resources");
    }
    foodAmt += toAdd;
  }

  public void useFoodResources(int toUse) {
    if (toUse < 0) {
      throw new IllegalArgumentException("Can't use a negative number of resources");
    }
    if (toUse > foodAmt) {
      throw new IllegalArgumentException("Not enough food resources");
    }
    foodAmt -= toUse;
  }

  public void addTechResources(int toAdd) {
    if (toAdd < 0) {
      throw new IllegalArgumentException("Can't add a negative number of resources");
    }
    techAmt += toAdd;
  }

  public void useTechResources(int toUse) {
    if (toUse < 0) {
      throw new IllegalArgumentException("Can't use a negative number of resources");
    }
    if (toUse > techAmt) {
      throw new IllegalArgumentException("Not enough tech resources");
    }
    techAmt -= toUse;
  }

  public void increaseMaxTechLevel() {
    maxTechLevel += 1;
  }

  public int getFoodAmt() {
    return foodAmt;
  }

  public int getTechAmt() {
    return techAmt;
  }

  public int getMaxTechLevel() {
    return maxTechLevel;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}

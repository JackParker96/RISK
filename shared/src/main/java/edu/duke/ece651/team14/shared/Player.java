package edu.duke.ece651.team14.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

// Player
/**
 * A class to represent a player in the RISC game
 */
public abstract class Player implements Serializable {
  // Player color
  public final Color color;

  // Player name;color name
  public final String name;
  // Number of food resources the player currently has
  public int foodAmt;
  // Number of tech resources the player currently has
  public int techAmt;
  // Current max tech level of the player
  public int maxTechLevel;

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

  protected int mapDFS(ArrayList<Territory> visited, Territory origin, Territory curr,
      Territory dest, int distTraveled) {
    ArrayList<Integer> distances = new ArrayList<>();
    // System.out.println(visited);
    // System.out.println(curr);
    if (curr.equals(dest)) {
      // System.out.println(visited);
      // System.out.println(distTraveled);
      distances.add(distTraveled);
    }
    distTraveled++;
    for (Territory t : curr.getAdjacentTerritories()) {
      ArrayList<Territory> prevTerrs = new ArrayList<>();
      for (Territory terrr : visited) {
        prevTerrs.add(terrr);
      }
      if (t.getOwner().equals(origin.getOwner()) && !prevTerrs.contains(t)) {
        prevTerrs.add(t);
        // System.out.println(prevTerrs);
        int result = mapDFS(prevTerrs, origin, t, dest, distTraveled);
        if (result > -1) {
          distances.add(result);
        }
      }
    }
    if (distances.size() > 0) {
      return Collections.min(distances);
    }
    return -1;
  }

  // assume distances from one territory to each of its adjacent territories are
  // equal -> set to 1
  public int findShortestPath(Territory origin, Territory dest) {
    int distTraveled = 0;
    ArrayList<Territory> visited = new ArrayList<>();
    visited.add(origin);
    int result = mapDFS(visited, origin, origin, dest, distTraveled);
    return result;
  }

  /**
   * At the start of each turn, update the amount of resources each player has
   * based on their territories
   *
   * @param m is the map of all territories
   */
  public void updateResourcesInTurn(Map m) {
    ArrayList<Territory> myTerrs = m.groupTerritoriesByPlayer().get(this);
    int foodProduced = 0;
    int techProduced = 0;
    for (Territory t : myTerrs) {
      foodProduced += t.getFoodProductionRate();
      techProduced += t.getTechProductionRate();
    }
    addFoodResources(foodProduced);
    addTechResources(techProduced);
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

  /**
   * Increment the max tech level of a Player by 1
   *
   * @throws MaxTechLevelException if the player's maxTechLevel is already maxed
   *                               out at 6
   */
  public void increaseMaxTechLevel() throws MaxTechLevelException {
    if (maxTechLevel == 6) {
      throw new MaxTechLevelException("Max tech level is already maxed out for this player");
    }
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

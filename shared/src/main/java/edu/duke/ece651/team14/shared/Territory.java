package edu.duke.ece651.team14.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class to represent RISC territory
 */
public abstract class Territory implements Serializable {
  private final String name;
  private Player owner;
  private ArrayList<Unit> units;
  private HashMap<Territory, Boolean> adjacentTerritories;

  /**
   * Creates a Territory from a given name
   *
   * @param name is the Territory name
   */
  public Territory(String name) {
    this.name = name.toLowerCase();
    this.owner = null;
    this.units = new ArrayList<Unit>();
    this.adjacentTerritories = new HashMap<Territory, Boolean>();

  }

  /**
   * Add all territories in map to adjacentTerritories
   *
   * @param allTerritories is an ArrayList of these territories
   */
  public boolean tryInitializeAllTerr(ArrayList<Territory> allTerritories) {
    for (Territory a : allTerritories) {
      adjacentTerritories.put(a, false);
    }
    return true;
  }

  /**
   * Mark a specific territory as adjacent
   *
   * @param terr is the adjacent Territory object
   */
  public boolean tryInitializeAdjacentTerr(Territory terr) {
    if (adjacentTerritories.containsKey(terr)) {
      adjacentTerritories.put(terr, true);
    } else {
      throw new IllegalArgumentException("Territory not in map");
    }
    return true;
  }

  /**
   * Mark a specific territory as adjacent
   *
   * @param terrName is the name of the adjacent territory
   */
  public boolean tryInitializeAdjacentTerrStr(String terrName) {
    for (Map.Entry<Territory, Boolean> adjacent_map : adjacentTerritories.entrySet()) {
      if (adjacent_map.getKey().getName().equals(terrName.toLowerCase())) {
        adjacentTerritories.put(adjacent_map.getKey(), true);
        return true;
      }
    }
    throw new IllegalArgumentException("Territory name not in map");
  }

  /**
   * Check if the territory is adjacent to this territory
   * 
   * @param terr: the territory to check
   * @return true if adjacent
   */
  public boolean isAdjacentTo(Territory terr) {
    if (!adjacentTerritories.containsKey(terr)) {
      throw new IllegalArgumentException("Territory not in map");
    }
    return adjacentTerritories.get(terr);
  }

  /**
   * Check if the territory is adjacent to this territory
   * 
   * @param terr: the territory to check
   * @return true if adjacent
   */
  public boolean isAdjacentTo(String terrName) {
    for (Map.Entry<Territory, Boolean> adjacent_map : adjacentTerritories.entrySet()) {
      if (adjacent_map.getKey().getName().equals(terrName.toLowerCase())) {
        return adjacentTerritories.get(adjacent_map.getKey());
      }
    }
    throw new IllegalArgumentException("Territory name not in map");
  }

  // Get the number of units in the Territory
  public int getNumUnits() {
    return units.size();
  }

  /**
   * Add units to a Territory
   *
   * @param units is a list of units to add to the territory
   * @return true if units successfully added
   * @throws IllegalArgumentException if the list of units is empty
   */
  public boolean tryAddUnits(ArrayList<Unit> units) throws IllegalArgumentException {
    if (units.size() == 0) {
      throw new IllegalArgumentException("Can't add 0 units to a territory - must add 1 or more");
    }
    this.units.addAll(units);
    return true;
  }

  /**
   * Returns territory name
   *
   * @return territory name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the Player who owns this Territory
   *
   * @return the Player who owns the Territory
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Make a Player the owner of this Territory
   */
  public void setOwner(Player player) {
    this.owner = player;
  }

  /**
   * Returns true if territories have same class, same name, same owner
   *
   * @param other is the other object to compare
   *
   * @return true if equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    // Check for same class (both Territories)
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;
      // Check for same name
      if (!name.equals(otherTerritory.getName())) {
        return false;
      }
      // If one or the other owners is null, check that both are null
      if (owner == null || otherTerritory.getOwner() == null) {
        return (owner == null && otherTerritory.getOwner() == null);
      }
      // If both owners are not null, check that they're the same
      return owner.equals(otherTerritory.getOwner());
    }
    return false;
  }

  /**
   * Returns territory name
   *
   * @return territory name
   */
  @Override
  public String toString() {
    return name;
  }

  /**
   * Returns hashcode from territory name
   *
   * @return hashcode from territory name
   */
  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}

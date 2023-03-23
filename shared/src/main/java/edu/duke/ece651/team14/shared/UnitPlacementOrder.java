package edu.duke.ece651.team14.shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is for server and clients communication to support initial units
 * placement
 */
public class UnitPlacementOrder implements Serializable {
  /**
   * Data structure to hold placement for one Territory
   */
  private class Placement implements Serializable {
    public final String TerritoryName;
    public int num_units;

    public Placement(String TerrName, int num_units) {
      this.TerritoryName = TerrName;
      this.num_units = num_units;
    }
  }

  private ArrayList<Placement> placements;

  /**
   * Constructor
   */
  public UnitPlacementOrder() {
    this.placements = new ArrayList<>();
  }

  /**
   * Add one territory placement order
   * 
   * @param TerrName
   * @param UnitsNum
   */
  public void addOneTerrPlacement(String TerrName, int UnitsNum) {
    this.placements.add(new Placement(TerrName, UnitsNum));
  }

  /**
   * Get the Territory name at index i
   * 
   * @param index
   * @return the name
   */
  public String getName(int index) {
    return placements.get(index).TerritoryName;
  }

  /**
   * Get the num of units want to place at index
   * 
   * @param index
   * @return the number of units
   */
  public int getNumUnits(int index) {
    return placements.get(index).num_units;
  }

  /**
   * Set the num of units want to place at index
   * 
   * @param index
   * @param num
   */
  public void setNumUnits(int index, int num) {
    if (num < 0) {
      throw new IllegalArgumentException("Number of units placed can't be negative");
    }
    placements.get(index).num_units = num;
  }

  /**
   * Return how many placements is in this order
   * 
   * @return
   */
  public int size() {
    return placements.size();
  }

  /**
   * Reset all the units to 0.
   */
  public void resetUnits() {
    for (Placement p : placements) {
      p.num_units = 0;
    }
  }
}

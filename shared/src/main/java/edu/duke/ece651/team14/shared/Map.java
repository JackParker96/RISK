package edu.duke.ece651.team14.shared;

import java.util.HashMap;

/**
 * Class to represent RISC Map of territories.
 */
public class Map {
  private HashMap<String, Territory> map;
  private String name;

  /**
   * Creats a Map with given territories and name
   *
   * @param territories is an interable of the map's territories
   *
   * @param name is the map's name
   */
  public Map(Iterable<Territory> territories, String name) {
    this.map = new HashMap<>();
    for (Territory t : territories) {
      map.put(t.getName(), t);
    }
    this.name = name;
  }

  /** 
   * Get the territory object by name.
   * @param TerrName
   * @return the territory object
   */
  public Territory getTerritoryByName(String TerrName){
    TerrName = TerrName.toLowerCase();
    if(!map.containsKey(TerrName)){
      throw new IllegalArgumentException("Territory does not exist in Map");
    }
    return map.get(TerrName);
  }

  /**
   * Returns the map's name
   *
   * @return the map's name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the map HashMap
   *
   * @return the map's HashMap
   */
  public HashMap<String, Territory> getMap() {
    return map;
  }
}

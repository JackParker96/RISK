package edu.duke.ece651.team14.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class to represent RISC Map of territories.
 */
public class Map implements Serializable {
  private HashMap<String, Territory> map;
  private String name;

  /**
   * Creats a Map with given territories and name
   *
   * @param territories is an interable of the map's territories
   *
   * @param name        is the map's name
   */
  public Map(Iterable<Territory> territories, String name) {
    this.map = new HashMap<>();
    for (Territory t : territories) {
      map.put(t.getName(), t);
    }
    this.name = name;
  }

  /**
   * Check if any Player controls all Territories on the map
   *
   * @return the Player who has achieved world domination, return null if no
   *         Player has achieved world domination
   */
  public Player getWinner() {
    HashSet<Player> activePlayers = new HashSet<>();
    for (Territory t : map.values()) {
      activePlayers.add(t.getOwner());
    }
    if (activePlayers.size() == 1) {
      ArrayList<Player> player = new ArrayList<Player>(activePlayers);
      return player.get(0);
    }
    return null;
  }

  /**
   * Get the territory object by name.
   * 
   * @param TerrName
   * @return the territory object
   */
  public Territory getTerritoryByName(String TerrName) {
    TerrName = TerrName.toLowerCase();
    if (!map.containsKey(TerrName)) {
      throw new IllegalArgumentException("Territory does not exist in Map");
    }
    return map.get(TerrName);
  }

  /**
   * Get a mapping from Players to the territories they own
   *
   * @return a HashMap whose keys are all the Players in the game and whose values
   *         are ArrayLists of territories owned by each Player
   */
  public HashMap<Player, ArrayList<Territory>> groupTerritoriesByPlayer() {
    // Intialize an empty map that we will populate and then return
    HashMap<Player, ArrayList<Territory>> ans = new HashMap<Player, ArrayList<Territory>>();
    // Loop through all the Territories in myMap
    for (Territory terr : map.values()) {
      Player p = terr.getOwner();
      // If we have already added this Player to ans, just add terr to the list of
      // Territories controlled by that Player
      if (ans.containsKey(p)) {
        ans.get(p).add(terr);
        // Otherwise, create a new entry in ans
      } else {
        ans.put(p, new ArrayList<Territory>());
        ans.get(p).add(terr);
      }
    }
    return ans;
  }

  /**
   * Initialize a unit placement order, with all units set to 0. It is used to
   * send to clients,
   * let the client fill out the order.
   * 
   * @param p
   * @return the unitplacement order with units num 0.
   */
  public UnitPlacementOrder getUnitsPlacementOrder(Player p) {
    HashMap<Player, ArrayList<Territory>> ownershipInfo = groupTerritoriesByPlayer();
    ArrayList<Territory> terrs = ownershipInfo.get(p);
    UnitPlacementOrder upo = new UnitPlacementOrder();
    for (Territory t : terrs) {
      upo.addOneTerrPlacement(t.getName(), 0);
    }
    return upo;
  }

  /**
   * Add units specified by the unit placement order
   * 
   * @param upo
   */
  public void handleUnitPlacementOrder(UnitPlacementOrder upo) {
    for (int i = 0; i < upo.size(); i++) {
      Territory t = getTerritoryByName(upo.getName(i));
      ArrayList<Unit> units = new ArrayList<>();
      for (int j = 0; j < upo.getNumUnits(i); j++) {
        units.add(new BasicUnit());
      }
      t.addUnits(units);
    }
  }


  /** 
   * Add one basic unit to all territories.
   */
  public void allAddOneUnit(){
    for(Territory t:map.values()){
      t.addUnits(new BasicUnit());
    }
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

  /**
   * Returns true if all Territories are the same and name is the same
   *
   * @param o is the object to compare
   *
   * @return true is equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Map otherMap = (Map) other;
      return ((name.equals(otherMap.getName())) && (map.equals(otherMap.getMap())));
    }
    return false;
  }
}

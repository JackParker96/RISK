package edu.duke.ece651.team14.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class MapTextView {
  private Map map;

  public MapTextView(Map map) {
    this.map = map;
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
    // Get the HashMap that corresponds to the Map object
    HashMap<String, Territory> myMap = map.getMap();
    // Loop through all the Territories in myMap
    for (Territory terr : myMap.values()) {
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
   * Take in an ArrayList of Territories and convert it to a comma-separated list
   * of territory names
   *
   * @param terrArr is the list of Territories you want to write as a
   *                comma-separated list of Territory names
   * @return a String that is a comma-separated list of Territory names
   */
  public String territoriesAsListOfStrings(ArrayList<Territory> terrArr) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < terrArr.size(); i++) {
      sb.append(terrArr.get(i).toString());
      if (i != terrArr.size() - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  /**
   * For each territory a player controls, display how many units are in that
   * territory and adjacency information for that territory
   *
   * @param p       is the Player you want information for
   * @param terrArr is an array of territories controlled by p
   * @return a String representing information about the state of the Player
   *         (detailed in the method description above)
   */
  public String displayPlayerInfo(Player p, ArrayList<Territory> terrArr) {
    StringBuilder sb = new StringBuilder();
    sb.append(p.toString().toUpperCase() + " Player:\n------------\n");
    // Loop through the territories owned by this player
    // For each territory, find our how many units are on that territory and which
    // territories are adjacent to that territory
    // Format this information correctly and append to the stringbuilder
    for (Territory terr : terrArr) {
      String name = terr.getName();
      int numUnits = terr.getNumUnits();
      ArrayList<Territory> adjTerrs = terr.getAdjacentTerritories();
      sb.append(numUnits + " units in " + name + " (next to: " + territoriesAsListOfStrings(adjTerrs) + ")\n");
    }
    return sb.toString();
  }

  /**
   * Display the current state of the game
   * For each player, for each territory that player controls, display the number
   * of units in that territory along with adjacency information for that
   * territory
   *
   * @return a String with all the information detailed in the method description
   */
  public String displayMap() {
    StringBuilder sb = new StringBuilder();
    HashMap<Player, ArrayList<Territory>> gameStateInfo = groupTerritoriesByPlayer();
    for (Player p : gameStateInfo.keySet()) {
      sb.append(displayPlayerInfo(p, gameStateInfo.get(p)));
      sb.append("\n");
    }
    return sb.toString();
  }
}

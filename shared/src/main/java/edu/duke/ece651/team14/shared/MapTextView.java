package edu.duke.ece651.team14.shared;

import java.util.ArrayList;
import java.util.HashMap;

public class MapTextView {
  private Map map;

  public MapTextView(Map map) {
    this.map = map;
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

  public String unitLevelInfo(HashMap<Integer, Integer> levelMap) {
    StringBuilder sb = new StringBuilder();
    for (int lv : levelMap.keySet()) {
      int numUnits = levelMap.get(lv);
      sb.append("     " + numUnits + " units of level " + lv + "\n");
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
      // Figure out the levels of the units contained in this territory
      ArrayList<Unit> units = terr.getUnits();
      ArrayList<Integer> levels = new ArrayList<>();
      for (Unit u : units) {
        int level = u.getTechLevel();
        levels.add(level);
      }
      // Create a hashmap with information about the levels of the units
      HashMap<Integer, Integer> levelMap = new HashMap<>();
      for (int level : levels) {
        if (levelMap.containsKey(level)) {
          levelMap.put(level, levelMap.get(level) + 1);
        } else {
          levelMap.put(level, 1);
        }
      }
      ArrayList<Territory> adjTerrs = terr.getAdjacentTerritories();
      String unitInfo = unitLevelInfo(levelMap);
      sb.append(numUnits + " units in " + name + " (next to: " + territoriesAsListOfStrings(adjTerrs) + ")\n");
      sb.append(unitInfo);
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
    HashMap<Player, ArrayList<Territory>> gameStateInfo = map.groupTerritoriesByPlayer();
    for (Player p : gameStateInfo.keySet()) {
      sb.append(displayPlayerInfo(p, gameStateInfo.get(p)));
      sb.append("\n");
    }
    return sb.toString();
  }
}

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

  public String displayPlayerInfo(Player p, ArrayList<Territory> terr_arr) {
    
  }

  public String displayMap() {
    return "";
  }
}

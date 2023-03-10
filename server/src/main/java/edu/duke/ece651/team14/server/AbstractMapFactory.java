package edu.duke.ece651.team14.server;

import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public interface AbstractMapFactory {
  /**
   * Make a map for players
   * 
   * @param players
   * @return a created map with
   */
  public Map makeMap(String mapName, ArrayList<Player> players);

  public HashMap<Integer, ArrayList<Territory>> makeGroups(ArrayList<Territory> allTerr, int numPlayers);
}

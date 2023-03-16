package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;

/**
 * Class to represent an abstract Map factory
 */
public interface AbstractMapFactory {
  /**
   * Make a map for given players
   *
   * @param mapName is the map name
   * @param players is the ArrayList of players
   * @return the map
   */
  public Map makeMap(String mapName, ArrayList<Player> players);
}

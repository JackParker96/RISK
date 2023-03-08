package edu.duke.ece651.team14.server;

import java.util.ArrayList;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;

public interface AbstractMapFactory {
  /** 
   * make a map for players
   * @param players
   * @return a created map with
   */
  public Map makeMap(String mapName, ArrayList<Player> players);
}

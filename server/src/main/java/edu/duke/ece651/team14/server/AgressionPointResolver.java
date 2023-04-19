package edu.duke.ece651.team14.server;

import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.AgressionPointMsg;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MaxTechLevelException;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public class AgressionPointResolver {
  private Map map;
  private AgressionPointMsg msg;
  private HashMap<Player, ArrayList<Territory>> originOwnership;

  public AgressionPointResolver(Map map) {
    this.map = map;
    this.msg = new AgressionPointMsg();
    this.originOwnership = map.groupTerritoriesByPlayer();
  }

  /**
   * Shoule be called after resolve attacks
   */
  public AgressionPointMsg resolveAgressionPoint() throws MaxTechLevelException{
    HashMap<Player, ArrayList<Territory>> curOwnership = map.groupTerritoriesByPlayer();
    for (Player p : originOwnership.keySet()) {
      if (curOwnership.containsKey(p)) {
        if(hasNewTerritory(originOwnership.get(p), curOwnership.get(p))){
          p.addAggPt(map);
          this.msg.addPlayerMsg(p.getName(), p.getAggPt());
        }else{
          p.resetAggPts();
          this.msg.addPlayerMsg(p.getName(), 0);
        }
      } else {// corner case: lost
        p.resetAggPts();
        this.msg.addPlayerMsg(p.getName(), 0);
      }
    }
    return this.msg;
  }

  private boolean hasNewTerritory(ArrayList<Territory> oldTerritories, ArrayList<Territory> newTerritories){
    for(Territory t: newTerritories){
      if(!oldTerritories.contains(t)){
        return true;
      }
    }
    return false;
  }
}

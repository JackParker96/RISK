package edu.duke.ece651.team14.server;

import java.util.ArrayList;

import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public class MapFactory implements AbstractMapFactory {
  @Override
  public Map makeMap(String mapName,ArrayList<Player> players) {
    ArrayList<Territory> allTerritories = makeTheMap();
    Map m = new Map(allTerritories,mapName);
    return m;
  }

  private ArrayList<Territory> makeTheMap() {
    ArrayList<Territory> allTerritories = new ArrayList<Territory>();
    allTerritories.add(new BasicTerritory("Narnia"));
    allTerritories.add(new BasicTerritory("Midkemia"));
    allTerritories.add(new BasicTerritory("Oz"));
    allTerritories.add(new BasicTerritory("Gondor"));
    allTerritories.add(new BasicTerritory("Mordor"));
    allTerritories.add(new BasicTerritory("Neverland"));

    allTerritories.add(new BasicTerritory("Elantris"));
    allTerritories.add(new BasicTerritory("Scadrial"));
    allTerritories.add(new BasicTerritory("Roshar"));
    allTerritories.add(new BasicTerritory("Mt Olympus"));
    allTerritories.add(new BasicTerritory("Mt Othrys"));
    allTerritories.add(new BasicTerritory("Camp Half-Blood"));

    allTerritories.add(new BasicTerritory("Hogwarts"));
    allTerritories.add(new BasicTerritory("Diagon Alley"));
    allTerritories.add(new BasicTerritory("Platform 9 and 3/4"));
    allTerritories.add(new BasicTerritory("Jurassic Park"));
    allTerritories.add(new BasicTerritory("Gotham City"));
    allTerritories.add(new BasicTerritory("Wakanda"));

    allTerritories.add(new BasicTerritory("The Capitol"));
    allTerritories.add(new BasicTerritory("District Twelve"));
    allTerritories.add(new BasicTerritory("North Pole"));
    allTerritories.add(new BasicTerritory("Atlantis"));
    allTerritories.add(new BasicTerritory("Wonka Chocolate Factory"));
    allTerritories.add(new BasicTerritory("Duke"));
    addAdjacency(allTerritories);
    return allTerritories;
  }

  private ArrayList<Territory> addAdjacency(ArrayList<Territory> allTerritories){
    for(Territory t:allTerritories){
      t.tryInitializeAllTerr(allTerritories);
    }
    //Narnia
    allTerritories.get(0).tryInitializeAdjacentTerrStr("Midkemia");
    allTerritories.get(0).tryInitializeAdjacentTerrStr("Mordor");
    //Midkemia
    allTerritories.get(1).tryInitializeAdjacentTerrStr("Narnia");
    allTerritories.get(1).tryInitializeAdjacentTerrStr("Neverland");
    allTerritories.get(1).tryInitializeAdjacentTerrStr("Mordor");
    allTerritories.get(1).tryInitializeAdjacentTerrStr("Gondor");
    //Oz ...
    return allTerritories;
  }

}

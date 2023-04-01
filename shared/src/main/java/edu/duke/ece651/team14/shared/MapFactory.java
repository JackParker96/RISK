package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

/**
 * Class to make a hard-coded map with 24 Territories
 */
public class MapFactory implements AbstractMapFactory {
  @Override
  /**
   * Makes a hard-coded map of 24 Territories
   *
   * @param mapName is the map name
   *
   * @param players is the ArrayList of players
   *
   * @return the map
   */
  public Map makeMap(String mapName, ArrayList<Player> players) {
    if (mapName.equals("test")) {
      return new Map(makeTestTerritories(players.get(0), players.get(1)), mapName);
    }
    ArrayList<Territory> allTerritories = makeTerritories();
    addAdjacency(allTerritories);
    addOwners(allTerritories, players);
    initializeProductionRates(allTerritories);
    Map m = new Map(allTerritories, mapName);
    return m;
  }

   /**
   * Makes a list of Territorires for testing
   */
  public ArrayList<Territory> makeTestTerritories(Player p1, Player p2) {
    ArrayList<Territory> terrs = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      terrs.add(new BasicTerritory(Integer.toString(i)));
      if (i < 4) {
        terrs.get(i).setOwner(p1);
      } else {
        terrs.get(i).setOwner(p2);
      }
    }

    // Add adjacencies
    terrs.get(0).addAdjacentTerritories(terrs.get(1));
    terrs.get(1).addAdjacentTerritories(terrs.get(2));
    terrs.get(2).addAdjacentTerritories(terrs.get(3));
    terrs.get(2).addAdjacentTerritories(terrs.get(4));
    terrs.get(4).addAdjacentTerritories(terrs.get(5));
    terrs.get(4).addAdjacentTerritories(terrs.get(6));

    terrs.get(1).addAdjacentTerritories(terrs.get(0));
    terrs.get(2).addAdjacentTerritories(terrs.get(1));
    terrs.get(3).addAdjacentTerritories(terrs.get(2));
    terrs.get(4).addAdjacentTerritories(terrs.get(2));
    terrs.get(5).addAdjacentTerritories(terrs.get(4));
    terrs.get(6).addAdjacentTerritories(terrs.get(4));

    terrs.get(1).addUnits(new BasicUnit());

    return terrs;
  }

  /**
   * Makes an ArrayList of 24 hard-coded Territories
   *
   * @return an ArrayList of 24 hard-coded Territories
   */
  public ArrayList<Territory> makeTerritories() {
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

    return allTerritories;
  }
  
  /**
   * Adds hard-coded resource production rates to given ArrayList of Territories
   *
   * @param allTerritories is the ArrayList of Territories to which to add production rates
   */
  public void initializeProductionRates(ArrayList<Territory> allTerritories) {
    // Narnia
    allTerritories.get(0).setFoodProductionRate(5);
    // Midkemia
    allTerritories.get(1).setTechProductionRate(10);
    // Oz
    allTerritories.get(2).setTechProductionRate(10);
    // Gondor
    allTerritories.get(3).setFoodProductionRate(5);
    // Mordor
    allTerritories.get(4).setTechProductionRate(10);
    // Neverland
    allTerritories.get(5).setFoodProductionRate(5);
    // Elantris
    allTerritories.get(6).setFoodProductionRate(5);
    // Scadrial
    allTerritories.get(7).setTechProductionRate(10);
    // Roshar
    allTerritories.get(8).setFoodProductionRate(5);
    // Mt Olympus
    allTerritories.get(9).setFoodProductionRate(5);
    // Mt Othrys
    allTerritories.get(10).setTechProductionRate(10);
    // Camp Half-Blood
    allTerritories.get(11).setTechProductionRate(10);
    // Hogwarts
    allTerritories.get(12).setFoodProductionRate(5);
    // Diagon Alley
    allTerritories.get(13).setFoodProductionRate(5);
    // Platform 9 and 3/4
    allTerritories.get(14).setTechProductionRate(10);
    // Jurassic Park
    allTerritories.get(15).setTechProductionRate(10);
    // Gotham City
    allTerritories.get(16).setTechProductionRate(10);
    // Wakanda
    allTerritories.get(17).setFoodProductionRate(5);
    // The Capitol
    allTerritories.get(18).setFoodProductionRate(5);
    // District Twelve
    allTerritories.get(19).setTechProductionRate(10);
    // North Pole
    allTerritories.get(20).setFoodProductionRate(5);
    // Atlantis
    allTerritories.get(21).setTechProductionRate(10);
    // Wonka Chocolate Factory
    allTerritories.get(22).setFoodProductionRate(5);
    // Duke
    allTerritories.get(23).setTechProductionRate(10);
  }

  /**
   * Adds hard-coded adjacencies to given ArrayList of Territories
   *
   * @param allTerritories is the ArrayList of Territories to which to add
   *                       adjacency information
   */
  public void addAdjacency(ArrayList<Territory> allTerritories) {
    // Initialize temporary map to add adjacent territories
    Map myMap = new Map(allTerritories, "m");
    // Narnia
    allTerritories.get(0).addAdjacentTerritories(myMap.getMap().get("Midkemia".toLowerCase()));
    allTerritories.get(0).addAdjacentTerritories(myMap.getMap().get("Mordor".toLowerCase()));
    // Midkemia
    allTerritories.get(1).addAdjacentTerritories(myMap.getMap().get("Narnia".toLowerCase()));
    allTerritories.get(1).addAdjacentTerritories(myMap.getMap().get("Neverland".toLowerCase()));
    allTerritories.get(1).addAdjacentTerritories(myMap.getMap().get("Mordor".toLowerCase()));
    allTerritories.get(1).addAdjacentTerritories(myMap.getMap().get("Gondor".toLowerCase()));
    // Oz
    allTerritories.get(2).addAdjacentTerritories(myMap.getMap().get("Neverland".toLowerCase()));
    allTerritories.get(2).addAdjacentTerritories(myMap.getMap().get("Elantris".toLowerCase()));
    allTerritories.get(2).addAdjacentTerritories(myMap.getMap().get("Gondor".toLowerCase()));
    allTerritories.get(2).addAdjacentTerritories(myMap.getMap().get("Mt Othrys".toLowerCase()));
    // Gondor
    allTerritories.get(3).addAdjacentTerritories(myMap.getMap().get("Midkemia".toLowerCase()));
    allTerritories.get(3).addAdjacentTerritories(myMap.getMap().get("Neverland".toLowerCase()));
    allTerritories.get(3).addAdjacentTerritories(myMap.getMap().get("Oz".toLowerCase()));
    allTerritories.get(3).addAdjacentTerritories(myMap.getMap().get("Mt Othrys".toLowerCase()));
    allTerritories.get(3).addAdjacentTerritories(myMap.getMap().get("Mordor".toLowerCase()));
    allTerritories.get(3).addAdjacentTerritories(myMap.getMap().get("Wakanda".toLowerCase()));
    allTerritories.get(3).addAdjacentTerritories(myMap.getMap().get("Platform 9 and 3/4".toLowerCase()));
    // Mordor
    allTerritories.get(4).addAdjacentTerritories(myMap.getMap().get("Narnia".toLowerCase()));
    allTerritories.get(4).addAdjacentTerritories(myMap.getMap().get("Midkemia".toLowerCase()));
    allTerritories.get(4).addAdjacentTerritories(myMap.getMap().get("Gondor".toLowerCase()));
    allTerritories.get(4).addAdjacentTerritories(myMap.getMap().get("Platform 9 and 3/4".toLowerCase()));
    allTerritories.get(4).addAdjacentTerritories(myMap.getMap().get("Hogwarts".toLowerCase()));
    // Neverland
    allTerritories.get(5).addAdjacentTerritories(myMap.getMap().get("Midkemia".toLowerCase()));
    allTerritories.get(5).addAdjacentTerritories(myMap.getMap().get("Gondor".toLowerCase()));
    allTerritories.get(5).addAdjacentTerritories(myMap.getMap().get("Oz".toLowerCase()));
    allTerritories.get(5).addAdjacentTerritories(myMap.getMap().get("Elantris".toLowerCase()));
    // Elantris
    allTerritories.get(6).addAdjacentTerritories(myMap.getMap().get("Neverland".toLowerCase()));
    allTerritories.get(6).addAdjacentTerritories(myMap.getMap().get("Oz".toLowerCase()));
    allTerritories.get(6).addAdjacentTerritories(myMap.getMap().get("Mt Othrys".toLowerCase()));
    allTerritories.get(6).addAdjacentTerritories(myMap.getMap().get("Mt Olympus".toLowerCase()));
    allTerritories.get(6).addAdjacentTerritories(myMap.getMap().get("Scadrial".toLowerCase()));
    // Scadrial
    allTerritories.get(7).addAdjacentTerritories(myMap.getMap().get("Elantris".toLowerCase()));
    allTerritories.get(7).addAdjacentTerritories(myMap.getMap().get("Mt Olympus".toLowerCase()));
    allTerritories.get(7).addAdjacentTerritories(myMap.getMap().get("Roshar".toLowerCase()));
    // Roshar
    allTerritories.get(8).addAdjacentTerritories(myMap.getMap().get("Scadrial".toLowerCase()));
    allTerritories.get(8).addAdjacentTerritories(myMap.getMap().get("Mt Olympus".toLowerCase()));
    allTerritories.get(8).addAdjacentTerritories(myMap.getMap().get("Camp Half-Blood".toLowerCase()));
    // Mt Olympus
    allTerritories.get(9).addAdjacentTerritories(myMap.getMap().get("Mt Othrys".toLowerCase()));
    allTerritories.get(9).addAdjacentTerritories(myMap.getMap().get("Elantris".toLowerCase()));
    allTerritories.get(9).addAdjacentTerritories(myMap.getMap().get("Scadrial".toLowerCase()));
    allTerritories.get(9).addAdjacentTerritories(myMap.getMap().get("Roshar".toLowerCase()));
    allTerritories.get(9).addAdjacentTerritories(myMap.getMap().get("Camp Half-Blood".toLowerCase()));
    // Mt Othrys
    allTerritories.get(10).addAdjacentTerritories(myMap.getMap().get("Gondor".toLowerCase()));
    allTerritories.get(10).addAdjacentTerritories(myMap.getMap().get("Oz".toLowerCase()));
    allTerritories.get(10).addAdjacentTerritories(myMap.getMap().get("Elantris".toLowerCase()));
    allTerritories.get(10).addAdjacentTerritories(myMap.getMap().get("Mt Olympus".toLowerCase()));
    allTerritories.get(10).addAdjacentTerritories(myMap.getMap().get("Camp Half-Blood".toLowerCase()));
    allTerritories.get(10).addAdjacentTerritories(myMap.getMap().get("Jurassic Park".toLowerCase()));
    allTerritories.get(10).addAdjacentTerritories(myMap.getMap().get("Wakanda".toLowerCase()));
    // Camp Half-Blood
    allTerritories.get(11).addAdjacentTerritories(myMap.getMap().get("Mt Othrys".toLowerCase()));
    allTerritories.get(11).addAdjacentTerritories(myMap.getMap().get("Mt Olympus".toLowerCase()));
    allTerritories.get(11).addAdjacentTerritories(myMap.getMap().get("Roshar".toLowerCase()));
    allTerritories.get(11).addAdjacentTerritories(myMap.getMap().get("Jurassic Park".toLowerCase()));
    allTerritories.get(11).addAdjacentTerritories(myMap.getMap().get("The Capitol".toLowerCase()));
    allTerritories.get(11).addAdjacentTerritories(myMap.getMap().get("North Pole".toLowerCase()));
    // Hogwarts
    allTerritories.get(12).addAdjacentTerritories(myMap.getMap().get("Mordor".toLowerCase()));
    allTerritories.get(12).addAdjacentTerritories(myMap.getMap().get("Platform 9 and 3/4".toLowerCase()));
    allTerritories.get(12).addAdjacentTerritories(myMap.getMap().get("Diagon Alley".toLowerCase()));
    // Diagon Alley
    allTerritories.get(13).addAdjacentTerritories(myMap.getMap().get("Hogwarts".toLowerCase()));
    allTerritories.get(13).addAdjacentTerritories(myMap.getMap().get("Platform 9 and 3/4".toLowerCase()));
    allTerritories.get(13).addAdjacentTerritories(myMap.getMap().get("Gotham City".toLowerCase()));
    allTerritories.get(13).addAdjacentTerritories(myMap.getMap().get("Atlantis".toLowerCase()));
    // Platform 9 and 3/4
    allTerritories.get(14).addAdjacentTerritories(myMap.getMap().get("Hogwarts".toLowerCase()));
    allTerritories.get(14).addAdjacentTerritories(myMap.getMap().get("Mordor".toLowerCase()));
    allTerritories.get(14).addAdjacentTerritories(myMap.getMap().get("Gondor".toLowerCase()));
    allTerritories.get(14).addAdjacentTerritories(myMap.getMap().get("Wakanda".toLowerCase()));
    allTerritories.get(14).addAdjacentTerritories(myMap.getMap().get("Jurassic Park".toLowerCase()));
    allTerritories.get(14).addAdjacentTerritories(myMap.getMap().get("Gotham City".toLowerCase()));
    allTerritories.get(14).addAdjacentTerritories(myMap.getMap().get("Diagon Alley".toLowerCase()));
    // Jurassic Park
    allTerritories.get(15).addAdjacentTerritories(myMap.getMap().get("Platform 9 and 3/4".toLowerCase()));
    allTerritories.get(15).addAdjacentTerritories(myMap.getMap().get("Wakanda".toLowerCase()));
    allTerritories.get(15).addAdjacentTerritories(myMap.getMap().get("Mt Othrys".toLowerCase()));
    allTerritories.get(15).addAdjacentTerritories(myMap.getMap().get("Camp Half-Blood".toLowerCase()));
    allTerritories.get(15).addAdjacentTerritories(myMap.getMap().get("The Capitol".toLowerCase()));
    allTerritories.get(15).addAdjacentTerritories(myMap.getMap().get("Atlantis".toLowerCase()));
    allTerritories.get(15).addAdjacentTerritories(myMap.getMap().get("Gotham City".toLowerCase()));
    // Gotham City
    allTerritories.get(16).addAdjacentTerritories(myMap.getMap().get("Diagon Alley".toLowerCase()));
    allTerritories.get(16).addAdjacentTerritories(myMap.getMap().get("Platform 9 and 3/4".toLowerCase()));
    allTerritories.get(16).addAdjacentTerritories(myMap.getMap().get("Jurassic Park".toLowerCase()));
    allTerritories.get(16).addAdjacentTerritories(myMap.getMap().get("Atlantis".toLowerCase()));
    // Wakanda
    allTerritories.get(17).addAdjacentTerritories(myMap.getMap().get("Gondor".toLowerCase()));
    allTerritories.get(17).addAdjacentTerritories(myMap.getMap().get("Mt Othrys".toLowerCase()));
    allTerritories.get(17).addAdjacentTerritories(myMap.getMap().get("Jurassic Park".toLowerCase()));
    allTerritories.get(17).addAdjacentTerritories(myMap.getMap().get("Platform 9 and 3/4".toLowerCase()));
    // The Capitol
    allTerritories.get(18).addAdjacentTerritories(myMap.getMap().get("Jurassic Park".toLowerCase()));
    allTerritories.get(18).addAdjacentTerritories(myMap.getMap().get("Camp Half-Blood".toLowerCase()));
    allTerritories.get(18).addAdjacentTerritories(myMap.getMap().get("North Pole".toLowerCase()));
    allTerritories.get(18).addAdjacentTerritories(myMap.getMap().get("Duke".toLowerCase()));
    allTerritories.get(18).addAdjacentTerritories(myMap.getMap().get("Wonka Chocolate Factory".toLowerCase()));
    allTerritories.get(18).addAdjacentTerritories(myMap.getMap().get("Atlantis".toLowerCase()));
    // District Twelve
    allTerritories.get(19).addAdjacentTerritories(myMap.getMap().get("Wonka Chocolate Factory".toLowerCase()));
    allTerritories.get(19).addAdjacentTerritories(myMap.getMap().get("Duke".toLowerCase()));
    // North Pole
    allTerritories.get(20).addAdjacentTerritories(myMap.getMap().get("The Capitol".toLowerCase()));
    allTerritories.get(20).addAdjacentTerritories(myMap.getMap().get("Camp Half-Blood".toLowerCase()));
    allTerritories.get(20).addAdjacentTerritories(myMap.getMap().get("Duke".toLowerCase()));
    // Atlantis
    allTerritories.get(21).addAdjacentTerritories(myMap.getMap().get("Diagon Alley".toLowerCase()));
    allTerritories.get(21).addAdjacentTerritories(myMap.getMap().get("Gotham City".toLowerCase()));
    allTerritories.get(21).addAdjacentTerritories(myMap.getMap().get("Jurassic Park".toLowerCase()));
    allTerritories.get(21).addAdjacentTerritories(myMap.getMap().get("The Capitol".toLowerCase()));
    allTerritories.get(21).addAdjacentTerritories(myMap.getMap().get("Wonka Chocolate Factory".toLowerCase()));
    // Wonka Chocolate Factory
    allTerritories.get(22).addAdjacentTerritories(myMap.getMap().get("Atlantis".toLowerCase()));
    allTerritories.get(22).addAdjacentTerritories(myMap.getMap().get("The Capitol".toLowerCase()));
    allTerritories.get(22).addAdjacentTerritories(myMap.getMap().get("Duke".toLowerCase()));
    allTerritories.get(22).addAdjacentTerritories(myMap.getMap().get("District Twelve".toLowerCase()));
    // Duke
    allTerritories.get(23).addAdjacentTerritories(myMap.getMap().get("Wonka Chocolate Factory".toLowerCase()));
    allTerritories.get(23).addAdjacentTerritories(myMap.getMap().get("The Capitol".toLowerCase()));
    allTerritories.get(23).addAdjacentTerritories(myMap.getMap().get("North Pole".toLowerCase()));
    allTerritories.get(23).addAdjacentTerritories(myMap.getMap().get("District Twelve".toLowerCase()));
  }

  /**
   * Make four groups of territories
   *
   * @param allTerritories is an ArrayList of all territories
   * @return HashMap with player number as key and ArrayList of territories for
   *         player as value
   */
  public void addOwners(ArrayList<Territory> allTerritories, ArrayList<Player> players) {
    int numPlayers = players.size();
    if (!(numPlayers >= 2 && numPlayers <= 4)) {
      throw new IllegalArgumentException("This class only supports 2-4 groups.");
    }

    int numTerr = allTerritories.size();
    int territoriesPerPlayer = numTerr / numPlayers;

    if (numPlayers == 3) {
      for (int i = 0; i < 6; i++) {
        for (int player = 0; player < 2; player++) {
          allTerritories.get(i + player * 6).setOwner(players.get(player));
        }
        allTerritories.get(i + 3 * 6).setOwner(players.get(2));
      }
      allTerritories.get(12).setOwner(players.get(0));
      allTerritories.get(14).setOwner(players.get(0));
      allTerritories.get(15).setOwner(players.get(1));
      allTerritories.get(17).setOwner(players.get(1));
      allTerritories.get(13).setOwner(players.get(2));
      allTerritories.get(16).setOwner(players.get(2));
    }
    
    else {
      for (int i = 0; i < territoriesPerPlayer; i++) {
        for (int player = 0; player < numPlayers; player++) {
          allTerritories.get(i + player * territoriesPerPlayer).setOwner(players.get(player));
        }
      }
    }

  
  }
}

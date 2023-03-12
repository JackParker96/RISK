package edu.duke.ece651.team14.server;

import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.BasicTerritory;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.Territory;

public class MapFactory implements AbstractMapFactory {
  @Override
  public Map makeMap(String mapName, ArrayList<Player> players) {
    ArrayList<Territory> allTerritories = makeTheMap();
    Map m = new Map(allTerritories, mapName);
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

  private ArrayList<Territory> addAdjacency(ArrayList<Territory> allTerritories) {
    //Initialize temporary map to add adjacent territories
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
    return allTerritories;
  }

  /**
   * Make four groups of territories
   *
   * @param allTerritories is an ArrayList of all territories
   * @return HashMap with player number as key and ArrayList of territories for
   *         player as value
   */
  private HashMap<Integer, ArrayList<Territory>> makeFourGroups(ArrayList<Territory> allTerritories) {
    HashMap<Integer, ArrayList<Territory>> groups = new HashMap<>();

    ArrayList<Territory> green = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      green.add(allTerritories.get(i));
    }

    ArrayList<Territory> yellow = new ArrayList<>();
    for (int j = 6; j < 12; j++) {
      yellow.add(allTerritories.get(j));
    }

    ArrayList<Territory> red = new ArrayList<>();
    for (int k = 12; k < 18; k++) {
      red.add(allTerritories.get(k));
    }

    ArrayList<Territory> blue = new ArrayList<>();
    for (int m = 18; m < 24; m++) {
      blue.add(allTerritories.get(m));
    }

    groups.put(1, green);
    groups.put(2, yellow);
    groups.put(3, red);
    groups.put(4, blue);

    return groups;
  }

  /**
   * Make three groups of territories
   *
   * @param allTerritories is an ArrayList of all territories
   * @return HashMap with player number as key and ArrayList of territories for
   *         player as value
   */
  private HashMap<Integer, ArrayList<Territory>> makeThreeGroups(ArrayList<Territory> allTerritories) {
    HashMap<Integer, ArrayList<Territory>> groups = new HashMap<>();

    ArrayList<Territory> green = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      green.add(allTerritories.get(i));
    }
    green.add(allTerritories.get(12));
    green.add(allTerritories.get(14));

    ArrayList<Territory> yellow = new ArrayList<>();
    for (int j = 6; j < 12; j++) {
      yellow.add(allTerritories.get(j));
    }
    yellow.add(allTerritories.get(15));
    yellow.add(allTerritories.get(17));

    ArrayList<Territory> blue = new ArrayList<>();
    for (int m = 18; m < 24; m++) {
      blue.add(allTerritories.get(m));
    }
    blue.add(allTerritories.get(13));
    blue.add(allTerritories.get(16));

    groups.put(1, green);
    groups.put(2, yellow);
    groups.put(3, blue);

    return groups;
  }

  /**
   * Make two groups of territories
   *
   * @param allTerritories is an ArrayList of all territories
   * @return HashMap with player number as key and ArrayList of territories for
   *         player as value
   */
  private HashMap<Integer, ArrayList<Territory>> makeTwoGroups(ArrayList<Territory> allTerritories) {
    HashMap<Integer, ArrayList<Territory>> groups = new HashMap<>();

    ArrayList<Territory> green = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      green.add(allTerritories.get(i));
    }
    for (int j = 6; j < 12; j++) {
      green.add(allTerritories.get(j));
    }

    ArrayList<Territory> blue = new ArrayList<>();
    for (int k = 12; k < 18; k++) {
      blue.add(allTerritories.get(k));
    }
    for (int m = 18; m < 24; m++) {
      blue.add(allTerritories.get(m));
    }

    groups.put(1, green);
    groups.put(2, blue);

    return groups;
  }

  /**
   * Determine groups of territories depending on number of players
   *
   * @param allTerr    is an ArrayList of all territories
   * @param numPlayers is the number of players
   * @return HashMap with player number as key and ArrayList of territories for
   *         player as value
   */
  @Override
  public HashMap<Integer, ArrayList<Territory>> makeGroups(ArrayList<Territory> allTerr, int numPlayers) {
    HashMap<Integer, ArrayList<Territory>> groups = new HashMap<>();
    if (numPlayers == 2) {
      makeTwoGroups(allTerr);
    } else if (numPlayers == 3) {
      makeThreeGroups(allTerr);
    } else if (numPlayers == 4) {
      makeFourGroups(allTerr);
    } else {
      throw new IllegalArgumentException("Wrong number of players");
    }
    return groups;
  }

}

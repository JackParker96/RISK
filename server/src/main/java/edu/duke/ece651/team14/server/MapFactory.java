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
    for (Territory t : allTerritories) {
      t.tryInitializeAllTerr(allTerritories);
    }
    // Narnia
    allTerritories.get(0).tryInitializeAdjacentTerrStr("Midkemia");
    allTerritories.get(0).tryInitializeAdjacentTerrStr("Mordor");
    // Midkemia
    allTerritories.get(1).tryInitializeAdjacentTerrStr("Narnia");
    allTerritories.get(1).tryInitializeAdjacentTerrStr("Neverland");
    allTerritories.get(1).tryInitializeAdjacentTerrStr("Mordor");
    allTerritories.get(1).tryInitializeAdjacentTerrStr("Gondor");
    // Oz
    allTerritories.get(2).tryInitializeAdjacentTerrStr("Neverland");
    allTerritories.get(2).tryInitializeAdjacentTerrStr("Elantris");
    allTerritories.get(2).tryInitializeAdjacentTerrStr("Gondor");
    allTerritories.get(2).tryInitializeAdjacentTerrStr("Mt Othrys");
    // Gondor
    allTerritories.get(3).tryInitializeAdjacentTerrStr("Midkemia");
    allTerritories.get(3).tryInitializeAdjacentTerrStr("Neverland");
    allTerritories.get(3).tryInitializeAdjacentTerrStr("Oz");
    allTerritories.get(3).tryInitializeAdjacentTerrStr("Mt Othrys");
    allTerritories.get(3).tryInitializeAdjacentTerrStr("Mordor");
    allTerritories.get(3).tryInitializeAdjacentTerrStr("Wakanda");
    allTerritories.get(3).tryInitializeAdjacentTerrStr("Platform 9 and 3/4");
    // Mordor
    allTerritories.get(4).tryInitializeAdjacentTerrStr("Narnia");
    allTerritories.get(4).tryInitializeAdjacentTerrStr("Midkemia");
    allTerritories.get(4).tryInitializeAdjacentTerrStr("Gondor");
    allTerritories.get(4).tryInitializeAdjacentTerrStr("Platform 9 and 3/4");
    allTerritories.get(4).tryInitializeAdjacentTerrStr("Hogwarts");
    // Neverland
    allTerritories.get(5).tryInitializeAdjacentTerrStr("Midkemia");
    allTerritories.get(5).tryInitializeAdjacentTerrStr("Gondor");
    allTerritories.get(5).tryInitializeAdjacentTerrStr("Oz");
    allTerritories.get(5).tryInitializeAdjacentTerrStr("Elantris");
    // Elantris
    allTerritories.get(6).tryInitializeAdjacentTerrStr("Neverland");
    allTerritories.get(6).tryInitializeAdjacentTerrStr("Oz");
    allTerritories.get(6).tryInitializeAdjacentTerrStr("Mt Othrys");
    allTerritories.get(6).tryInitializeAdjacentTerrStr("Mt Olympus");
    allTerritories.get(6).tryInitializeAdjacentTerrStr("Scadrial");
    // Scadrial
    allTerritories.get(7).tryInitializeAdjacentTerrStr("Elantris");
    allTerritories.get(7).tryInitializeAdjacentTerrStr("Mt Olympus");
    allTerritories.get(7).tryInitializeAdjacentTerrStr("Roshar");
    // Roshar
    allTerritories.get(8).tryInitializeAdjacentTerrStr("Scadrial");
    allTerritories.get(8).tryInitializeAdjacentTerrStr("Mt Olympus");
    allTerritories.get(8).tryInitializeAdjacentTerrStr("Camp Half-Blood");
    // Mt Olympus
    allTerritories.get(9).tryInitializeAdjacentTerrStr("Mt Othrys");
    allTerritories.get(9).tryInitializeAdjacentTerrStr("Elantris");
    allTerritories.get(9).tryInitializeAdjacentTerrStr("Scadrial");
    allTerritories.get(9).tryInitializeAdjacentTerrStr("Roshar");
    allTerritories.get(9).tryInitializeAdjacentTerrStr("Camp Half-Blood");
    // Mt Othrys
    allTerritories.get(10).tryInitializeAdjacentTerrStr("Gondor");
    allTerritories.get(10).tryInitializeAdjacentTerrStr("Oz");
    allTerritories.get(10).tryInitializeAdjacentTerrStr("Elantris");
    allTerritories.get(10).tryInitializeAdjacentTerrStr("Mt Olympus");
    allTerritories.get(10).tryInitializeAdjacentTerrStr("Camp Half-Blood");
    allTerritories.get(10).tryInitializeAdjacentTerrStr("Jurassic Park");
    allTerritories.get(10).tryInitializeAdjacentTerrStr("Wakanda");
    // Camp Half-Blood
    allTerritories.get(11).tryInitializeAdjacentTerrStr("Mt Othrys");
    allTerritories.get(11).tryInitializeAdjacentTerrStr("Mt Olympus");
    allTerritories.get(11).tryInitializeAdjacentTerrStr("Roshar");
    allTerritories.get(11).tryInitializeAdjacentTerrStr("Jurassic Park");
    allTerritories.get(11).tryInitializeAdjacentTerrStr("The Capitol");
    allTerritories.get(11).tryInitializeAdjacentTerrStr("North Pole");
    // Hogwarts
    allTerritories.get(12).tryInitializeAdjacentTerrStr("Mordor");
    allTerritories.get(12).tryInitializeAdjacentTerrStr("Platform 9 and 3/4");
    allTerritories.get(12).tryInitializeAdjacentTerrStr("Diagon Alley");
    // Diagon Alley
    allTerritories.get(13).tryInitializeAdjacentTerrStr("Hogwarts");
    allTerritories.get(13).tryInitializeAdjacentTerrStr("Platform 9 and 3/4");
    allTerritories.get(13).tryInitializeAdjacentTerrStr("Gotham City");
    allTerritories.get(13).tryInitializeAdjacentTerrStr("Atlantis");
    // Platform 9 and 3/4
    allTerritories.get(14).tryInitializeAdjacentTerrStr("Hogwarts");
    allTerritories.get(14).tryInitializeAdjacentTerrStr("Mordor");
    allTerritories.get(14).tryInitializeAdjacentTerrStr("Gondor");
    allTerritories.get(14).tryInitializeAdjacentTerrStr("Wakanda");
    allTerritories.get(14).tryInitializeAdjacentTerrStr("Jurassic Park");
    allTerritories.get(14).tryInitializeAdjacentTerrStr("Gotham City");
    allTerritories.get(14).tryInitializeAdjacentTerrStr("Diagon Alley");
    // Jurassic Park
    allTerritories.get(15).tryInitializeAdjacentTerrStr("Platform 9 and 3/4");
    allTerritories.get(15).tryInitializeAdjacentTerrStr("Wakanda");
    allTerritories.get(15).tryInitializeAdjacentTerrStr("Mt Othrys");
    allTerritories.get(15).tryInitializeAdjacentTerrStr("Camp Half-Blood");
    allTerritories.get(15).tryInitializeAdjacentTerrStr("The Capitol");
    allTerritories.get(15).tryInitializeAdjacentTerrStr("Atlantis");
    allTerritories.get(15).tryInitializeAdjacentTerrStr("Gotham City");
    // Gotham City
    allTerritories.get(16).tryInitializeAdjacentTerrStr("Diagon Alley");
    allTerritories.get(16).tryInitializeAdjacentTerrStr("Platform 9 and 3/4");
    allTerritories.get(16).tryInitializeAdjacentTerrStr("Jurassic Park");
    allTerritories.get(16).tryInitializeAdjacentTerrStr("Atlantis");
    // Wakanda
    allTerritories.get(17).tryInitializeAdjacentTerrStr("Gondor");
    allTerritories.get(17).tryInitializeAdjacentTerrStr("Mt Othrys");
    allTerritories.get(17).tryInitializeAdjacentTerrStr("Jurassic Park");
    allTerritories.get(17).tryInitializeAdjacentTerrStr("Platform 9 and 3/4");
    // The Capitol
    allTerritories.get(18).tryInitializeAdjacentTerrStr("Jurassic Park");
    allTerritories.get(18).tryInitializeAdjacentTerrStr("Camp Half-Blood");
    allTerritories.get(18).tryInitializeAdjacentTerrStr("North Pole");
    allTerritories.get(18).tryInitializeAdjacentTerrStr("Duke");
    allTerritories.get(18).tryInitializeAdjacentTerrStr("Wonka Chocolate Factory");
    allTerritories.get(18).tryInitializeAdjacentTerrStr("Atlantis");
    // District Twelve
    allTerritories.get(19).tryInitializeAdjacentTerrStr("Wonka Chocolate Factory");
    allTerritories.get(19).tryInitializeAdjacentTerrStr("Duke");
    // North Pole
    allTerritories.get(20).tryInitializeAdjacentTerrStr("The Capitol");
    allTerritories.get(20).tryInitializeAdjacentTerrStr("Camp Half-Blood");
    allTerritories.get(20).tryInitializeAdjacentTerrStr("Duke");
    // Atlantis
    allTerritories.get(21).tryInitializeAdjacentTerrStr("Diagon Alley");
    allTerritories.get(21).tryInitializeAdjacentTerrStr("Gotham City");
    allTerritories.get(21).tryInitializeAdjacentTerrStr("Jurassic Park");
    allTerritories.get(21).tryInitializeAdjacentTerrStr("The Capitol");
    allTerritories.get(21).tryInitializeAdjacentTerrStr("Wonka Chocolate Factory");
    // Wonka Chocolate Factory
    allTerritories.get(22).tryInitializeAdjacentTerrStr("Atlantis");
    allTerritories.get(22).tryInitializeAdjacentTerrStr("The Capitol");
    allTerritories.get(22).tryInitializeAdjacentTerrStr("Duke");
    allTerritories.get(22).tryInitializeAdjacentTerrStr("District Twelve");
    // Duke
    allTerritories.get(23).tryInitializeAdjacentTerrStr("Wonka Chocolate Factory");
    allTerritories.get(23).tryInitializeAdjacentTerrStr("The Capitol");
    allTerritories.get(23).tryInitializeAdjacentTerrStr("North Pole");
    allTerritories.get(23).tryInitializeAdjacentTerrStr("District Twelve");
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

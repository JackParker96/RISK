package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class MapTextViewTest {

  @Test
  public void test_MapTextView() throws MaxTechLevelException {
    // Create two players
    Player p1 = new BasicPlayer(new Color("blue"), "Maya");
    Player p2 = new BasicPlayer(new Color("red"), "Evan");
    // Create three territories
    BasicTerritory gondor = new BasicTerritory("gondor");
    BasicTerritory mordor = new BasicTerritory("mordor");
    BasicTerritory duke = new BasicTerritory("duke");
    // Set Territory owners
    gondor.setOwner(p1);
    mordor.setOwner(p1);
    duke.setOwner(p2);
    // Add Units to territories
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit();
    Unit u3 = new BasicUnit();
    Unit u4 = new BasicUnit();
    Unit u5 = new BasicUnit();
    Unit u6 = new BasicUnit();
    u1.increaseTechLevel(1);
    u2.increaseTechLevel(2);
    u6.increaseTechLevel(6);
    ArrayList<Unit> gondorUnits = new ArrayList<Unit>();
    ArrayList<Unit> mordorUnits = new ArrayList<Unit>();
    ArrayList<Unit> dukeUnits = new ArrayList<Unit>();
    gondorUnits.add(u1);
    gondorUnits.add(u2);
    gondorUnits.add(u3);
    mordorUnits.add(u4);
    mordorUnits.add(u5);
    dukeUnits.add(u6);
    gondor.addUnits(gondorUnits);
    mordor.addUnits(mordorUnits);
    duke.addUnits(dukeUnits);
    // Package the territories into a list
    ArrayList<Territory> terr_arr = new ArrayList<Territory>();
    terr_arr.add(gondor);
    terr_arr.add(mordor);
    terr_arr.add(duke);
    // Set up adjacencies
    gondor.addAdjacentTerritories(mordor);

    mordor.addAdjacentTerritories(gondor);
    mordor.addAdjacentTerritories(duke);
    duke.addAdjacentTerritories(mordor);
    // Create a map and use it to construct a MapTextView
    Map map = new Map(terr_arr, "my_awesome_map");
    MapTextView view = new MapTextView(map);
    // Test groupTerritoriesByPlayer method
    HashMap<Player, ArrayList<Territory>> ownershipInfo = map.groupTerritoriesByPlayer();
    assertEquals(true, ownershipInfo.containsKey(p1));
    assertEquals(true, ownershipInfo.containsKey(p2));
    assertEquals(2, ownershipInfo.get(p1).size());
    assertEquals(1, ownershipInfo.get(p2).size());
    for (Territory terr : ownershipInfo.get(p1)) {
      assertEquals(true, ((terr.equals(gondor)) || (terr.equals(mordor))));
    }
    assertEquals(duke, ownershipInfo.get(p2).get(0));
    // Test displayPlayerInfo method
    String expected1 = "MAYA Player:\n" +
        "------------\n" +
        "3 units in gondor (next to: mordor)\n" +
        "     1 units of level 0\n" +
        "     1 units of level 1\n" +
        "     1 units of level 2\n" +
        "2 units in mordor (next to: gondor, duke)\n" +
        "     2 units of level 0\n";
    assertEquals(expected1, view.displayPlayerInfo(p1, ownershipInfo.get(p1)));
    String expected2 = "EVAN Player:\n" +
        "------------\n" +
        "1 units in duke (next to: mordor)\n" +
        "     1 units of level 6\n";
    assertEquals(expected2, view.displayPlayerInfo(p2, ownershipInfo.get(p2)));
    // Test displayMap method
    String expected3 = expected1 + "\n" + expected2 + "\n";
    assertEquals(expected3, view.displayMap());

    // test initialize unitplacement order
    UnitPlacementOrder maya_upo = map.getUnitsPlacementOrder(p1);
    assertEquals("gondor", maya_upo.getName(0));
    assertEquals(0,maya_upo.getNumUnits(0));
    assertEquals("mordor",maya_upo.getName(1));
    assertEquals(0,maya_upo.getNumUnits(1));

    //test handle unitplacement order
    maya_upo.setNumUnits(0, 5);
    maya_upo.setNumUnits(1, 3);
    map.handleUnitPlacementOrder(maya_upo);
    assertEquals(8, gondor.getNumUnits());
    assertEquals(5,mordor.getNumUnits());
  }

}

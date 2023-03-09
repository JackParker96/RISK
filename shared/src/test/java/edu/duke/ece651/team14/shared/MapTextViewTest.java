package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class MapTextViewTest {

  public MapTextView createMapTextView() {
    Player p1 = new BasicPlayer(new Color("blue"), "Maya");
    Player p2 = new BasicPlayer(new Color("red"), "Evan");
    BasicTerritory gondor = new BasicTerritory("gondor");
    BasicTerritory mordor = new BasicTerritory("mordor");
    BasicTerritory duke = new BasicTerritory("duke");
    gondor.setOwner(p1);
    mordor.setOwner(p1);
    duke.setOwner(p2);
    ArrayList<Territory> terr_arr = new ArrayList<Territory>();
    terr_arr.add(gondor);
    terr_arr.add(mordor);
    terr_arr.add(duke);
    Map map = new Map(terr_arr, "my_awesome_map");
    MapTextView view = new MapTextView(map);
    return view;
  }

  @Test
  public void test_groupTerritoriesByPlayer() {
    MapTextView view = createMapTextView();
    HashMap<Player, ArrayList<Territory>> ownershipInfo = view.groupTerritoriesByPlayer();
    Player p1 = new BasicPlayer(new Color("blue"), "Maya");
    Player p2 = new BasicPlayer(new Color("red"), "Evan");
    BasicTerritory gondor = new BasicTerritory("gondor");
    BasicTerritory mordor = new BasicTerritory("mordor");
    BasicTerritory duke = new BasicTerritory("duke");
    gondor.setOwner(p1);
    mordor.setOwner(p1);
    duke.setOwner(p2);
    assertEquals(true, ownershipInfo.containsKey(p1));
    assertEquals(true, ownershipInfo.containsKey(p2));
    assertEquals(2, ownershipInfo.get(p1).size());
    assertEquals(1, ownershipInfo.get(p2).size());
    for (Territory terr : ownershipInfo.get(p1)) {
      assertEquals(true, ((terr.equals(gondor)) || (terr.equals(mordor))));
    }
    assertEquals(duke, ownershipInfo.get(p2).get(0));
  }

}

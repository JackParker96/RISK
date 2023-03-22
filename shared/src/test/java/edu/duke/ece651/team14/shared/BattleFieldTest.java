package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BattleFieldTest {

  public static BattleField oneResolve(Territory duke, Player blue_devil, Player attcker1, Player attacker2,
      CombatResolver cr) {
    BattleField bf = new BattleField(cr, duke);
    assertEquals(1, bf.getNumArmies());
    assertEquals(5, bf.getArmyUnitsNum(blue_devil));

    // one attacker
    AttackOrder aktOrder = new AttackOrder(new BasicTerritory("dont cate"), duke, 5, attcker1);
    AttackOrder aktOrder2 = new AttackOrder(new BasicTerritory("dont cate"), duke, 3, attcker1);
    bf.addAttackerArmy(aktOrder);
    bf.addAttackerArmy(aktOrder2);
    assertEquals(2, bf.getNumArmies());
    assertEquals(8, bf.getArmyUnitsNum(attcker1));
    // another attacker
    assertEquals(-1, bf.getArmyUnitsNum(attacker2));
    AttackOrder aktOrder3 = new AttackOrder(new BasicTerritory("dont cate"), duke, 3, attacker2);
    AttackOrder aktOrder4 = new AttackOrder(new BasicTerritory("dont cate"), duke, 4, attacker2);
    bf.addAttackerArmy(aktOrder3);
    bf.addAttackerArmy(aktOrder4);
    assertEquals(3, bf.getNumArmies());
    assertEquals(7, bf.getArmyUnitsNum(attacker2));
    return bf;
  }

  @Test
  public void test_adddefender_attackers() {
    UnitsFactory uf = new UnitsFactory();
    Territory duke = new BasicTerritory("Duke");
    Player blue_devil = new BasicPlayer(new Color("blue"), "BuleDevil");
    duke.setOwner(blue_devil);
    duke.addUnits(uf.makeUnits(5, "basic"));
    // make a mock resolver that always make defender wins
    CombatResolver mockResolver = mock(CombatResolver.class);
    Mockito.when(mockResolver.getCombatResult()).thenReturn(true);
    Player attcker1 = new BasicPlayer(new Color("yellow"), "attcker1");
    Player attacker2 = new BasicPlayer(new Color("green"), "Celtics");
    BattleField bf = oneResolve(duke, blue_devil,attcker1,attacker2,mockResolver);
    // test resolve
    bf.resolve();
    assertEquals(1, bf.getNumArmies());
    assertEquals(blue_devil, duke.getOwner());
    assertEquals(5, duke.getNumUnits());
    String expected_result = "The BuleDevil player defends the Territory duke successfully!";
    assertEquals(expected_result,bf.getResult());
  }

  @Test
  public void test_attacker_always_win() {
    UnitsFactory uf = new UnitsFactory();
    Territory duke = new BasicTerritory("Duke");
    Player blue_devil = new BasicPlayer(new Color("blue"), "BuleDevil");
    duke.setOwner(blue_devil);
    duke.addUnits(uf.makeUnits(5, "basic"));
    // make a mock resolver that always make attacker wins
    CombatResolver mockResolver = mock(CombatResolver.class);
    Mockito.when(mockResolver.getCombatResult()).thenReturn(false);
    Player attcker1 = new BasicPlayer(new Color("yellow"), "attcker1");
    Player attacker2 = new BasicPlayer(new Color("green"), "Celtics");
    BattleField bf = oneResolve(duke, blue_devil,attcker1,attacker2,mockResolver);
    // test resolve
    bf.resolve();
    assertEquals(1, bf.getNumArmies());
    assertEquals(attacker2, duke.getOwner());
    assertEquals(7, duke.getNumUnits());
  }

  @Test
  public void test_real_resolver(){
    UnitsFactory uf = new UnitsFactory();
    Territory duke = new BasicTerritory("Duke");
    Player blue_devil = new BasicPlayer(new Color("blue"), "BuleDevil");
    duke.setOwner(blue_devil);
    duke.addUnits(uf.makeUnits(5, "basic"));
    Player attcker1 = new BasicPlayer(new Color("yellow"), "attcker1");
    Player attacker2 = new BasicPlayer(new Color("green"), "Celtics");
    BattleField bf = oneResolve(duke, blue_devil,attcker1,attacker2,new DiceResolver());
    // test resolve
    bf.resolve();
    assertEquals(1, bf.getNumArmies());
    System.out.println(duke.getOwner());
  }
  

}

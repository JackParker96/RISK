package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BattleFieldTest {
  Unit defender;
  Unit attacker;

  @BeforeEach
  public void init() {
    this.defender = new BasicUnit();
    this.attacker = new BasicUnit();
  }

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
  public void test_defender_always_win() {
    UnitsFactory uf = new UnitsFactory();
    Territory duke = new BasicTerritory("Duke");
    Player blue_devil = new BasicPlayer(new Color("blue"), "BuleDevil");
    duke.setOwner(blue_devil);
    duke.addUnits(uf.makeUnits(5, "basic"));
    // make a mock resolver that always make defender wins
    CombatResolver mockResolver = mock(CombatResolver.class);
    Mockito.when(mockResolver.getCombatResult(defender, attacker)).thenReturn(true);
    Player attcker1 = new BasicPlayer(new Color("yellow"), "attcker1");
    Player attacker2 = new BasicPlayer(new Color("green"), "Celtics");
    BattleField bf = oneResolve(duke, blue_devil, attcker1, attacker2, mockResolver);
    // test resolve
    bf.resolve();
    assertEquals(1, bf.getNumArmies());
    assertEquals(attcker1, duke.getOwner());
    assertEquals(2, duke.getNumUnits());
  }

  @Test
  public void test_defender_no_unit() {
    Territory duke = new BasicTerritory("Duke");
    Player blue_devil = new BasicPlayer(new Color("blue"), "BuleDevil");
    duke.setOwner(blue_devil);
    // make a mock resolver that always make defender wins
    CombatResolver mockResolver = mock(CombatResolver.class);
    Mockito.when(mockResolver.getCombatResult(defender, attacker)).thenReturn(true);
    Player attcker1 = new BasicPlayer(new Color("yellow"), "attcker1");
    Player attacker2 = new BasicPlayer(new Color("green"), "Celtics");
    BattleField bf = new BattleField(mockResolver, duke);
    AttackOrder aktOrder = new AttackOrder(new BasicTerritory("dont cate"), duke, 5, attcker1);
    AttackOrder aktOrder2 = new AttackOrder(new BasicTerritory("dont cate"), duke, 3, attcker1);
    AttackOrder aktOrder3 = new AttackOrder(new BasicTerritory("dont cate"), duke, 3, attacker2);
    AttackOrder aktOrder4 = new AttackOrder(new BasicTerritory("dont cate"), duke, 4, attacker2);
    bf.addAttackerArmy(aktOrder);
    bf.addAttackerArmy(aktOrder2);
    bf.addAttackerArmy(aktOrder3);
    bf.addAttackerArmy(aktOrder4);
    // test resolve
    bf.resolve();
    assertEquals(attcker1, duke.getOwner());
    String expected_result = "\nOn Territory duke: \nThe BuleDevil player defends with 0 units\nThe attcker1 player attacks with 8 units\nThe Celtics player attacks with 7 units\nThe attcker1 player conquers Territory duke!";
    assertEquals(expected_result, bf.getResult());
  }

  @Test
  public void test_attacker_no_unit() {
    UnitsFactory uf = new UnitsFactory();
    Territory duke = new BasicTerritory("Duke");
    Player blue_devil = new BasicPlayer(new Color("blue"), "BuleDevil");
    duke.setOwner(blue_devil);
    duke.addUnits(uf.makeUnits(5, "basic"));
    // make a mock resolver that always make defender wins
    CombatResolver mockResolver = mock(CombatResolver.class);
    Mockito.when(mockResolver.getCombatResult(defender, attacker)).thenReturn(true);
    Player attcker1 = new BasicPlayer(new Color("yellow"), "attcker1");
    Player attacker2 = new BasicPlayer(new Color("green"), "Celtics");
    BattleField bf = new BattleField(mockResolver, duke);
    AttackOrder aktOrder = new AttackOrder(new BasicTerritory("dont cate"), duke, 0, attcker1);
    AttackOrder aktOrder2 = new AttackOrder(new BasicTerritory("dont cate"), duke, 0, attcker1);
    AttackOrder aktOrder3 = new AttackOrder(new BasicTerritory("dont cate"), duke, 3, attacker2);
    AttackOrder aktOrder4 = new AttackOrder(new BasicTerritory("dont cate"), duke, 4, attacker2);
    bf.addAttackerArmy(aktOrder);
    bf.addAttackerArmy(aktOrder2);
    bf.addAttackerArmy(aktOrder3);
    bf.addAttackerArmy(aktOrder4);
    // test resolve
    bf.resolve();
    assertEquals(attacker2, duke.getOwner());
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
    Mockito.when(mockResolver.getCombatResult(defender, attacker)).thenReturn(false);
    Player attcker1 = new BasicPlayer(new Color("yellow"), "attcker1");
    Player attacker2 = new BasicPlayer(new Color("green"), "Celtics");
    BattleField bf = oneResolve(duke, blue_devil, attcker1, attacker2, mockResolver);
    // test resolve
    bf.resolve();
    assertEquals(1, bf.getNumArmies());
    assertEquals(attcker1, duke.getOwner());
    assertEquals(2, duke.getNumUnits());
  }

  @Test
  public void test_real_resolver() {
    UnitsFactory uf = new UnitsFactory();
    Territory duke = new BasicTerritory("Duke");
    Player blue_devil = new BasicPlayer(new Color("blue"), "BuleDevil");
    duke.setOwner(blue_devil);
    duke.addUnits(uf.makeUnits(5, "basic"));
    Player attcker1 = new BasicPlayer(new Color("yellow"), "attcker1");
    Player attacker2 = new BasicPlayer(new Color("green"), "Celtics");
    BattleField bf = oneResolve(duke, blue_devil, attcker1, attacker2, new DiceResolver());
    // test resolve
    bf.resolve();
    assertEquals(1, bf.getNumArmies());
    System.out.println(duke.getOwner());
  }

  @Test
  public void test_sort() throws MaxTechLevelException {
    UnitsFactory uf = new UnitsFactory();
    ArrayList<Unit> units = uf.makeUnits(5, "basic");
    for (int i = 0; i < units.size(); i++) {
      units.get(i).increaseTechLevel(i);
    }
    units.sort((u0, u1) -> u1.getTechLevel() - u0.getTechLevel());
    for(int i=0;i<units.size();i++){
      assertEquals(units.size()-1-i, units.get(i).getTechLevel());
    }
    units.sort((u0, u1) -> u0.getTechLevel() - u1.getTechLevel());
    for(int i=0;i<units.size();i++){
      assertEquals(i, units.get(i).getTechLevel());
    }
  }

}

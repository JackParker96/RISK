package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DiceResolverTest {
  @Test
  public void test_diceresolver() {
    Unit defender = new BasicUnit();
    Unit attacker = new BasicUnit();
    DiceResolver cr = new DiceResolver();
    for (int seed = 0; seed <= 20; seed++) {
      cr.setSeed(seed);
      int dice1expected = cr.getRand().nextInt(20);
      int dice2expected = cr.getRand().nextInt(20);
      cr.setSeed(seed);// reset seed, to generate same result as above
      boolean expected_result = dice1expected >= dice2expected ? true : false;
      assertEquals(expected_result, cr.getCombatResult(defender,attacker));
    }
  }
}

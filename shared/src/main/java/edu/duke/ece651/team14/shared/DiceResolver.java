package edu.duke.ece651.team14.shared;

import java.util.HashMap;
import java.util.Random;

public class DiceResolver implements CombatResolver {
  private final Random rand;
  private static HashMap<Integer, Integer> bonus = new HashMap<>();
  static {
    bonus.put(0, 0);
    bonus.put(1, 1);
    bonus.put(2, 3);
    bonus.put(3, 5);
    bonus.put(4, 8);
    bonus.put(5, 11);
    bonus.put(6, 15);
  }

  public DiceResolver() {
    this.rand = new Random();
  }

  /**
   * For generating pesudo random numbers for testing.
   * 
   * @param seed
   */
  public void setSeed(int seed) {
    rand.setSeed(seed);
  }

  public Random getRand() {
    return rand;
  }

  /**
   * The defender wins if the dice number is larger or equal to the attacker
   * number.
   */
  @Override
  public boolean getCombatResult(Unit defender, Unit attacker) {
    int defender_num = rand.nextInt(20) + bonus.get(defender.getTechLevel());
    int attacker_num = rand.nextInt(20) + bonus.get(attacker.getTechLevel());
    return defender_num >= attacker_num;
  }

}

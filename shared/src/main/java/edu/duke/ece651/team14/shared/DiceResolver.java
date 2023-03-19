package edu.duke.ece651.team14.shared;

import java.util.Random;

public class DiceResolver implements CombatResolver {
  private final Random rand;

  public DiceResolver() {
    this.rand = new Random();
  }

  /** 
   * For generating pesudo random numbers for testing.
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
  public boolean getCombatResult() {
    int defender_num = rand.nextInt(20);// generate number between 0-19
    int attacker_num = rand.nextInt(20);
    return defender_num >= attacker_num;
  }

}

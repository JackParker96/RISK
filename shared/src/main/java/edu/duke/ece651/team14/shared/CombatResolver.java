package edu.duke.ece651.team14.shared;

/** 
Interface to decide whether the defender unit defend successfully.
e.g.,DiceResolver implements this interface.
*/
public interface CombatResolver {
  public boolean getCombatResult(Unit defender,Unit attacker);
}

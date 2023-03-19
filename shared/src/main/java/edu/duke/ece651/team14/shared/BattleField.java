package edu.duke.ece651.team14.shared;
import java.util.ArrayList;

public class BattleField {
  private final CombatResolver resolver;
  private final Territory location;

  /**
   * An Army identifies a player with all of its Units which are ready to fight.
   */
  private class Army {
    public final Player player;
    public final ArrayList<Unit> units;

    public Army(Player player, ArrayList<Unit> units) {
      this.player = player;
      this.units = units;
    }

    public Army(Player player) {
      this.player = player;
      this.units = new ArrayList<Unit>();
    }

    /**
     * Add another list of units to the army
     * 
     * @param newUnits
     */
    public void addUnits(ArrayList<Unit> newUnits) {
      this.units.addAll(newUnits);
    }

    /**
     * Return true if no units remains in this army.
     * 
     * @return
     */
    public boolean isDefeated() {
      return units.size() == 0;
    }

    /**
     * Get the unit to attend one fight.
     * 
     * @return
     */
    public Unit getUnitToFight() {
      return units.get(units.size() - 1);
    }

    /**
     * Precondition: the unit is dead.
     * Remove the unit if it dies in the fight
     */
    public void removeDeadUnit() {
      units.remove(units.size() - 1);
    }
  }

  private ArrayList<Army> combatList;

  public BattleField(CombatResolver resolver, Territory location) {
    this.resolver = resolver;
    this.location = location;
    this.combatList = new ArrayList<Army>();
    addDefenderArmy();
  }

  /**
   * Precondition: all move orders corresponding to this territory have taken
   * effect.
   */
  protected void addDefenderArmy() {
    Army defenderArmy = new Army(location.getOwner(), location.getUnits());
    combatList.add(defenderArmy);
  }

  /**
   * Precondition: the units from the attacker's territory have been moved out,(or
   * deleted).
   * Postcondition: new units according to the attack order will be created, and
   * are
   * put in the battlefield. These units are not belong to any territories yet.
   * 
   * @param atkOrder
   */
  public void addAttackerArmy(AttackOrder atkOrder) {
    Army attackerArmy = getArmy(atkOrder.getPlayer());
    AbstractUnitsFactory uf = new UnitsFactory();
    if (attackerArmy == null) {
      attackerArmy = new Army(atkOrder.getPlayer());
      attackerArmy.addUnits(uf.makeUnits(atkOrder.getNumUnits(), atkOrder.getUnitType()));
      combatList.add(attackerArmy);
    } else {
      attackerArmy.addUnits(uf.makeUnits(atkOrder.getNumUnits(), atkOrder.getUnitType()));
    }
  }

  /**
   * Resolve the combat, the army with the lower index are the defender, this
   * gives advantages to the player who originally own the territory
   */
  public void resolve() {
    while (!hasWinner()) {
      for (int i = 0; i < combatList.size(); i++) {
        int another_index = (i + 1) % combatList.size();
        int defender_index = Math.min(i, another_index);
        int attacker_index = Math.max(i, another_index);
        resolveOneFight(combatList.get(defender_index), combatList.get(attacker_index));
        if (hasWinner()) {
          break;
        }
      }
    }
    conquer();
  }

  /**
   * Have one unit from the defender and one unit from the attacker army fight.
   * 
   * @param defender
   * @param attacker
   */
  protected void resolveOneFight(Army defender, Army attacker) {
    boolean defenderWins = resolver.getCombatResult();
    if (defenderWins) {
      attacker.getUnitToFight().tryToKill();// defender wins and the attacker's unit dies
      // in this version tryTokill always succeed.
      attacker.removeDeadUnit();
      if (attacker.isDefeated()) {
        combatList.remove(attacker);
      }
    } else {
      defender.getUnitToFight().tryToKill();// attacker wins and the defender's unit dies
      defender.removeDeadUnit();
      if (defender.isDefeated()) {
        combatList.remove(defender);
      }
    }
  }

  /**
   * Return true if in the combatList there is only one army left.
   * 
   * @return
   */
  protected boolean hasWinner() {
    return combatList.size() == 1;
  }

  /**
   * Combat resolved, one army left, conquer the territory.
   */
  protected void conquer() {
    Player winner = combatList.get(0).player;
    if (!this.location.getOwner().equals(winner)) {// the ownership of this territory changed
      this.location.setOwner(winner);
      this.location.addUnits(combatList.get(0).units);
    } // else : no need to set owner, and the units remains there
  }

  /**
   * Return the army if the player's army are already in this battle field
   * Return null if the player's army does not exist.
   * 
   * @param player
   * @return
   */
  protected Army getArmy(Player player) {
    for (Army a : combatList) {
      if (a.player == player) {
        return a;
      }
    }
    return null;
  }

  /**
   * Return the number of armies in the battle field.
   * 
   * @return
   */
  public int getNumArmies() {
    return combatList.size();
  }

  /**
   * Return the units number of the army.
   * 
   * @param player
   * @return
   */
  public int getArmyUnitsNum(Player player) {
    for (Army a : combatList) {
      if (a.player == player) {
        return a.units.size();
      }
    }
    return -1;
  }
}
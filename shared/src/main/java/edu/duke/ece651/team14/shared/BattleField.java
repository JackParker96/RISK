package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

public class BattleField {
  private final CombatResolver resolver;
  private final Territory location;
  private String result;
  private Map map;
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
     * Precondition: isDefeated() return false;
     * 
     * @return return the unit to fight,
     */
    public Unit getUnitToFight() {
      return units.get(units.size() - 1);// error when unit size is 0;must satisfy precondition
    }

    /**
     * Precondition: the unit is dead.
     * Remove the unit if it dies in the fight
     */
    public void removeDeadUnit() {
      units.remove(units.size() - 1);
    }

    /**
     * Sort the unit by ascending order of level
     */
    public void ascSort() {
      units.sort((u0, u1) -> u0.getTechLevel() - u1.getTechLevel());
    } 

    /**
     * Sort the unit by descending order of level.
     */
    public void descSort() {
      units.sort((u0, u1) -> u1.getTechLevel() - u0.getTechLevel());
    }
  }

  private ArrayList<Army> combatList;

  public BattleField(CombatResolver resolver, Territory location, Map map) {
    this.resolver = resolver;
    this.location = location;
    this.combatList = new ArrayList<Army>();
    this.result = "\nOn Territory " + this.location + ": \n";
    this.map = map;
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
    if (attackerArmy == null) {
      attackerArmy = new Army(atkOrder.getPlayer());
      transferUnits(attackerArmy, atkOrder);
      combatList.add(attackerArmy);
    } else {
      transferUnits(attackerArmy, atkOrder);
    }
  }


  /**
   * Transfer units from territory to the battle field.
   */
  private void transferUnits(Army attackerArmy,AttackOrder atkOrder){
    Territory attack_origin = map.getTerritoryByName(atkOrder.getOrigin().getName());
    ArrayList<Unit> units = attack_origin.getUnits();
    units.sort((u0, u1) -> u0.getTechLevel() - u1.getTechLevel());
    ArrayList<Unit> temp = new ArrayList<>();
    for(int i=0;i<atkOrder.getNumUnits();i++){
      Unit atkUnit = units.remove(0);
      temp.add(atkUnit);
    }
    attackerArmy.addUnits(temp);
  }

  /**
   * Resolve the combat, the army with index i is defender, and (i+i)%N is
   * attacker.
   * Note attacker and defender switches roles
   */
  public void resolve() {
    this.result += recordBattleInfo();
    while (!hasWinner()) {
      for (int i = 0; i < combatList.size(); i++) {
        int another_index = (i + 1) % combatList.size();
        Army defender = combatList.get(i);
        Army attacker = combatList.get(another_index);
        defender.descSort();// defender has last unit with lowest bonus to fight
        attacker.ascSort();// attacker has last unit with highest bonus to fight
        resolveOneFight(defender, attacker);
        if (hasWinner()) {
          break;
        }
      }
    }
    conquer();
  }

  /**
   * Return detail information about the battle.
   */
  protected String recordBattleInfo() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < combatList.size(); i++) {
      Player p = combatList.get(i).player;
      int numUnits = combatList.get(i).units.size();
      String action = "";
      if (i == 0) {
        action = "defends";
      } else {
        action = "attacks";
      }
      sb.append("The " + p + " player " + action + " with " + numUnits + " units\n");
    }
    return sb.toString();
  }

  /**
   * Have one unit from the defender and one unit from the attacker army fight.
   * 
   * @param defender
   * @param attacker
   */
  protected void resolveOneFight(Army defender, Army attacker) {
    if (attacker.isDefeated()) {
      combatList.remove(attacker);
    } else if (defender.isDefeated()) {
      combatList.remove(defender);
    } else {// both has left unit to fight
      Unit defenderUnit = defender.getUnitToFight();
      Unit attackerUnit = attacker.getUnitToFight();
      boolean defenderWins = resolver.getCombatResult(defenderUnit, attackerUnit);
      if (defenderWins) {
        attackerUnit.tryToKill();// defender wins and the attacker's unit dies
        attacker.removeDeadUnit();
        if (attacker.isDefeated()) {
          combatList.remove(attacker);
        }
      } else {// defender Loses
        defenderUnit.tryToKill();// attacker wins and the defender's unit dies
        defender.removeDeadUnit();
        if (defender.isDefeated()) {
          combatList.remove(defender);
        }
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
      this.result += "The " + winner.getName() + " player conquers Territory " + this.location.getName() + "!";
    } // else : no need to set owner, and the units remains there
    else {
      this.result += "The " + winner.getName() + " player defends Territory " + this.location.getName()
          + " successfully!";
    }
  }

  /**
   * Returns the combat result.
   */
  public String getResult() {
    return this.result;
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

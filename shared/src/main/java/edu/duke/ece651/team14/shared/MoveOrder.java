package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/**
 * Class to represent move order
 */
public class MoveOrder extends Order {
  private ArrayList<Unit> unitsPicked;

  /**
   * Creates a MoveOrder
   *
   * @param origin      is the origin Territory
   * @param destination is the destination Territory
   * @param numUnits    is the number of units to send
   * @param player      is the player making the order
   * @param unitType    is the type of unit to send
   */
  public MoveOrder(Territory origin, Territory destination, int numUnits, Player player, String unitType) {
    super(origin, destination, numUnits, player, unitType, "move");
    this.unitsPicked = new ArrayList<Unit>();
  }

  /**
   * Creates a MoveOrder
   *
   * @param origin      is the origin Territory
   * @param destination is the destination Territory
   * @param numUnits    is the number of units to send
   * @param player      is the player making the order
   */
  public MoveOrder(Territory origin, Territory destination, int numUnits, Player player) {
    super(origin, destination, numUnits, player, "move");
    this.unitsPicked = new ArrayList<Unit>();
  }

  public int calculateCost() {
    unitsPicked.clear();
    int cost = 0;
    ArrayList<Unit> units = new ArrayList<Unit>();
    for (Unit u : getOrigin().getUnits()) {
      units.add(u);
    }
    units.sort((u0, u1) -> u0.getTechLevel() - u1.getTechLevel());  // sort units by ascending order
    for (int i = 0; i < getNumUnits(); i++) {
      Unit moveUnit = units.remove(0);
      unitsPicked.add(moveUnit);
      int distance = getPlayer().findShortestPath(getOrigin(), getDestination());
      cost += 2 * distance; 
    }
    return cost;
  }

  public ArrayList<Unit> getUnitsPicked() {
    return unitsPicked;
  }

}

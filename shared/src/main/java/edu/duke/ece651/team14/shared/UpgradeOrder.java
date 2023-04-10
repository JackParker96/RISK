
package edu.duke.ece651.team14.shared;

import java.util.ArrayList;
/**
 * Class to represent an upgrade order
 */
public class UpgradeOrder extends Order {
  private int currTechLevel;
  private int newTechLevel;
  private int prices[];

  /**
   * Creates an UpgradeOrder
   *
   * @param origin        is the origin Territory
   * @param destination   is the destination Territory
   * @param numUnits      is the number of units to upgrade
   * @param player        is the player making the order
   * @param unitType      is the type of unit to send - difference from other
   *                      constructor
   * @param currTechLevel is the current tech level of the units to upgrade
   * @param newTechLevel  is the new tech level for the units being upgraded
   */
  public UpgradeOrder(Territory origin, Territory destination, int numUnits, Player player,
      String unitType, int currTechLevel, int newTechLevel) {
    super(origin, destination, numUnits, player, unitType, "upgrade");
    this.currTechLevel = currTechLevel;
    this.newTechLevel = newTechLevel;
    this.prices = new int[]{3, 8, 19, 25, 35, 50};
  }

  /**
   * Creates an UpgradeOrder
   *
   * @param origin        is the origin Territory
   * @param destination   is the destination Territory
   * @param numUnits      is the number of units to upgrade
   * @param player        is the player making the order
   * @param currTechLevel is the current tech level of the units to upgrade
   * @param newTechLevel  is the new tech level for the units being upgraded
   */
  public UpgradeOrder(Territory origin, Territory destination, int numUnits, Player player,
      int currTechLevel, int newTechLevel) {
    super(origin, destination, numUnits, player, "upgrade");
    this.currTechLevel = currTechLevel;
    this.newTechLevel = newTechLevel;
    this.prices = new int[]{3, 8, 19, 25, 35, 50};
  }

  public int calculateCost() {
    int cost = 0;
    for (int i = currTechLevel; i < newTechLevel; i++) {
      cost += prices[i];
    }
    return cost * getNumUnits();
  }

  public int getCurrTechLevel() {
    return currTechLevel;
  }

  public int getNewTechLevel() {
    return newTechLevel;
  }

}

package edu.duke.ece651.team14.shared;

import java.io.Serializable;

public class BasicUnit implements Unit, Serializable {
  // The type of the unit (may need multiple types of units in future versions)
  private String type;
  // Boolean representing whether the Unit is alive or dead
  private boolean alive;
  // The current teech level of the Unit
  private int techLevel;

  /**
   * Construct a BasicUnit
   *
   * @param type is the type of unit (in version 1 there is only one type of unit)
   */
  public BasicUnit() {
    this.type = "basic";
    this.alive = true;
    this.techLevel = 0;
  }

  /**
   * Increase the tech level of a single unit
   *
   * @param numLevels is the number of levels the unit should be upgraded
   * @throws MaxTechLevelException    if incrementing by numLevels would put the
   *                                  units techLevel over 6
   * @throws IllegalArgumentException if you try to increase by a negative number
   */
  public void increaseTechLevel(int numLevels) throws MaxTechLevelException {
    if (techLevel + numLevels > 6) {
      throw new MaxTechLevelException("Units can only be upgraded up to tech level 6");
    }
    if (numLevels < 0) {
      throw new IllegalArgumentException("Can't increase tech level by a negative number");
    }
    techLevel += numLevels;
  }

  public int getTechLevel() {
    return techLevel;
  }

  /**
   * Kill a unit (if it isn't already dead)
   *
   * @return true if the unit wasn't already dead
   * @throws IllegalArgumentException if the unit is alreay dead
   */
  @Override
  public boolean tryToKill() {
    if (!alive) {
      throw new IllegalArgumentException("Unit already dead");
    }
    alive = false;
    return true;
  }

  // Get the type of the BasicUnit
  @Override
  public String getType() {
    return type;
  }

  // Check if the unit is alive
  @Override
  public boolean isAlive() {
    return alive;
  }

  // Check if two BasicUnits are the same by checking if they are both the same
  // type and are both alive or both dead
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      BasicUnit otherBasicUnit = (BasicUnit) other;
      return (type.equals(otherBasicUnit.getType()) && alive == otherBasicUnit.isAlive());
    }
    return false;
  }

  @Override
  public String toString() {
    String status = "";
    if (alive) {
      status = "alive";
    } else {
      status = "dead";
    }
    return "BasicUnit - " + type + " - " + status;
  }

  @Override
  public int hashCode() {
    int status = 0;
    if (alive) {
      status = 1;
    }
    return type.hashCode() + status;
  }
}

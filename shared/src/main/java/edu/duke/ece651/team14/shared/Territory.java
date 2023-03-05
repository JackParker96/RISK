package edu.duke.ece651.team14.shared;

import java.io.Serializable;

/**
 * Abstract class to represent RISC territory
 */
public abstract class Territory implements Serializable{
  private final String name;

  /**
   * Creates a Territory from a given name
   *
   * @param name is the Territory name
   */
  public Territory(String name) {
    this.name = name.toLowerCase();
  }

  /**
   * Returns territory name
   *
   * @return territory name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns true if territories have same class and same name
   *
   * @param other is the other object to compare
   *
   * @return true if equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;
      return name.equals(otherTerritory.getName());
    }
    return false;
  }

  /**
   * Returns territory name
   *
   * @return territory name
   */
  @Override
  public String toString() {
    return name;
  }

  /**
   * Returns hashcode from territory name
   *
   * @return hashcode from territory name
   */
  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}

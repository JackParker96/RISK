package edu.duke.ece651.team14.shared;

import java.io.Serializable;

/**
 * Abstract class to represent RISC territory
 */
public abstract class Territory implements Serializable{
  private final String name;
  private Player owner;

  /**
   * Creates a Territory from a given name
   *
   * @param name is the Territory name
   */
  public Territory(String name) {
    this.name = name.toLowerCase();
    this.owner = null;
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
   * Get the Player who owns this Territory
   *
   * @return the Player who owns the Territory
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Make a Player the owner of this Territory
   */
  public void setOwner(Player player) {
    this.owner = player;
  }
  
  /**
   * Returns true if territories have same class, same name, same owner
   *
   * @param other is the other object to compare
   *
   * @return true if equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    // Check for same class (both Territories)
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;
      // Check for same name
      if (!name.equals(otherTerritory.getName())) {
        return false;
      }
      // If one or the other owners is null, check that both are null
      if (owner == null || otherTerritory.getOwner() == null) {
        return (owner == null && otherTerritory.getOwner() == null);
      }
      // If both owners are not null, check that they're the same
      return owner.equals(otherTerritory.getOwner());
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

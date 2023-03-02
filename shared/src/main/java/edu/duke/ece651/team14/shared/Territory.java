package edu.duke.ece651.team14.shared;

public class Territory {
  private final String name;

  public Territory(String name) {
    this.name = name;
  }

  /**
   * Get the name of the territory
   * 
   * @return the name of the territory
   */
  public String getName() {
    return name;
  }

  /**
   * Check if another object is equivalent to this object (compare data, not
   * references)
   * 
   * @param other is the other object to be compared to this Territory object
   * @return true if the two objects are both Territory objects and both have the
   *         same attributes, otherwise return false
   */
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;
      return name == otherTerritory.getName();
    }
    return false;
  }

  /**
   * Get a string representation of a Territory object
   * 
   * @return the name of the Territory
   */
  @Override
  public String toString() {
    return name;
  }

  /**
   * Get a hash code for a Territory for the purposes of putting a Territory in a
   * HashMap
   * 
   * @return the hash code for a Territory - just use the built-in hashCode()
   *         method from Java's String class
   */
  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}

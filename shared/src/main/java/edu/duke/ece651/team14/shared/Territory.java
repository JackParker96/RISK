package edu.duke.ece651.team14.shared;

public class Territory {
  private final String name;

  public Territory(String name) {
    this.name = name.toLowerCase();
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;
      return name.equals(otherTerritory.getName());
    }
    return false;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}

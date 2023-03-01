package edu.duke.ece651.team14.shared;

public class Territory {
  private final String name;

  public Territory(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Territory otherTerritory = (Territory) other;
      return name == otherTerritory.getName();
    }
    return false;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    return (name.charAt(0) + name.charAt(1) + name.charAt(name.length() - 1)) / name.length();
  }
}

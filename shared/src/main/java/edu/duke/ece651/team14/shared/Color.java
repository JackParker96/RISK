package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/**
 * A class to represent a color
 * Can only be red, green, blue, or yellow
 */
public class Color {
  private final String color;

  /**
   * Constructor for a Color
   *
   * @return a Color object of the specified color
   * @throws IllegalArgumentException if the color is not red, green, blue, or
   *                                  yellow
   */
  public Color(String color) {
    ArrayList<String> colors = new ArrayList<String>();
    colors.add("red");
    colors.add("green");
    colors.add("blue");
    colors.add("yellow");
    color = color.toLowerCase();
    if (!colors.contains(color)) {
      throw new IllegalArgumentException("Color can only be red, green, blue, or yellow but instead was " + color);
    }
    this.color = color;
  }

  public String getColor() {
    return color;
  }
  
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Color otherColor = (Color) other;
      return color.equals(otherColor.getColor());
    }
    return false;
  }

  @Override
  public String toString() {
    return color;
  }

  @Override
  public int hashCode() {
    return color.hashCode();
  }
}

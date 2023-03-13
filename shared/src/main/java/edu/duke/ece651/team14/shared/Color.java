package edu.duke.ece651.team14.shared;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A class to represent a color
 * Can only be red, green, blue, or yellow
 */
public class Color implements Serializable{
  public static final HashMap<String, Color> colors;

  static {
    colors = new HashMap<>();
    colors.put("red", new Color(255, 0, 0));
    colors.put("green", new Color(0, 255, 0));
    colors.put("blue", new Color(0, 0, 255));
    colors.put("yellow", new Color(255, 255, 0));
  }

  public final int r;
  public final int g;
  public final int b;
  // Opacity value
  public final int a;

  /**
   * Creates a color object with given values for r, g, b
   *
   * @param r is the red value
   *
   * @param g is the g value
   *
   * @param b is the b value
   *
   * @throws IllegalArgumentException if r, g, b values are not valid (in range
   *                                  [0, 255])
   */
  public Color(int r, int g, int b) {
    if (!isValidColorValue(r) || !isValidColorValue(g) || !isValidColorValue(b)) {
      throw new IllegalArgumentException("Given color values are invalid");
    }
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = 1;
  }

  /**
   * Creates a color object from another color object
   *
   * @param c is the other color object
   */
  public Color(Color c) {
    this.r = c.r;
    this.g = c.g;
    this.b = c.b;
    this.a = c.a;
  }

  /**
   * Creates a color object with one of the default color values
   *
   * @param color is the default color name
   *
   * @throws IllegalArgumentException if given color is not one of the default colors
   */
  public Color(String color) {
    this(getDefaultColor(color.toLowerCase()));
  }

  /**
   * Returns true if r, g, b, a, are equal
   *
   * @param other is the color to compare
   *
   * @return true if equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Color otherColor = (Color) other;
      return ((r == otherColor.r) && (g == otherColor.g) && (b == otherColor.b) && (a == otherColor.a));
    }
    return false;
  }


  /**
   * Returns string in format "r: <r> g: <g> b: <b> a <a>
   *
   * @return string representation of color
   */
  @Override
  public String toString() {
    return "r: " + r + " g: " + g + " b: " + b + " a: " + 1;
  }

  /**
   * Returns hashCode from toString() method
   *
   * @return the hashCode
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Helper method to return default color, if it exists, to be used in Color
   * constructor
   *
   * @param colorName is the color name
   *
   * @throws IllegalArgumentException if color is not a default color
   *
   * @return desired Color object
   */
  private static Color getDefaultColor(String colorName) {
    if (colors.containsKey(colorName)) {
      return colors.get(colorName);
    } else {
      throw new IllegalArgumentException("Given color, " + colorName + " is not a valid option");
    }
  }

  /**
   * Helper method to check if given value would be a valid rgb value
   *
   * @param color value
   *
   * @return true if valid, false otherwise
   */
  private static boolean isValidColorValue(int colorValue) {
    return ((colorValue >= 0) && (colorValue <= 255));
  }
}

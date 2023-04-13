package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/**
 * A class to represent a GUI player in the RISC game
 */
public class GUIPlayer extends Player {
  public GameModel model;

  public GUIPlayer(Color color, String name, GameModel model) {
    super(color, name);
    this.model = model;
  }

  /**
   * At the start of each turn, update the amount of resources each player has
   * based on their territories
   *
   * @param m is the map of all territories
   */
  @Override
  public void updateResourcesInTurn(Map m) {
    ArrayList<Territory> myTerrs = m.groupTerritoriesByPlayer().get(this);
    int foodProduced = 0;
    int techProduced = 0;
    for (Territory t : myTerrs) {
      foodProduced += t.getFoodProductionRate();
      techProduced += t.getTechProductionRate();
    }
    addFoodResources(foodProduced);
    addTechResources(techProduced);
  }

  @Override
  public void addFoodResources(int toAdd) {
    if (toAdd < 0) {
      throw new IllegalArgumentException("Can't add a negative number of resources");
    }
    foodAmt += toAdd;
    model.foodResources.set(model.getFoodResources() + toAdd);
  }

  @Override
  public void useFoodResources(int toUse) {
    if (toUse < 0) {
      throw new IllegalArgumentException("Can't use a negative number of resources");
    }
    if (toUse > foodAmt) {
      throw new IllegalArgumentException("Not enough food resources");
    }
    foodAmt -= toUse;
    model.foodResources.set(model.getFoodResources() - toUse);
  }

  @Override
  public void addTechResources(int toAdd) {
    if (toAdd < 0) {
      throw new IllegalArgumentException("Can't add a negative number of resources");
    }
    techAmt += toAdd;
    model.techResources.set(model.getTechResources() + toAdd);
  }

  @Override
  public void useTechResources(int toUse) {
    if (toUse < 0) {
      throw new IllegalArgumentException("Can't use a negative number of resources");
    }
    if (toUse > techAmt) {
      throw new IllegalArgumentException("Not enough tech resources");
    }
    techAmt -= toUse;
    model.techResources.set(model.getTechResources() - toUse);
  }

  /**
   * Increment the max tech level of a Player by 1
   *
   * @throws MaxTechLevelException if the player's maxTechLevel is already maxed
   *                               out at 6
   */
  @Override
  public void increaseMaxTechLevel() throws MaxTechLevelException {
    if (maxTechLevel == 6) {
      throw new MaxTechLevelException("Max tech level is already maxed out for this player");
    }
    maxTechLevel += 1;
    model.maxTechLevel.set(model.getMaxTechLevel() + 1);
  }

  public int getFoodAmt() {
    return foodAmt;
  }

  public int getTechAmt() {
    return techAmt;
  }

  public int getMaxTechLevel() {
    return maxTechLevel;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Player otherPlayer = (Player) other;
      return name.equals(otherPlayer.getName());
    }
    return false;
  }
}

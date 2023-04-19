package edu.duke.ece651.team14.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * A class to represent a GUI player in the RISC game
 */
public class GUIPlayer extends Player {
  public GameModel model;

  public GUIPlayer(Color color, String name, GameModel model) {
    super(color, name);
    this.model = model;
  }

  // Increment the player's aggression points by 1
  // Check if they get a reward. If so, distribute that reward
  public void addAggPt() throws MaxTechLevelException {
    // Add 1 aggression point to the model
    model.aggPts.set(model.getAggPts() + 1);
    // Figure out if player gets a reward
    int pts = model.getAggPts();
    // 3 aggression points -> Reward Level 1
    if (pts == 3) {
      rewardLevel1();
    }
    // 5 aggression points -> Reward Level 2
    if (pts == 5) {
      rewardLevel2();
    }
    // 8, 10, 12, 14, ... aggression points -> Reward Level 3
    if ((pts >= 8) && (pts % 2 == 0)) {
      rewardLevel3();
    }
  }

  // Reset aggression points to 0
  public void resetAggPts() {
    model.aggPts.set(0);
  }

  /**
   * Reward the player for collecting 3 aggression points
   * Add 5 Level 1 units to a random territory controlled by the player
   */
  public void rewardLevel1() throws MaxTechLevelException {
    // Create a list of 5 level 1 units
    ArrayList<Unit> toAdd = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      BasicUnit u = new BasicUnit();
      u.increaseTechLevel(1);
      toAdd.add(u);
    }
    // Search through the map until a territory owned by this player is found
    // When found, add the 5 units to that territory
    HashMap<String, Territory> m = model.getMap().getMap();
    Collection<Territory> terrs = m.values();
    for (Territory terr : terrs) {
      if (this.equals(terr.getOwner())) {
        terr.addUnits(toAdd);
        break;
      }
    }
  }

  /**
   * Reward player for collecting 5 aggression points
   * Upgrade the player to the next max tech level for free
   */
  public void rewardLevel2() throws MaxTechLevelException {
    if (this.getMaxTechLevel() != 6) {
      this.increaseMaxTechLevel();
    }
  }

  /**
   * Reward player for collecting 8, 10, 12, 14, etc. aggression points
   * Increase the level of every single one of the player's units
   */
  public void rewardLevel3() throws MaxTechLevelException {
    // Search through the map and find all the territories owned by this player
    HashMap<String, Territory> m = model.getMap().getMap();
    Collection<Territory> terrs = m.values();
    for (Territory terr : terrs) {
      if (this.equals(terr.getOwner())) {
        // For each territory owned by this player, increase the level of all the units
        // on that territory by 1
        ArrayList<Unit> units = terr.getUnits();
        for (Unit u : units) {
          if (u.getTechLevel() != 6) {
            u.increaseTechLevel(1);
          }
        }
      }
    }
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
    if (other != null) {
      Player otherPlayer = (Player) other;
      return name.equals(otherPlayer.getName());
    }
    return false;
  }

  @Override
  public void setAggPts(int points) {
    this.aggPts = points;
    this.model.aggPts.set(points);
  }
}

package edu.duke.ece651.team14.client;

import edu.duke.ece651.team14.shared.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameModel {

  Map map;
  // Code needs to change this in whoamiphase
  public String playerName;
  public IntegerProperty mapCount;
  public IntegerProperty foodResources;
  public IntegerProperty techResources;
  public IntegerProperty maxTechLevel;
  public BooleanProperty hasLost;
  public StringProperty selectedTerritory;
  public StringProperty gameLogText;

  public GameModel(Map map, int mapCount, int foodResources, int techResources, int maxTechLevel) {
    this.map = map;
    this.mapCount = new SimpleIntegerProperty(mapCount);
    this.foodResources = new SimpleIntegerProperty(foodResources);
    this.techResources = new SimpleIntegerProperty(techResources);
    this.maxTechLevel = new SimpleIntegerProperty(maxTechLevel);
    this.hasLost = new SimpleBooleanProperty(false);
    this.selectedTerritory = new SimpleStringProperty("");
    this.gameLogText = new SimpleStringProperty("");
  }

  public Map getMap() {
    return map;
  }

  /**
   * Reassigns the map to the given map. Increments mapCount by 1
   *
   * @param map is the new map
   */
  public void setMap(Map map) {
    this.map = map;
    mapCount.set(getMapCount() + 1);
  }

  public int getFoodResources() {
    return foodResources.get();
  }

  public int getTechResources() {
    return techResources.get();
  }

  public int getMaxTechLevel() {
    return maxTechLevel.get();
  }

  public int getMapCount() {
    return mapCount.get();
  }

  public String getGameLogText() {
    return gameLogText.get();
  }

}

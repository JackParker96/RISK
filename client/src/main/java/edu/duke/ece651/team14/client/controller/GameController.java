package edu.duke.ece651.team14.client.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import edu.duke.ece651.team14.client.GUIClientPlayer;
import edu.duke.ece651.team14.client.GameModel;
import edu.duke.ece651.team14.shared.BasicUnit;
import edu.duke.ece651.team14.shared.MapTextView;
import edu.duke.ece651.team14.shared.Unit;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GameController implements Initializable {
  @FXML
  Label howToPlay;

  @FXML
  Label exitGame;

  @FXML
  TextArea territoryStatsText;

  @FXML
  TextArea gameLogText;

  @FXML
  GUIController guiController;

  @FXML
  InputButtonsController inputButtonsController;
  
  // @FXML
  // ListView<String> gameLogList;

  GameModel model;
  GUIClientPlayer client;

  public GameController(GameModel model, GUIClientPlayer client) {
    this.model = model;
    this.client = client;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    inputButtonsController.gameLogText = gameLogText;
    guiController.gameLogText = gameLogText;
    try {
      model.setMap(client.recvMap());
      MapTextView view = new MapTextView(model.getMap());
      gameLogText.setText(view.displayMap());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void initialize() {
    model.selectedTerritory.addListener((obs, oldValue, newValue) -> {
      territoryStatsText.setText(newValue);
    });
    model.gameLogText.addListener((obs, oldValue, newValue) -> {
      gameLogText.setText(newValue);
      // Platform.runLater(() ->
      // gameLogText.scrollTopProperty().set(Double.MAX_VALUE));
      // gameLogList.getItems().add(newValue);
      // gameLogList.scrollTo(gameLogList.getItems().size() - 1);
      // gameLogList.getSelectionModel().select(gameLogList.getItems().size() - 1);
      // gameLogList.getFocusModel().focus(gameLogList.getItems().size() - 1);
      // System.out.println(gameLogText.getScrollTop());
    });
  }

  public void setTerrText(ObservableValue<String> obs, String oldValue, String newValue) {
    territoryStatsText.setText("");
  }

  public void setTerrText(String newValue) {
    String pl = "placeholder";
    StringBuilder sb = new StringBuilder();
    sb.append("Territory: " + newValue + "\n");
    sb.append("Owner: " + pl + "\n");
    sb.append("Food Production Rate: " + pl + "\n");
    sb.append("Technology Production Rate: " + pl + "\n");
    HashMap<Integer, Integer> unitInfo = unitTechLevels(null); // change null to units array list
    for (int i = 0; i < 7; i++) {
      if (unitInfo.get(i) > 0) {
        sb.append("Level " + i + " Units: " + unitInfo.get(i) + "\n");
      }
    }
    sb.append("Unit Level 0: " + pl + "\n");
    sb.append("Unit Level 1: " + pl + "\n"); // only list if unit number for type greater than 0
    sb.append("\nNOTE: adjacent territories are each 1 distance away");
    territoryStatsText.setText(sb.toString());
  }

  protected HashMap<Integer, Integer> unitTechLevels(ArrayList<Unit> units) {
    HashMap<Integer, Integer> unitInfo = new HashMap<Integer, Integer>();
    for (int i = 0; i < 7; i++) {
      unitInfo.put(i, 0);
    }
    for (Unit u : units) {
      if (u.getType() == "basic") {
        BasicUnit b = (BasicUnit) u;
        int techLevel = b.getTechLevel();
        unitInfo.put(techLevel, unitInfo.get(techLevel) + 1);
      }
    }
    return unitInfo;
  }

  public void setPlayerText() {
    String pl = "placeholder";
    StringBuilder sb = new StringBuilder();
    sb.append("Player: " + pl + "\n");
    sb.append("Maximum Technology Level: " + model.getMaxTechLevel() + "\n");
    sb.append("Total Food Resources: " + model.getFoodResources() + "\n");
    sb.append("Total Technology Resources: " + model.getTechResources() + "\n");
    territoryStatsText.setText(sb.toString());
  }

  @FXML
  /**
   * Displays Alert Box with information on how to play game. Gets text from
   * howToPlay.txt file
   *
   * @throws IOException
   * @throws URISyntaxException
   */
  private void onHowToPlay() throws IOException, URISyntaxException {
    Alert howToPlayAlert = new Alert(AlertType.INFORMATION);
    String helpText = new String(Files.readAllBytes(Paths.get(getClass().getResource("/ui/howToPlay.txt").toURI())));
    howToPlayAlert.setContentText(helpText);
    howToPlayAlert.setHeaderText("How to Play");
    howToPlayAlert.setTitle("How To Play");
    howToPlayAlert.show();
  }
}

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
import edu.duke.ece651.team14.shared.GameModel;
import edu.duke.ece651.team14.shared.MapTextView;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.Unit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class GameController implements Initializable {
  @FXML
  Label howToPlay;

  @FXML
  Label exitGame;

  @FXML
  TextArea territoryStatsText;

  @FXML
  TextArea userStatsText;

  @FXML
  TextArea gameLogText;

  @FXML
  GUIController guiController;

  @FXML
  InputButtonsController inputButtonsController;

  HashMap<String, String> terrNames = new HashMap<String, String>();

  // @FXML
  // ListView<String> gameLogList;

  GameModel model;

  GUIClientPlayer client;

  public GameController(GameModel model, GUIClientPlayer client) {
    this.model = model;
    this.client = client;
    terrNames.put("midkemia_l", "midkemia");
    terrNames.put("gondor_l", "gondor");
    terrNames.put("oz_l", "oz");
    terrNames.put("neverland_l", "neverland");
    terrNames.put("narnia_l", "narnia");
    terrNames.put("mordor_l", "mordor");
    terrNames.put("scadrial_l", "scadrial");
    terrNames.put("elantris_l", "elantris");
    terrNames.put("olympus_l", "mt olympus");
    terrNames.put("roshar_l", "roshar");
    terrNames.put("othrys_l", "mt othrys");
    terrNames.put("camp_half_blood_l", "camp half-blood");
    terrNames.put("gotham_city_l", "gotham city");
    terrNames.put("diagon_alley_l", "diagon alley");
    terrNames.put("hogwarts_l", "hogwarts");
    terrNames.put("platform_l", "platform 9 and 3/4");
    terrNames.put("jurassic_park_l", "jurassic park");
    terrNames.put("wakanda_l", "wakanda");
    terrNames.put("district12_l", "district twelve");
    terrNames.put("duke_l", "duke");
    terrNames.put("north_pole_l", "north pole");
    terrNames.put("wonka_l", "wonka chocolate factory");
    terrNames.put("atlantis_l", "atlantis");
    terrNames.put("capitol_l", "the capitol");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setPlayerText();
    model.selectedTerritory.addListener((obs, oldValue, newValue) -> {
      System.out.println("In GameModel printing newValue: " + newValue);
      setTerrText(newValue);
    });
    // model.gameLogText.addListener((obs, oldValue, newValue) -> {
    // gameLogText.setText(newValue);
    // });
    model.foodResources.addListener((obs, oldValue, newValue) -> {
      setPlayerText();
    });
    model.techResources.addListener((obs, oldValue, newValue) -> {
      setPlayerText();
    });
    model.maxTechLevel.addListener((obs, oldValue, newValue) -> {
      setPlayerText();
    });
    inputButtonsController.gameLogText = gameLogText;
    guiController.gameLogText = gameLogText;
    try {
      model.setMap(client.recvMap());
      //MapTextView view = new MapTextView(model.getMap());
      //gameLogText.setText(view.displayMap());
      this.client.getPlayer().updateResourcesInTurn(model.getMap());
      inputButtonsController.gameLogshowPlayer();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /*
   * public void initialize() {
   * setPlayerText();
   * model.selectedTerritory.addListener((obs, oldValue, newValue) -> {
   * setTerrText(newValue);
   * });
   * model.gameLogText.addListener((obs, oldValue, newValue) -> {
   * gameLogText.setText(newValue);
   * // Platform.runLater(() ->
   * // gameLogText.scrollTopProperty().set(Double.MAX_VALUE));
   * // gameLogList.getItems().add(newValue);
   * // gameLogList.scrollTo(gameLogList.getItems().size() - 1);
   * // gameLogList.getSelectionModel().select(gameLogList.getItems().size() - 1);
   * // gameLogList.getFocusModel().focus(gameLogList.getItems().size() - 1);
   * // System.out.println(gameLogText.getScrollTop());
   * });
   * }
   */

  public void setTerrText(String newValue) {
    StringBuilder sb = new StringBuilder();
    Territory t = model.getMap().getTerritoryByName(terrNames.get(newValue));
    System.out.println(model.getSelectedTerritory());
    sb.append("Territory: " + t.getName() + "\n");
    sb.append("Owner: " + t.getOwner() + "\n");
    sb.append("Food Production Rate: " + t.getFoodProductionRate() + "\n");
    sb.append("Technology Production Rate: " + t.getTechProductionRate() + "\n");
    HashMap<Integer, Integer> unitInfo = unitTechLevels(t.getUnits());
    System.out.println(unitInfo.size());
    for (int i = 0; i < 7; i++) {
      if (unitInfo.get(i) > 0) {
        sb.append("Level " + i + " Units: " + unitInfo.get(i) + "\n");
      }
    }
    sb.append("\nNOTE: adjacent territories are each 1 distance away");
    territoryStatsText.setText(sb.toString());
  }

  protected HashMap<Integer, Integer> unitTechLevels(ArrayList<Unit> units) {
    HashMap<Integer, Integer> unitInfo = new HashMap<Integer, Integer>();
    for (int i = 0; i < 7; i++) {
      unitInfo.put(i, 0);
    }
    for (Unit u : units) {
      int techLevel = u.getTechLevel();
      unitInfo.put(techLevel, unitInfo.get(techLevel) + 1);
    }
    return unitInfo;
  }

  public void setPlayerText() {
    StringBuilder sb = new StringBuilder();
    sb.append("Player: " + model.getPlayerName() + "\n");
    sb.append("Maximum Technology Level: " + model.getMaxTechLevel() + "\n");
    sb.append("Total Food Resources: " + model.getFoodResources() + "\n");
    sb.append("Total Technology Resources: " + model.getTechResources() + "\n");
    sb.append("Aggression Points: " + model.getAggPts() + "\n");
    userStatsText.setText(sb.toString());
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

  @FXML
  private void OnExit() {
    Stage stage = (Stage) exitGame.getScene().getWindow();
    boolean exit = ConfirmBox.display("Exit the game", "Are you sure you want to exit?", "Yes, I want to exit", "No");
    if (exit) {
      stage.close();
    }
  }
}

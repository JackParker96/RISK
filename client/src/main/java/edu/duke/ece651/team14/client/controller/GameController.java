package edu.duke.ece651.team14.client.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.duke.ece651.team14.client.GameModel;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GameController {
  @FXML
  Label howToPlay;

  @FXML
  Label exitGame;

  @FXML
  TextArea territoryStatsText;

  @FXML
  TextArea gameLogText;

  // @FXML
  // ListView<String> gameLogList;

  GameModel model;

  public GameController(GameModel model) {
    this.model = model;
  }

  public void initialize() {
    model.selectedTerritory.addListener((obs, oldValue, newValue) -> {
        
      territoryStatsText.setText(newValue);
    });
    model.gameLogText.addListener((obs, oldValue, newValue) -> {
      gameLogText.setText(newValue);
      // Platform.runLater(() -> gameLogText.scrollTopProperty().set(Double.MAX_VALUE));
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

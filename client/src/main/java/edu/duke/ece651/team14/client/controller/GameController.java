package edu.duke.ece651.team14.client.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.beans.property.StringProperty;
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

  StringProperty terrText;

  public GameController(StringProperty s) {
    this.terrText = s;
  }

  public void initialize() {
    territoryStatsText.textProperty().bindBidirectional(terrText);
  }

  
  public void setTerrText(String text) {
    territoryStatsText.setText(text);
  }

  @FXML
  /**
   * Displays Alert Box with information on how to play game. Gets text from howToPlay.txt file
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

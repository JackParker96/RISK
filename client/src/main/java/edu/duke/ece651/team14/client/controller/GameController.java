package edu.duke.ece651.team14.client.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GameController implements Initializable {
  @FXML
  Label howToPlay;

  @FXML
  Label exitGame;

  public void initialize(URL location, ResourceBundle resources) {

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

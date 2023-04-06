package edu.duke.ece651.team14.client.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIController {
  private Stage stage;
  private Scene scene;

  @FXML
  private Text actiontarget;
  @FXML
  public TextArea txtBox;

  @FXML
  private Label midkemia_b;
  @FXML
  private Label gondor_b;
  @FXML
  private Label oz_b;
  @FXML
  private Label neverland_b;
  @FXML
  private Label narnia_b;
  @FXML
  private Label mordor_b;
  @FXML
  private Label scadrial_b;
  @FXML
  private Label elantris_b;
  @FXML
  private Label olympus_b;
  @FXML
  private Label roshar_b;
  @FXML
  private Label othrys_b;
  @FXML
  private Label camp_half_blood_b;
  @FXML
  private Label gotham_city_b;
  @FXML
  private Label diagon_alley_b;
  @FXML
  private Label hogwarts_b;
  @FXML
  private Label platform_b;
  @FXML
  private Label jurassic_park_b;
  @FXML
  private Label wakanda_b;
  @FXML
  private Label district12_b;
  @FXML
  private Label duke_b;
  @FXML
  private Label north_pole_b;
  @FXML
  private Label wonka_b;
  @FXML
  private Label atlantis_b;
  @FXML
  private Label capitol_b;

  @FXML
  public void handleSubmitButtonAction(ActionEvent event) throws IOException {
    actiontarget.setText("Sign in button pressed");
    URL url = new File("src/main/resources/ui/game.xml").toURI().toURL();
    Parent root = FXMLLoader.load(url);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  protected Label getBackgroundID(String terr_id) {
    Label id;
    switch (terr_id) {
      case "midkemia_l":
        id = midkemia_b;
        break;
      case "gondor_l":
        id = gondor_b;
        break;
      case "oz_l":
        id = oz_b;
        break;
      case "neverland_l":
        id = neverland_b;
        break;
      case "narnia_l":
        id = narnia_b;
        break;
      case "mordor_l":
        id = mordor_b;
        break;
      case "scadrial_l":
        id = scadrial_b;
        break;
      case "elantris_l":
        id = elantris_b;
        break;
      case "olympus_l":
        id = olympus_b;
        break;
      case "roshar_l":
        id = roshar_b;
        break;
      case "othrys_l":
        id = othrys_b;
        break;
      case "camp_half_blood_l":
        id = camp_half_blood_b;
        break;
      case "gotham_city_l":
        id = gotham_city_b;
        break;
      case "diagon_alley_l":
        id = diagon_alley_b;
        break;
      case "hogwarts_l":
        id = hogwarts_b;
        break;
      case "platform_l":
        id = platform_b;
        break;
      case "jurassic_park_l":
        id = jurassic_park_b;
        break;
      case "wakanda_l":
        id = wakanda_b;
        break;
      case "district12_l":
        id = district12_b;
        break;
      case "duke_l":
        id = duke_b;
        break;
      case "north_pole_l":
        id = north_pole_b;
        break;
      case "wonka_l":
        id = wonka_b;
        break;
      case "atlantis_l":
        id = atlantis_b;
        break;
      default:
        id = capitol_b;
        break;
    }
    return id;
  }

  @FXML
  public void chooseTerritory(MouseEvent event) throws IOException {
    Label terr = (Label) event.getSource();
    String id = terr.getId();
    Label terr_back = getBackgroundID(id);
    
    //for testing: turn territory to bright blue if clicked - CHANGE 
    terr_back.setStyle("-fx-background-color: #0096FF");
  }

  @FXML
  public void click(ActionEvent event) {
    txtBox.setWrapText(true);
    txtBox.setText("Initializing game setup...");
    Thread t = new Thread(() -> {
      try {
        txtBox.appendText(
            "\n\nYou are the [BLANK] player. Please add units to your territories. You can add 0 or more units to each territory, as long as you do not exceed the total number allowed.");
      } finally {
      }
    });
    t.start();
  }

}

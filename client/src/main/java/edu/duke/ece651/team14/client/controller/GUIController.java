package edu.duke.ece651.team14.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import edu.duke.ece651.team14.client.GUIClientPlayer;
import edu.duke.ece651.team14.shared.GameModel;
import edu.duke.ece651.team14.shared.Territory;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GUIController implements Initializable {
  StringProperty terrText;

  GameModel model;

  HashMap<String, String> playerColors = new HashMap<String, String>();

  HashMap<String, String> terrNames = new HashMap<String, String>();

  ArrayList<Label> labels;

  @FXML
  TextArea gameLogText;

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

  Label[] labels2;

  GUIClientPlayer client;

  public GUIController(GameModel model, GUIClientPlayer client) {
    this.model = model;
    this.client = client;
    terrNames.put("midkemia_b", "midkemia");
    terrNames.put("gondor_b", "gondor");
    terrNames.put("oz_b", "oz");
    terrNames.put("neverland_b", "neverland");
    terrNames.put("narnia_b", "narnia");
    terrNames.put("mordor_b", "mordor");
    terrNames.put("scadrial_b", "scadrial");
    terrNames.put("elantris_b", "elantris");
    terrNames.put("olympus_b", "mt olympus");
    terrNames.put("roshar_b", "roshar");
    terrNames.put("othrys_b", "mt othrys");
    terrNames.put("camp_half_blood_b", "camp half-blood");
    terrNames.put("gotham_city_b", "gotham city");
    terrNames.put("diagon_alley_b", "diagon alley");
    terrNames.put("hogwarts_b", "hogwarts");
    terrNames.put("platform_b", "platform 9 and 3/4");
    terrNames.put("jurassic_park_b", "jurassic park");
    terrNames.put("wakanda_b", "wakanda");
    terrNames.put("district12_b", "district twelve");
    terrNames.put("duke_b", "duke");
    terrNames.put("north_pole_b", "north pole");
    terrNames.put("wonka_b", "wonka chocolate factory");
    terrNames.put("atlantis_b", "atlantis");
    terrNames.put("capitol_b", "the capitol");

    playerColors.put("red", "#F4CCCC");
    playerColors.put("green", "#D9EAD3");
    playerColors.put("blue", "#CFE2F3");
    playerColors.put("yellow", "#FFF2CC");
    labels = new ArrayList<>();
  }

  @Override
  public void initialize(URL url, ResourceBundle r) {
        terrNames.put("midkemia_b", "midkemia");
    terrNames.put("gondor_b", "gondor");
    terrNames.put("oz_b", "oz");
    terrNames.put("neverland_b", "neverland");
    terrNames.put("narnia_b", "narnia");
    terrNames.put("mordor_b", "mordor");
    terrNames.put("scadrial_b", "scadrial");
    terrNames.put("elantris_b", "elantris");
    terrNames.put("olympus_b", "mt olympus");
    terrNames.put("roshar_b", "roshar");
    terrNames.put("othrys_b", "mt othrys");
    terrNames.put("camp_half_blood_b", "camp half-blood");
    terrNames.put("gotham_city_b", "gotham city");
    terrNames.put("diagon_alley_b", "diagon alley");
    terrNames.put("hogwarts_b", "hogwarts");
    terrNames.put("platform_b", "platform 9 and 3/4");
    terrNames.put("jurassic_park_b", "jurassic park");
    terrNames.put("wakanda_b", "wakanda");
    terrNames.put("district12_b", "district twelve");
    terrNames.put("duke_b", "duke");
    terrNames.put("north_pole_b", "north pole");
    terrNames.put("wonka_b", "wonka chocolate factory");
    terrNames.put("atlantis_b", "atlantis");
    terrNames.put("capitol_b", "the capitol");

    playerColors.put("red", "#F4CCCC");
    playerColors.put("green", "#D9EAD3");
    playerColors.put("blue", "#CFE2F3");
    playerColors.put("yellow", "#FFF2CC");

    labels.addAll(Arrays.asList(midkemia_b, gondor_b, mordor_b, oz_b, neverland_b, narnia_b, scadrial_b, elantris_b, olympus_b,
        roshar_b, othrys_b, camp_half_blood_b, gotham_city_b, diagon_alley_b, hogwarts_b, platform_b, jurassic_park_b,
        wakanda_b, district12_b, duke_b, north_pole_b, wonka_b, atlantis_b, capitol_b));
    labels2 = new Label[]{ midkemia_b, gondor_b, mordor_b, oz_b, neverland_b, narnia_b, scadrial_b, elantris_b, olympus_b, roshar_b,
        othrys_b, camp_half_blood_b, gotham_city_b, diagon_alley_b, hogwarts_b, platform_b, jurassic_park_b, wakanda_b,
        district12_b, duke_b, north_pole_b, wonka_b, atlantis_b, capitol_b };
    addSelectedListener();
    addMapUpdateListener();
  }

  /**
   * Updates the territory colors on map updates
   */
  public void addMapUpdateListener() {
    model.mapCount.addListener((obs, oldValue, newValue) -> {
      System.out.println("Map Count" + newValue);
      for (Label b : labels) {
        String id = b.getId();
        String actualName = terrNames.get(id);
        Territory terr = model.getMap().getTerritoryByName(actualName);
        String color = playerColors.get(terr.getOwner().getName());
        b.setStyle("-fx-background-color: " + color);
      }
    });
  }

  /**
   * Changes given territoiry background color to newColor
   * 
   * @param territory is the Label representing the territory
   * @param newColor  is the new color
   */
  public void changeTerritoryColor(Label territory, Color newColor) {
    System.out.println(territory);
    BackgroundFill obf = territory.getBackground().getFills().get(0);
    territory.setBackground(new Background(new BackgroundFill(newColor, obf.getRadii(), obf.getInsets())));
  }

  /**
   * Adds a listener which darkens the territory color when selected
   */
  public void addSelectedListener() {
    model.selectedTerritory.addListener((obs, oldValue, newValue) -> {
      Label newLabel = getBackgroundID(newValue);
      BackgroundFill oldBackground = newLabel.getBackground().getFills().get(0);
      Color oc = (Color) oldBackground.getFill();
      newLabel.setBackground(new Background(
          new BackgroundFill(new Color(Math.min(1.0, oc.getRed() * 0.8), Math.min(1.0, oc.getGreen() * 0.8),
              Math.min(1.0, oc.getBlue() * 0.8), 1), oldBackground.getRadii(), oldBackground.getInsets())));
      if (!oldValue.equals("")) {
        Label oldLabel = getBackgroundID(oldValue);
        BackgroundFill ob = oldLabel.getBackground().getFills().get(0);
        Color c = (Color) ob.getFill();
        oldLabel.setBackground(new Background(new BackgroundFill(new Color(Math.min(1.0, c.getRed() * 1.25),
            Math.min(1.0, c.getGreen() * 1.25), Math.min(1.0, c.getBlue() * 1.25), 1), ob.getRadii(), ob.getInsets())));
      }
    });
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

    System.out.println("Source of event: " + terr);
    String id = terr.getId();

    Label terr_back = getBackgroundID(id);

    System.out.println("Background: " + terr_back);

    System.out.println("Label id" + id);

    model.selectedTerritory.set(id);
    // terrText.set(id + "\nUnits: 7\nOwner: Red\nPotato: Tomato");

    // for testing: turn territory to bright blue if clicked - CHANGE
    // terr_back.setStyle("-fx-background-color: #0096FF");
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

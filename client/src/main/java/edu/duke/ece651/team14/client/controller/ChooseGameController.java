package edu.duke.ece651.team14.client.controller;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import edu.duke.ece651.team14.client.App;
import edu.duke.ece651.team14.client.GUIClientPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChooseGameController implements Initializable {
  @FXML
  RadioButton CreateNewGameButton;
  @FXML
  VBox JoinBox;
  @FXML
  VBox ReJoinBox;
  @FXML
  ToggleGroup group;
  @FXML
  ChoiceBox<String> ChoiceBox;

  GUIClientPlayer client;
  ArrayList<ArrayList<Integer>> gamechoices;

  public ChooseGameController(GUIClientPlayer client) throws Exception {
    this.client = client;
  }

  // dynamically add radio buttons
  private void initLayout() throws Exception {
    this.gamechoices = client.getGameChoice();// receive from server's game choices
    ArrayList<Integer> JoinableGames = gamechoices.get(0);
    ArrayList<Integer> RejoinableGames = gamechoices.get(1);
    for (int game_id : JoinableGames) {
      RadioButton button = new RadioButton(String.valueOf(game_id));
      button.setToggleGroup(group);
      JoinBox.getChildren().add(button);
    }
    for (int game_id : RejoinableGames) {
      RadioButton button = new RadioButton(String.valueOf(game_id));
      button.setToggleGroup(group);
      ReJoinBox.getChildren().add(button);
    }
  }

  private boolean isJoinGame(String game_id) {
    return gamechoices.get(0).contains(Integer.valueOf(game_id));
  }

  private boolean isRejoinGame(String game_id) {
    return gamechoices.get(1).contains(Integer.valueOf(game_id));
  }

  // called when loaded from xml
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      initLayout();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  //On user press button "Confirm"
  @FXML
  public void handleSubmitButtonAction(ActionEvent event) throws Exception {
    RadioButton selected = (RadioButton) group.getSelectedToggle();
    String game_id = selected.getText();
    String num_players = ChoiceBox.getValue();
    if (game_id.equals("Create a new Game")) {
      System.out.println("New Game: " + "0" + num_players);
      client.sendGameChoice("0" + num_players);
    } else {
      System.out.println("Join game: " + game_id);
      client.sendGameChoice(game_id);
    }
    client.resetCommunicator();
    client.whoAmIPhase();
    if (game_id.equals("Create a new Game") || isJoinGame(game_id)) {
      switchScene(event);
      //TODO: switch to the scene that support initial placement phase
    }else if(isRejoinGame(game_id)){
      // TODO: add support for rejoin game
      // direct switch to the scene to play game, skip initial placements phase.
    }
  }

  private void switchScene(ActionEvent event) throws IOException{
    URL url = App.class.getResource("/ui/init_units.fxml");
    FXMLLoader loader = new FXMLLoader(url);
    loader.setControllerFactory((c) -> {
        return client.getControllerInitializer().get(c);
    });
    Parent root = loader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  
}

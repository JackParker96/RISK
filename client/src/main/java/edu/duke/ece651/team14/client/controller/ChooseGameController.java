package edu.duke.ece651.team14.client.controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import edu.duke.ece651.team14.client.GUIClientPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class ChooseGameController implements Initializable{
  @FXML
  RadioButton CreateNewGameButton;
  @FXML
  VBox JoinBox;
  @FXML
  VBox ReJoinBox;
  @FXML
  ToggleGroup group;

  GUIClientPlayer client;
  ArrayList<ArrayList<Integer>> gamechoices;
  
  public ChooseGameController(GUIClientPlayer client) throws Exception {
    this.client = client;
  }

  //dynamically add radio buttons
  private void initLayout() throws Exception{
    this.gamechoices = client.getGameChoice();//receive from server's game choices
    ArrayList<Integer> JoinableGames = new ArrayList<Integer>(Arrays.asList(1,2,3,5));//gamechoices.get(0);
    ArrayList<Integer> RejoinableGames = new ArrayList<Integer>(Arrays.asList(9,10,11));//gamechoices.get(1);
    for(int game_id:JoinableGames){
      RadioButton button = new RadioButton(String.valueOf(game_id));
      button.setToggleGroup(group);
      JoinBox.getChildren().add(button);
    }
    for(int game_id:RejoinableGames){
      RadioButton button = new RadioButton(String.valueOf(game_id));
      button.setToggleGroup(group);
      ReJoinBox.getChildren().add(button);
    }
  }

  //called when loaded from xml
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try{
      initLayout();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  @FXML
  public void handleSubmitButtonAction(ActionEvent event){
    RadioButton selected = (RadioButton) group.getSelectedToggle();
    String value = selected.getText();
    if(value.equals("Create a new Game")){
      System.out.print("0");
    }else{
      System.out.print(value);
    }
  }
}

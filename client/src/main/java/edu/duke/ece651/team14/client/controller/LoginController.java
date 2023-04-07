package edu.duke.ece651.team14.client.controller;

import java.net.URL;

import edu.duke.ece651.team14.client.App;
import edu.duke.ece651.team14.client.GUIClientPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
  GUIClientPlayer client;//model
  @FXML
  TextField userName;
  @FXML
  TextField password;
  @FXML
  Text loginResult;
  
  public LoginController(GUIClientPlayer client){
    this.client = client;
  }

  @FXML
  public void handleSubmitButtonAction(ActionEvent event) throws Exception{
    boolean login_result = client.login(userName.getText(), password.getText());
    if(login_result == true){//login success
      switchScene(event);
    }else{
      loginResult.setText("Invalid Account!");
      loginResult.setFill(Color.RED);
      userName.clear();
      password.clear();
    }
  }


  private void switchScene(ActionEvent event) throws Exception{
    URL url = App.class.getResource("/ui/chooseGame.fxml");
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

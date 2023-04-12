package edu.duke.ece651.team14.client.controller;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.duke.ece651.team14.client.App;
import edu.duke.ece651.team14.client.GUIClientPlayer;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InitUnitsController implements Initializable {

  @FXML
  VBox Terr_list;
  @FXML
  Text Result;

  GUIClientPlayer client;
  UnitPlacementOrder upo;

  public InitUnitsController(GUIClientPlayer client) {
    this.client = client;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      Map m = this.client.recvMap();
      System.out.println("receive map");
      upo = m.getUnitsPlacementOrder(this.client.getPlayer());
      init_layout();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void init_layout() {
    for (int i = 0; i < upo.size(); i++) {
      String terr_name = upo.getName(i);
      TextField tf = new TextField();
      Label terr_label = new Label(terr_name);
      terr_label.setTextFill(Color.WHITE);
      HBox hbox = new HBox();
      hbox.setSpacing(10);
      hbox.setPadding(new Insets(10));
      hbox.getChildren().addAll(terr_label, tf);
      Terr_list.getChildren().add(hbox);
    }
  }

  @FXML
  public void handleSubmitButtonAction(ActionEvent event) throws Exception {
    try {
      checkInput();
      Result.setText("Wait for other players to finish...");
      this.client.getCommunicator().sendObject(upo);
      System.out.println("Switch Scene called");
      switchScene(event);
    } catch (IllegalArgumentException e) {
      Result.setText(e.getMessage());
      Result.setFill(Color.RED);
    } catch (Exception ee) {
      Result.setText(ee.getMessage());
      Result.setFill(Color.RED);
    }
  }

  /** 
   * Switch to play game view.
   * @param event
   * @throws IOException
   */
  private void switchScene(ActionEvent event) throws IOException{
    URL url = App.class.getResource("/ui/game.fxml");
    FXMLLoader loader = new FXMLLoader(url);
    loader.setControllerFactory((c) -> {
        return client.getControllerInitializer().get(c);
    });
    Parent root = loader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }

  private void checkInput() {
    int sum = 0;
    int index = 0;
    for (Node child : Terr_list.getChildren()) {
      if (child instanceof HBox) {
        HBox hbox = (HBox) child;
        for (Node grandchild : hbox.getChildren()) {
          if (grandchild instanceof TextField) {
            TextField tf = (TextField) grandchild;
            int unit_num = Integer.parseInt(tf.getText());
            if (unit_num < 0) {
              throw new IllegalArgumentException("Unit number should not less than 0");
            }
            upo.setNumUnits(index, unit_num);
            sum += unit_num;
            System.out.print("Input units num: " + unit_num);
          }
        }
      }
      index++;
    }
    if (sum != 30) {
      throw new IllegalArgumentException("You should put all your 30 units");
    }
    System.out.println("Total :" + sum);
  }

}

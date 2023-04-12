package edu.duke.ece651.team14.client.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Pop up a confirm box, reference:thenewboston JavaFX Java GUI Tutorial - 6 -
 * Communicating Between Windows
 */
public class ConfirmBox {
  static boolean ans = false;

  public static boolean display(String title, String message, String choice1, String choice2) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);// DISABLE THE ORIGIN WINDOW
    window.setTitle(title);
    window.setMinWidth(250);
    Label label = new Label();
    label.setText(message);

    Button yes = new Button(choice1);
    Button no = new Button(choice2);

    yes.setOnAction(e -> {
      ans = true;
      window.close();
    });

    no.setOnAction(e -> {
      ans = false;
      window.close();
    });

    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, yes, no);
    layout.setAlignment(Pos.CENTER);
    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
    return ans;
  }

}

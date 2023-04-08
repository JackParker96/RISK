package edu.duke.ece651.team14.client.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public class InputButtonsController {

  ArrayList<String> terrs;

  public InputButtonsController() {
    terrs = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      terrs.add("Territory" + i);
    }
  }

  @FXML
  public void onMove(ActionEvent e) {
    printSource(e);
    int a = getInteger("Enter something");
    System.out.println(a);
  }

  @FXML
  public void onAttack(ActionEvent e) {
    printSource(e);
    String t = getChoice("Choose territory", terrs);
    System.out.println(t);
  }

  @FXML
  public void onResearch(ActionEvent e) {

  }

  @FXML
  public void onUpgrade(ActionEvent e) {

  }

  @FXML
  public void onFinishTurn(ActionEvent e) {

  }

  public void printSource(ActionEvent e) {
    System.out.println(e.getSource());
  }

  /**
   * Calls tryGetInteger until the user enters a valid integer
   *
   * @param prompt is the prompt to display to the user
   *
   * @return int the integer entered by the user
   */
  public int getInteger(String prompt) {
    while (true) {
      Optional<Integer> o = tryGetInteger(prompt);
      if (o.isPresent()) {
        return o.get();
      }
    }
  }

  /**
   * Prompts the user for an integer. Allows the user to close box, in which case
   * the Optional<Integer> is empty.
   *
   * @param prompt is the prompt to display to the user
   *
   * @return Optional<Integer>: empty if user cancels/hits x, containing integer
   *         otherwise.
   */
  public Optional<Integer> tryGetInteger(String prompt) {
    boolean isValid = false;
    boolean enteredInvalidInput = false;
    int num = 0;

    while (!isValid) {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Enter Number");
      if (!enteredInvalidInput) {
        dialog.setHeaderText(prompt);
      } else {
        dialog.setHeaderText(prompt + "\n\nPlease enter valid integer");
      }
      dialog.setGraphic(null);
      Optional<String> result = dialog.showAndWait();
      System.out.println("Waited");
      if (result.isPresent()) {
        try {
          System.out.println("here");
          num = Integer.parseInt(result.get());
          return (Optional<Integer>) Optional.of(num);
        } catch (Exception e) {
          System.out.println("invalid");
          enteredInvalidInput = true;
        }
      } else {
        Optional<Integer> o = Optional.empty();
        return o;
      }
    }
    return Optional.of(0);
  }

  public <T> T getChoice(String prompt, List<T> options) {
    while (true) {
      Optional<T> o = tryGetChoice(prompt, options);
      if (o.isPresent()) {
        return o.get();
      }
    }
  }

  /**
   * Prompts the user to choose from a list of specified options
   *
   * @param prompt  is the prompt to show the user
   * @param options is the list of options to display
   *
   * @return an Optional<String>: empty if user hits cancel/x button, otherwise
   *         contains user selection
   */
  public <T> Optional<T> tryGetChoice(String prompt, List<T> options) {
    ChoiceDialog<T> dialog = new ChoiceDialog<>(options.get(0), options);
    dialog.setHeaderText(prompt);
    dialog.setTitle("Enter Choice");
    dialog.setGraphic(null);
    return dialog.showAndWait();
  }

}

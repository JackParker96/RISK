package edu.duke.ece651.team14.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import edu.duke.ece651.team14.shared.GameModel;
import edu.duke.ece651.team14.client.GUIClientPlayer;
import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.GUIOrderprocessor;
import edu.duke.ece651.team14.shared.MapTextView;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.ResearchOrder;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UpgradeOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class InputButtonsController implements Initializable {
  ArrayList<String> terrs;

  GameModel model;

  GUIClientPlayer client;

  GUIOrderprocessor processor;

  @FXML
  TextArea gameLogText;
  
  @FXML
  Button upgrade;

  @FXML
  Button move;

  @FXML
  Button attack;

  @FXML
  Button research;

  @FXML
  Button confirm;

  /**
   * state to indicate which button should be disabled
   */
  int state;

  public InputButtonsController(GameModel model, GUIClientPlayer clientPlayer) {
    this.terrs = new ArrayList<>();
    this.model = model;
    this.client = clientPlayer;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    switchState(1);
    processor = new GUIOrderprocessor(model.getMap());
  }

  /**
   * Manipulate state
   * button state:
   * 1. upgrade and confirm
   * 2. move and confirm
   * 3. attack and confirm
   * 4. research and confirm
   * 5. finish
   */
  private void switchState(int new_state) {
    switch (new_state) {
      case 1:
        upgrade.setDisable(false);
        move.setDisable(true);
        attack.setDisable(true);
        research.setDisable(true);
        confirm.setText("Finish Upgrade");
        state = 1;
        break;
      case 2:
        upgrade.setDisable(true);
        confirm.setText("Finish Move");
        move.setDisable(false);
        attack.setDisable(true);
        research.setDisable(true);
        state = 2;
        break;
      case 3:
        upgrade.setDisable(true);
        confirm.setText("Finish Attack");
        move.setDisable(true);
        attack.setDisable(false);
        research.setDisable(true);
        state = 3;
        break;
      case 4:
        upgrade.setDisable(true);
        confirm.setText("Finish Research");
        move.setDisable(true);
        attack.setDisable(true);
        research.setDisable(false);
        state = 4;
        break;
      case 5:
        upgrade.setDisable(true);
        confirm.setText("Finish Turn");
        move.setDisable(true);
        attack.setDisable(true);
        research.setDisable(true);
        state = 5;
    }
  }

  @FXML
  public void onMove(ActionEvent e) {
    resetOwnedTerrs();
    String origin = getChoice("Choose origin territory", terrs);
    String destination = getChoice("Choose destination territory", terrs);
    int numUnits = getInteger("Enter number of units to move");
    Territory origin_terr = this.model.getMap().getTerritoryByName(origin);
    Territory dest_terr = this.model.getMap().getTerritoryByName(destination);
    Order move = new MoveOrder(origin_terr, dest_terr, numUnits, this.client.getPlayer());
    try {
      int food = processor.processMove(this.model.getMap(), move, this.client.getPlayer());
      processor.addOrder(new MoveOrder(origin_terr, dest_terr, numUnits, this.client.getBasicPlayer()));
      gameLogText.appendText("Valid move order with food cost: " + food + "\n");
      //gameLogShowMap();
      gameLogshowPlayer();
    } catch (Exception ee) {
      gameLogText.appendText(ee.getMessage());
    }
  }

  @FXML
  public void onAttack(ActionEvent e) {
    resetOwnedTerrs();
    String origin = getChoice("Choose origin territory", terrs);
    setAllTerrs();
    String destination = getChoice("Choose territory you want to attack", terrs);
    int numUnits = getInteger("Enter number of units to attack");
    Territory origin_terr = this.model.getMap().getTerritoryByName(origin);
    Territory dest_terr = this.model.getMap().getTerritoryByName(destination);
    Order attackOrder = new AttackOrder(origin_terr, dest_terr, numUnits, this.client.getPlayer());
    try {
      int food = processor.processAttack(this.model.getMap(), attackOrder, this.client.getPlayer());
      processor.addOrder(new AttackOrder(origin_terr, dest_terr, numUnits, this.client.getBasicPlayer()));
      gameLogText.appendText("Valid attack order with food cost: " + food + "\n");
      //gameLogShowMap();
      gameLogshowPlayer();
    } catch (Exception ee) {
      gameLogText.appendText(ee.getMessage());
    }
  }

  @FXML
  public void onResearch(ActionEvent e) {
    resetOwnedTerrs();
    Order ro = new ResearchOrder(this.client.getPlayer());
    try {
      int tech = processor.processResearch(this.model.getMap(), ro, this.client.getPlayer());
      gameLogText.appendText("Valid research order with tech cost: " + tech + "\n");
    } catch (Exception ee) {
      gameLogText.appendText(ee.getMessage());
    }
    switchState(5);
  }

  @FXML
  public void onUpgrade(ActionEvent e) {
    resetOwnedTerrs();
    String origin = getChoice("Choose origin territory", terrs);
    int cur_level = getInteger("Enter the current level of units you want to upgrade");
    int new_level = getInteger("Enter the level you want to upgrade to");
    int numUnits = getInteger("Enter number of units to upgrade");
    Territory origin_terr = model.getMap().getTerritoryByName(origin);
    Order o = new UpgradeOrder(origin_terr, null, numUnits, client.getPlayer(), cur_level, new_level);
    try {
      int tech = processor.processUpgrade(model.getMap(), o, client.getPlayer());
      processor.addOrder(new UpgradeOrder(origin_terr, null, numUnits, client.getBasicPlayer(),cur_level, new_level));
      // model.gameLogText.set("Valid upgrade order with cost:");
      gameLogText.appendText("Valid upgrade order with tech cost: " + tech + "\n");
      //gameLogShowMap();
      gameLogshowPlayer();
    } catch (Exception exp) {
      // model.gameLogText.set(exp.getMessage());
      gameLogText.appendText(exp.getMessage());
    }
  }

  @FXML
  public void onFinishTurn(ActionEvent e) {
    switch (state) {
      case 1:
        switchState(2);
        break;
      case 2:
        switchState(3);
        break;
      case 3:
        switchState(4);
        break;
      case 4:
        switchState(5);
        break;
      case 5:// send to server, recv results,
        finishOneTurn();
    }
  }

  private void finishOneTurn() {
    try {
      gameLogText.appendText("Wait for other players...\n");
      this.client.getCommunicator().sendObject(processor.getVerifiedOrders());
      processor.clearVerified();
      String result = this.client.getCommunicator().recvString();
      gameLogText.appendText(result);
      int aggPts = this.client.handleAggPts();
      gameLogText.appendText("Player's current Aggression Points: "+aggPts+"\n");
      //TODO: replace it with more meaningful information
      String gameresult = this.client.getCommunicator().recvString();
      if (gameresult.equals("Gameover")) {// one global winner occurs, exit game.
        this.model.setMap(client.recvMap());
        String wininfo = this.client.displayWinInfo(this.model.getMap());
        ConfirmBox.display(gameresult, wininfo, "Good game well played", "bad game");
        Stage window = (Stage) gameLogText.getScene().getWindow();
        window.close();
      } else {
        startAnotherTurn();
      }
    } catch (Exception exp) {
      System.out.println(exp.getMessage());
    }
  }

  private void startAnotherTurn() {
    try {
      this.model.setMap(client.recvMap());
      if (this.client.getPlayer().hasLost(this.model.getMap())) {// has lost
        boolean disconnect = ConfirmBox.display("You lose!", "Do you want to disconnect?", "Disconnect", "Watch Game");
        if (disconnect) {
          Stage window = (Stage) gameLogText.getScene().getWindow();
          window.close();
        } else {
          processor.clearVerified();
          switchState(5);
        }
      } else {// not lost yet
        this.client.getPlayer().updateResourcesInTurn(this.model.getMap());
        resetOwnedTerrs();
        //gameLogShowMap();
        gameLogshowPlayer();
        switchState(1);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void printSource(ActionEvent e) {
    System.out.println(e.getSource());
  }

  private void resetOwnedTerrs() {
    terrs.clear();
    for (Territory t : model.getMap().groupTerritoriesByPlayer().get(client.getPlayer())) {
      terrs.add(t.getName());
    }
  }

  private void setAllTerrs() {
    terrs.clear();
    for (String t : model.getMap().getMap().keySet()) {
      terrs.add(t);
    }
  }

  public void gameLogshowPlayer() {
    StringBuilder sb = new StringBuilder();
    Player p = this.client.getPlayer();
    sb.append("Player: " + p.getName() + "\n");
    sb.append("Maximum Technology Level: " + p.getMaxTechLevel() + "\n");
    sb.append("Total Food Resources: " + p.getFoodAmt() + "\n");
    sb.append("Total Technology Resources: " + p.getTechAmt() + "\n");
    sb.append("Current Aggression Points: "+ p.getAggPt()+ "\n");
    gameLogText.appendText(sb.toString());
  }

  private void gameLogShowMap() {
    MapTextView view = new MapTextView(this.model.getMap());
    gameLogText.appendText(view.displayMap());
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

  /**
   * Gets a choice from the user among specified options
   * 
   * @param prompt  is the prompt to display to the user
   * @param options is the list of options to diplay to the user
   *
   * @return the option the user chooses
   */
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

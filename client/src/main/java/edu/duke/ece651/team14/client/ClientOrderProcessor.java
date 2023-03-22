package edu.duke.ece651.team14.client;

import java.io.IOException;
import java.util.ArrayList;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.Territory;

public abstract class ClientOrderProcessor {
  protected final ClientPlayer clientPlayer;
  protected Map map;
  protected ArrayList<Order> verifiedOrders;

  public ClientOrderProcessor(ClientPlayer clientPlayer, Map map) {
    this.clientPlayer = clientPlayer;
    this.map = map;
    this.verifiedOrders = new ArrayList<>();
  }

  protected String promptForDoneEnteringOrders(String type) throws IOException {
    clientPlayer.sendMsg("Type 'D' if you're done entering " + type
        + " orders for this turn. Type anything else to continue entering orders");
    return clientPlayer.getInput().toLowerCase();
  }

  protected Territory promptForTerr(String type) throws IOException {
    while (true) {
      clientPlayer.sendMsg(type + " territory for order:");
      String terrStr = clientPlayer.getInput().toLowerCase();
      try {
        Territory terr = map.getTerritoryByName(terrStr);
        clientPlayer.sendMsg("Setting " + type + ": " + terrStr);
        return terr;
      } catch (IllegalArgumentException e) {
        clientPlayer.sendMsg(e.getMessage());
      }
    }
  }

  protected int promptForNumUnits() throws IOException {
    while (true) {
      clientPlayer.sendMsg("Number of units to send:");
      String numStr = clientPlayer.getInput();
      int numUnits = 0;
      try {
        numUnits = Integer.parseInt(numStr);
      } catch (NumberFormatException e) {
        clientPlayer.sendMsg("Invalid number");
        continue;
      }
      if (numUnits < 1) {
        clientPlayer.sendMsg("Must send at least one unit");
        continue;
      }
      clientPlayer.sendMsg("Sending " + numUnits + " units");
      return numUnits;
    }
  }

  protected abstract Order processOrder() throws IOException;

  public ArrayList<Order> processAllOrdersForOneTurn(String type) throws IOException {
    clientPlayer.sendMsg("Time to place all your " + type + " orders for this turn");
    while (true) {
      Order verifiedOrder = processOrder();
      // verifiedOrder will be null is user indicates they are done entering orders
      // for this turn
      if (verifiedOrder == null) {
        break;
      }
      verifiedOrders.add(verifiedOrder);
    }
    return verifiedOrders;
  }

}

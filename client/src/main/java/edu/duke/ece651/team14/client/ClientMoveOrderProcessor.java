package edu.duke.ece651.team14.client;

import java.io.IOException;
import java.util.ArrayList;

import edu.duke.ece651.team14.shared.DestinationOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.MoveOrderCostRuleChecker;
import edu.duke.ece651.team14.shared.MoveOrderPathExistsRuleChecker;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.OriginDestNotSameTerrRuleChecker;
import edu.duke.ece651.team14.shared.OriginOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.Unit;
import edu.duke.ece651.team14.shared.UnitMover;

public class ClientMoveOrderProcessor extends ClientOrderProcessor {
  private final OrderRuleChecker checker;

  public ClientMoveOrderProcessor(ClientPlayer clientPlayer, Map map) {
    super(clientPlayer, map);
    this.checker = new OriginDestNotSameTerrRuleChecker(new OriginOwnershipRuleChecker(
        new DestinationOwnershipRuleChecker(new MoveOrderPathExistsRuleChecker(
            new NumberOfUnitsRuleChecker(new MoveOrderCostRuleChecker(null))))));
  }

  protected Order processOrder() throws IOException {
    while (true) {
      String done = promptForDoneEnteringOrders("move");
      if (done.equals("d")) {
        return null;
      }
      Territory origin = promptForTerr("Origin");
      Territory dest = promptForTerr("Destination");
      int numUnits = promptForNumUnits();
      Order order = new MoveOrder(origin, dest, numUnits, clientPlayer.myPlayer);
      // checkResult will be null if all the rule checkers pass
      String checkResult = checker.checkOrder(map, order);
      if (checkResult != null) {
        clientPlayer.sendMsg(checkResult);
        continue;
      }
      MoveOrder moveOrder = (MoveOrder) order;
      int cost = moveOrder.calculateCost();
      clientPlayer.myPlayer.useFoodResources(cost);
      ArrayList<Unit> unitsToSend = moveOrder.getUnitsPicked();
      // UnitMover.moveUnits(origin, dest, numUnits, "basic");
      // instead create UnitMover.sendUnitArray which takes in unitsToSend
      UnitMover.sendUnitArray(origin, dest, unitsToSend);
      // send information from client player to server
      return order;
    }
  }
}

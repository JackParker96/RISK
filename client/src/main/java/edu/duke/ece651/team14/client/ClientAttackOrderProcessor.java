package edu.duke.ece651.team14.client;

import java.io.IOException;
import java.util.ArrayList;

import edu.duke.ece651.team14.shared.AdjacentTerritoryRuleChecker;
import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.AttackOrderCostRuleChecker;
import edu.duke.ece651.team14.shared.DestinationNotOwnedRuleChecker;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.OriginOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.Unit;
import edu.duke.ece651.team14.shared.UnitMover;

public class ClientAttackOrderProcessor extends ClientOrderProcessor {
  private final OrderRuleChecker checker;

  public ClientAttackOrderProcessor(ClientPlayer clientPlayer, Map map) {
    super(clientPlayer, map);
    this.checker = new OriginOwnershipRuleChecker(new DestinationNotOwnedRuleChecker(
        new NumberOfUnitsRuleChecker(new AdjacentTerritoryRuleChecker(
            new AttackOrderCostRuleChecker(null)))));
  }

  protected Order processOrder() throws IOException {
    while (true) {
      String done = promptForDoneEnteringOrders("attack");
      if (done.equals("d")) {
        return null;
      }
      Territory origin = promptForTerr("Origin");
      Territory dest = promptForTerr("Destination");
      int numUnits = promptForNumUnits();
      Order order = new AttackOrder(origin, dest, numUnits, clientPlayer.myPlayer);
      // checkResult will be null if all the rule checkers pass
      String checkResult = checker.checkOrder(map, order);
      if (checkResult != null) {
        clientPlayer.sendMsg(checkResult);
        continue;
      }
      AttackOrder attackOrder = (AttackOrder) order;
      int cost = attackOrder.calculateCost();
      clientPlayer.myPlayer.useFoodResources(cost);
      ArrayList<Unit> unitsToSend = attackOrder.getUnitsPicked();
      // UnitMover.moveUnits(origin, dest, numUnits, "basic");
      // instead create UnitMover.sendUnitArray which takes in unitsToSend
      UnitMover.sendUnitArray(origin, dest, unitsToSend);

      // send information from client player to server
      return order;
    }
  }
}

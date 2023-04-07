package edu.duke.ece651.team14.client;

import java.io.IOException;

import edu.duke.ece651.team14.shared.AdjacentTerritoryRuleChecker;
import edu.duke.ece651.team14.shared.AttackOrder;
import edu.duke.ece651.team14.shared.DestinationNotOwnedRuleChecker;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.OriginOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UnitMover;

public class ClientAttackOrderProcessor extends ClientOrderProcessor {
  private final OrderRuleChecker checker;

  public ClientAttackOrderProcessor(ClientPlayer clientPlayer, Map map) {
    super(clientPlayer, map);
    this.checker = new OriginOwnershipRuleChecker(
        new DestinationNotOwnedRuleChecker(new NumberOfUnitsRuleChecker(new AdjacentTerritoryRuleChecker(null))));
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
      UnitMover.moveUnits(origin, dest, numUnits, "basic");
      int distance = origin.getDistToAdjacentTerr(dest);
      int price = clientPlayer.myPlayer.getMaxTechLevel() * numUnits;
      clientPlayer.myPlayer.useFoodResources(3 * distance * price);
      // send information from client player to server
      return order;
    }
  }
}

package edu.duke.ece651.team14.client;

import java.io.IOException;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MaxTechLevelRuleChecker;
import edu.duke.ece651.team14.shared.NumUnitsInTechLevelRuleChecker;
import edu.duke.ece651.team14.shared.NumberOfUnitsRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.OriginOwnershipRuleChecker;
import edu.duke.ece651.team14.shared.TechLevelRuleChecker;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UnitMover;
import edu.duke.ece651.team14.shared.UpgradeOrder;
import edu.duke.ece651.team14.shared.UpgradeOrderCostRuleChecker;

public class ClientUpgradeOrderProcessor extends ClientOrderProcessor {
  private final OrderRuleChecker checker;

  public ClientUpgradeOrderProcessor(ClientPlayer clientPlayer, Map map) {
    super(clientPlayer, map);
    this.checker = new OriginOwnershipRuleChecker(new NumberOfUnitsRuleChecker(
        new TechLevelRuleChecker(
            new NumUnitsInTechLevelRuleChecker(
                new UpgradeOrderCostRuleChecker(null)))));
  }

  protected Order processOrder() throws IOException {
    while (true) {
      String done = promptForDoneEnteringOrders("upgrade");
      if (done.equals("d")) {
        return null;
      }
      Territory terr = promptForTerr("Origin");
      int numUnits = promptForNumUnits();
      int currTechLevel = 0;
      int newTechLevel = 1;
      Order order = new UpgradeOrder(terr, null, numUnits, clientPlayer.myPlayer,
          currTechLevel, newTechLevel);
      // checkResult will be null if all the rule checkers pass
      String checkResult = checker.checkOrder(map, order);
      if (checkResult != null) {
        clientPlayer.sendMsg(checkResult);
        continue;
      }
      // send information from client player to server
      return order;
    }
  }
}

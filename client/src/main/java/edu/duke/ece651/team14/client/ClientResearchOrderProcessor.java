package edu.duke.ece651.team14.client;

import java.io.IOException;
import java.util.ArrayList;

import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MaxTechLevelRuleChecker;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.OrderRuleChecker;
import edu.duke.ece651.team14.shared.ResearchOrder;
import edu.duke.ece651.team14.shared.ResearchOrderCostRuleChecker;

public class ClientResearchOrderProcessor extends ClientOrderProcessor {
  private final OrderRuleChecker checker;

  public ClientResearchOrderProcessor(ClientPlayer clientPlayer, Map map) {
    super(clientPlayer, map);
    this.checker = new MaxTechLevelRuleChecker(new ResearchOrderCostRuleChecker(null));
  }

  protected ResearchOrder processOrder() throws IOException {
    ResearchOrder order = new ResearchOrder(clientPlayer.myPlayer);
    String checkResult = checker.checkOrder(map, order);
    if (checkResult != null) {
      clientPlayer.sendMsg(checkResult);
      return null;
    }
    int cost = order.calculateCost();
    clientPlayer.myPlayer.useTechResources(cost);
    return order;
  }

  @Override
  public ArrayList<Order> processAllOrdersForOneTurn(String type) throws IOException {
    ResearchOrder verifiedOrder = processOrder();
    if (verifiedOrder == null) {
      return null;
    }
    verifiedOrders.add(verifiedOrder);
    return verifiedOrders;
  }
}



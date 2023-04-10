package edu.duke.ece651.team14.shared;

public class ResearchOrder extends Order {

  public ResearchOrder(Player player) {
    super(new BasicTerritory("test"), new BasicTerritory("test"), 0, player, "research");
  }

  public int calculateCost() {
    int [] costs = new int [] {20, 40, 80, 160, 320};
    int currLevel = getPlayer().getMaxTechLevel();
    int cost = costs[currLevel - 1];
    return cost;
  }

}

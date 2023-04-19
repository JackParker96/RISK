package edu.duke.ece651.team14.shared;

public class AllianceOrder extends Order {
  private Player newAlly;

  public AllianceOrder(Player player, Player newAlly) {
    super(new BasicTerritory("test"), new BasicTerritory("test"), 0, player, null, "alliance");
    this.newAlly = newAlly;
  }

  public Player getNewAlly() {
    return newAlly;
  }
}

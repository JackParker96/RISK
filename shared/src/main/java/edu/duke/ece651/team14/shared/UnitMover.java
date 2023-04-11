package edu.duke.ece651.team14.shared;

import java.util.ArrayList;
import java.util.Iterator;

public class UnitMover {
  /**
   * Moves up to numUnits units of type unitType from Territory origin to
   * Territory destination
   *
   * @param origin      is the origin Territory
   * @param destination is the destination Territory
   * @param numUnits    is the number of units to move
   * @param unitType    is the unit type
   */
  public static void moveUnits(Territory origin, Territory destination, int numUnits, String unitType) {
    ArrayList<Unit> originUnits = origin.getUnits();
    Iterator<Unit> iterator = originUnits.iterator();
    while (iterator.hasNext() && numUnits > 0) {
      Unit u = iterator.next();
      if (u.getType().equals(unitType)) {
        iterator.remove();
        destination.addUnits(u);
        numUnits--;
      }
    }
  }

  public static void sendUnitArray(Territory origin, Territory destination, ArrayList<Unit> unitsToSend) {
    Iterator<Unit> iterator = unitsToSend.iterator();
    int count = unitsToSend.size();
    while (iterator.hasNext() && count > 0) {
      Unit u = iterator.next();
      if (origin.removeUnit(u)) {
        destination.addUnits(u);
        count--;
      }
    }
  }
}

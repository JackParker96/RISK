package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/**
 * Factory to make different types of units.
 */

public class UnitsFactory implements AbstractUnitsFactory {

  /**
   * Make units of the specified type
   */
  @Override
  public ArrayList<Unit> makeUnits(int num_units, String type) {
    ArrayList<Unit> units = new ArrayList<>();
    if (type.equals("basic")) {
      units = makeBasicUnits(num_units);
    }else{
      System.out.println(type);
      throw new IllegalArgumentException("Unit type not supported");
    }
    return units;
  }

  /** 
   * make BasicUnit
   * @param num_units
   * @return
   */
  protected ArrayList<Unit> makeBasicUnits(int num_units) {
    ArrayList<Unit> units = new ArrayList<>();
    for (int i = 0; i < num_units; i++) {
      units.add(new BasicUnit());
    }
    return units;
  }
}

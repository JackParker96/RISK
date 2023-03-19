package edu.duke.ece651.team14.shared;

import java.util.ArrayList;

/** 
 * Factory to make different kinds of Unit list.
*/
public interface AbstractUnitsFactory {
  public ArrayList<Unit> makeUnits(int num_units,String type);
}

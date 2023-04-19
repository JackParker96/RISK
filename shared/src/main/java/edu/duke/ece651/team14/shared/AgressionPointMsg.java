package edu.duke.ece651.team14.shared;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.util.Pair;

public class AgressionPointMsg implements Serializable {
  private ArrayList<Pair<String,Integer>> points;

  public AgressionPointMsg(){
    this.points = new ArrayList<>();
  }

  /** 
   * 
   * @param playerName: e.g., yellow, green, blue, red
   * @param point: 1(add point) or 0(reset)
   */
  public void addPlayerMsg(String playerName,int point){
    this.points.add(new Pair<String,Integer>(playerName, point));
  }

  public int getPoint(String playerName){
    for(int i=0;i<points.size();i++){
      if(playerName.equals(points.get(i).getKey())){
        return points.get(i).getValue();
      }
    }
    throw new IllegalArgumentException("No such player name in points message");
  }
}

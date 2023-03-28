package edu.duke.ece651.team14.server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.team14.shared.Account;
import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.Player;

public class Game {
  private int game_id;//unqiue id to identify game
  private int num_players;
  private Map map;
  private HashMap<Account,Player> PlayerMapping;
  private HashMap<Player, Communicator> playerCommunicators;
  private HashMap<Player, String> connectionStatus;

  //________________________________Static variables_____________________//
  private static final HashMap<Integer, String> colorMap = new HashMap<>();
  private static int id_count;
  static {
    colorMap.put(0, "red");
    colorMap.put(1, "green");
    colorMap.put(2, "blue");
    colorMap.put(3, "yellow");
    id_count = 0;
  }
  //_____________________________________________________________________//

  public Game(int num_players){
    id_count++;
    this.game_id = id_count;
    this.num_players = num_players;
    this.playerCommunicators = new HashMap<>();
    this.connectionStatus = new HashMap<>();
    MapFactory f = new MapFactory();
    this.map = f.makeMap("Earth", new ArrayList<Player>(this.playerCommunicators.keySet())); // actual map
    //this.map = f.makeMap("test", new ArrayList<Player>(this.playerCommunicators.keySet())); // test map
  }

  
  public void addPlayer(){
  }
}

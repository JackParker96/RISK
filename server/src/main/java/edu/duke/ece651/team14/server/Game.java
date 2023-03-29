package edu.duke.ece651.team14.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.duke.ece651.team14.shared.Account;
import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Map;
import javafx.util.Pair;

public class Game {
  private int game_id;// unqiue id to identify game
  private int num_players;
  private int cur_players;
  private Map map;
  private ClientSockets sockets;
  private HashMap<Account, PlayerInfo> pinfos;
  private BlockingQueue<Pair<Account,Socket>> waitList;
  
  // ________________________________Static variables_____________________//
  private static final HashMap<Integer, String> colorMap = new HashMap<>();
  private static int id_count;
  static {
    colorMap.put(0, "red");
    colorMap.put(1, "green");
    colorMap.put(2, "blue");
    colorMap.put(3, "yellow");
    id_count = 0;
  }
  // _____________________________________________________________________//

  public Game(int num_players, ClientSockets sockets, Socket thisSocket, Account account) throws IOException {
    id_count++;
    this.game_id = id_count;
    this.num_players = num_players;
    this.cur_players = 1;
    // MapFactory f = new MapFactory();
    // this.map = f.makeMap("Earth", new
    // ArrayList<Player>(this.playerCommunicators.keySet())); // actual map
    // this.map = f.makeMap("test", new
    // ArrayList<Player>(this.playerCommunicators.keySet())); // test map
    this.sockets = sockets;
    this.pinfos = new HashMap<>();
    String colorName = colorMap.get(cur_players - 1);
    pinfos.put(account, new PlayerInfo(thisSocket, new BasicPlayer(new Color(colorName), colorName)));
    this.waitList = new LinkedBlockingQueue<>();
  }

  protected void acceptPlayersPhase() throws IOException,InterruptedException{
    System.out.println("Wait for players to join...");
    while (cur_players < num_players) {
      Pair<Account,Socket> new_join = waitList.take();//block until a new player join.
      addPlayer(new_join.getKey(), new_join.getValue());
      System.out.println("New player joins game "+game_id);
    }
    System.out.println("All player joined, start game id: "+game_id);
  }
  
  protected void addPlayer(Account a, Socket s) throws IOException{
    String colorName = colorMap.get(cur_players);
    pinfos.put(a, new PlayerInfo(s, new BasicPlayer(new Color(colorName), colorName)));
    cur_players++;
  }

  public void joinGame(Account a,Socket s) throws InterruptedException{
    this.waitList.put(new Pair<Account,Socket>(a, s));
  }

  //for testing
  protected HashMap<Account,PlayerInfo> getPlayerInfos(){
    return pinfos;
  }
}

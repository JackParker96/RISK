package edu.duke.ece651.team14.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.duke.ece651.team14.shared.Account;
import edu.duke.ece651.team14.shared.BasicPlayer;
import edu.duke.ece651.team14.shared.Color;
import edu.duke.ece651.team14.shared.Communicator;
import edu.duke.ece651.team14.shared.DiceResolver;
import edu.duke.ece651.team14.shared.GUIOrderprocessor;
import edu.duke.ece651.team14.shared.Map;
import edu.duke.ece651.team14.shared.MapFactory;
import edu.duke.ece651.team14.shared.MoveOrder;
import edu.duke.ece651.team14.shared.Order;
import edu.duke.ece651.team14.shared.Player;
import edu.duke.ece651.team14.shared.ServerOrderProcessor;
import edu.duke.ece651.team14.shared.Territory;
import edu.duke.ece651.team14.shared.UnitPlacementOrder;
import edu.duke.ece651.team14.shared.UpgradeOrder;
import javafx.util.Pair;

public class Game {
  private int game_id;// unqiue id to identify game
  private int num_players;
  private int cur_players;
  private Map map;
  private ClientSockets sockets;
  private HashMap<Account, PlayerInfo> pinfos;
  private BlockingQueue<Pair<Account, Socket>> waitList;

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
    this.sockets = sockets;
    this.pinfos = new HashMap<>();
    String colorName = colorMap.get(cur_players - 1);
    pinfos.put(account, new PlayerInfo(thisSocket, new BasicPlayer(new Color(colorName), colorName)));
    this.waitList = new LinkedBlockingQueue<>();
  }

  public void runGame() throws InterruptedException, IOException, ClassNotFoundException {
    acceptPlayersPhase();
    initializeGamePhase();
    playGamePhase();
    gameOverPhase();
  }

  protected void acceptPlayersPhase() throws IOException, InterruptedException {
    System.out.println("Wait for players to join...");
    while (cur_players < num_players) {
      addPlayer();
      System.out.println("New player joins game " + game_id);
    }
    System.out.println("All player joined, start game id: " + game_id);
  }

  protected PlayerInfo addPlayer() throws IOException, InterruptedException {
    Pair<Account, Socket> new_join = waitList.take();// block until a new player join.
    Account a = new_join.getKey();
    Socket s = new_join.getValue();
    PlayerInfo new_pinfo = null;
    if (!pinfos.containsKey(a)) {// new player joins
      String colorName = colorMap.get(cur_players);
      new_pinfo = new PlayerInfo(s, new BasicPlayer(new Color(colorName), colorName));
      pinfos.put(a, new_pinfo);
      cur_players++;
    } else {
      new_pinfo = new PlayerInfo(s, pinfos.get(a).getPlayer());
      pinfos.put(a, new_pinfo);
    }
    return new_pinfo;
  }

  public void joinGame(Account a, Socket s) throws InterruptedException {
    this.waitList.put(new Pair<Account, Socket>(a, s));
  }

  // for testing
  protected HashMap<Account, PlayerInfo> getPlayerInfos() {
    return pinfos;
  }

  protected void initializeGamePhase() throws IOException, ClassNotFoundException {
    MapFactory f = new MapFactory();
    this.map = f.makeMap("Earth", getAllPlayers()); // actual map
    // this.map = f.makeMap("test", getAllPlayers());
    // test map
    sendGameInitInfo();
    receivePlacementOrders();
  }

  protected void sendGameInitInfo() throws IOException, ClassNotFoundException {
    for (PlayerInfo pinfo : pinfos.values()) {
      Communicator c = pinfo.getCommunicator();
      Player p = pinfo.getPlayer();
      c.sendObject(p);
      c.sendObject(map);
    }
  }

  protected ArrayList<Player> getAllPlayers() {
    ArrayList<Player> players = new ArrayList<>();
    for (PlayerInfo pinfo : pinfos.values()) {
      players.add(pinfo.getPlayer());
    }
    return players;
  }

  protected void receivePlacementOrders() throws IOException, ClassNotFoundException {
    for (PlayerInfo pinfo : pinfos.values()) {
      Communicator c = pinfo.getCommunicator();
      UnitPlacementOrder upo = c.recvUnitPOrder();
      System.out.println("recv unit placement request from " + pinfo.getPlayer());
      map.handleUnitPlacementOrder(upo);
    }
  }

  /**
   * Add reconnected player to this game.
   */
  protected void addReconnectedPlayer() throws IOException,InterruptedException{
    while(!waitList.isEmpty()){
      PlayerInfo joined_pinfo = addPlayer();
      joined_pinfo.getCommunicator().sendObject(joined_pinfo.getPlayer());
    }
  }
  
  // * Runs through all stages of the game
  // *
  // * @throws IOException, ClassNotFoundException
  //
  protected void playGamePhase() throws IOException, ClassNotFoundException,InterruptedException{
    while (true) {
      addReconnectedPlayer();
      executeOneTurn();
      Player winner = this.map.getWinner();
      if (winner != null) {
        sendInfo("Gameover");
        sendInfo(this.map);
        break;
      } else {
        sendInfo("Continue");
      }
    }
  }

  protected void executeOneTurn()
      throws IOException, ClassNotFoundException {
    sendInfo(this.map);
    HashMap<String, ArrayList<Order>> orders = receiveAllOrders();
    //resolve upgrade
    ServerOrderProcessor processor = new ServerOrderProcessor(this.map);
    serverResolveUpgrade(processor, orders.get("upgrade"));
    serverResolveMove(processor, orders.get("move"));
    // resolve attack
    ServerAttackOrderResolver sar = new ServerAttackOrderResolver(map, new DiceResolver());
    String results = sar.resolveAllAttackOrders(orders.get("attack"));// the attcking information
    sendInfo(results);
    this.map.allAddOneUnit();
  }


  /** 
   * Make use of the GUIprocessor, it makes new order and resolve locally. To get rid of pointer problems.
   * @param processor
   * @param orders
   */
  private void serverResolveUpgrade(ServerOrderProcessor processor,ArrayList<Order> orders){
    try{
      for(Order o:orders){
        UpgradeOrder uo = (UpgradeOrder)o;
        Order serverOrder = new UpgradeOrder(map.getTerritoryByName(uo.getOrigin().getName()), null, uo.getNumUnits(), uo.getPlayer(),uo.getCurrTechLevel(), uo.getNewTechLevel());
        processor.processUpgrade(map, serverOrder, uo.getPlayer());
      }
    }
    catch(Exception e){
      System.out.println(e.getMessage());
    }
  }


  private void serverResolveMove(ServerOrderProcessor processor, ArrayList<Order> moveOrders){
    try{
      for(Order o:moveOrders){
        MoveOrder mo = (MoveOrder)o;
        Territory origin = map.getTerritoryByName(mo.getOrigin().getName());
        Territory dest = map.getTerritoryByName(mo.getDestination().getName());
        Order serverOrder = new MoveOrder(origin, dest, mo.getNumUnits(), mo.getPlayer());
        processor.processMove(map, serverOrder, mo.getPlayer());
      }
    }
    catch(Exception e){
      System.out.println();
    }
  }

  /**
   * Send the information to all players
   * 
   * @param results
   * @throws IOException
   */
  protected void sendInfo(Object infos) {
    for (PlayerInfo pinfo : pinfos.values()) {
      if (!pinfo.isConnected()) {// if not connected
        continue;
      }
      Communicator c = pinfo.getCommunicator();
      try {
        c.sendObject(infos);
      } catch (IOException ioe) {
        Socket player_socket = pinfo.getThisSocket();
        this.sockets.removeSocket(player_socket);
        pinfo.setDisconnected();
      }
    }
  }

  /**
   * Receives orders from every player for a given turn
   *
   *
   * @throw IOException, ClassNotFoundException
   *
   * @return a HashMap of the orders
   */
  protected HashMap<String, ArrayList<Order>> receiveAllOrders() throws IOException, ClassNotFoundException {
    ArrayList<Order> allOrders = new ArrayList<>();
    for (PlayerInfo pinfo : pinfos.values()) {
      if (!pinfo.isConnected()) {
        continue;
      }
      Communicator c = pinfo.getCommunicator();
      try {
        ArrayList<Order> orders = c.recvOrders();
        allOrders.addAll(orders);
      } catch (IOException ioe) {
        Socket player_socket = pinfo.getThisSocket();
        this.sockets.removeSocket(player_socket);
        pinfo.setDisconnected();
      }
    }
    if (allDisconnected()) {// all players disconnect
      throw new IllegalStateException("All players disconnect, terminate game.");
    }
    System.out.println("receiving all orders for one turn");
    return sortOrders(allOrders);
  }

  protected boolean allDisconnected() {
    boolean alldisc = true;
    for (PlayerInfo pinfo : pinfos.values()) {
      if (pinfo.isConnected()) {
        alldisc &= false;
      }
    }
    return alldisc;
  }

  /**
   * Takes in an ArrayList of orders and return a HashMap of the orders arranged
   * by order type
   *
   * @param orders is the list of Orders
   *
   * @return the HashMap of Orders arranged by order type
   */
  protected HashMap<String, ArrayList<Order>> sortOrders(ArrayList<Order> orders) {
    HashMap<String, ArrayList<Order>> sortedOrders = new HashMap<>();
    sortedOrders.put("upgrade", new ArrayList<Order>());
    sortedOrders.put("move", new ArrayList<Order>());
    sortedOrders.put("attack", new ArrayList<Order>());
    sortedOrders.put("research", new ArrayList<Order>());
    for (Order o : orders) {
      sortedOrders.get(o.getOrderType()).add(o);
    }
    return sortedOrders;
  }

  /**
   * Game over phase, wait until all players disconnect.
   */
  protected void gameOverPhase() throws IOException {
    boolean loop = true;
    while (loop) {
      loop = false;
      for (PlayerInfo pinfo : pinfos.values()) {
        if (!pinfo.isConnected()) {
          continue;
        }
        if (pinfo.getThisSocket().getInputStream().read() == -1) {
          this.sockets.removeSocket(pinfo.getThisSocket());
          pinfo.setDisconnected();
        } else {
          loop = true;
        }
      }
    }
    System.out.println("Shut down this game..");
  }

  /**
   * Return true if this game can no longer accept new players
   */
  public boolean gameStarted() {
    return cur_players == num_players;
  }

  /**
   * Check whether the account is belong to this game, and can be reconnected.
   * 
   * @param acc
   * @return true if belongs to this game
   */
  public boolean canReJoin(Account acc) {
    boolean belongToGame = belongToGame(acc);
    return belongToGame && !pinfos.get(acc).isConnected();
  }

  /**
   * 
   * @param acc
   * @return True if belong to this game.
   */
  public boolean belongToGame(Account acc) {
    return this.pinfos.keySet().contains(acc);
  }

  /**
   * Get Game id.
   * 
   * @return game id
   */
  public int getID() {
    return game_id;
  }

  /**
   * Release all the resources (sockets) of this game, in case of error throw out
   * of game.
   * e.g. player disconnects in initialization game phase.
   */
  public void releaseResources() {
    for (PlayerInfo pinfo : pinfos.values()) {
      Socket s = pinfo.getThisSocket();
      sockets.removeSocket(s);
    }
  }
}

package edu.duke.ece651.team14.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.duke.ece651.team14.shared.Account;
import edu.duke.ece651.team14.shared.Communicator;

public class RequestHandler implements Runnable {
  private final ClientSockets sockets;
  private final Socket clientSocket;
  private final List<Game> allGames;
  private final HashSet<Account> accounts;
  private Communicator c;
  private Account acc;

  public RequestHandler(ClientSockets sockets, Socket clientSocket, List<Game> allGames,
      HashSet<Account> accounts) {
    this.sockets = sockets;
    this.clientSocket = clientSocket;
    this.allGames = allGames;
    this.accounts = accounts;
  }

  @Override
  public void run() {
    try {
      c = new Communicator(clientSocket.getOutputStream(), clientSocket.getInputStream());
      login();
      c.sendObject(getJoinableGame(acc));
      c.sendObject(getRejoinableGame(acc));
      String choice = c.recvString();
      joinGame(choice);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void login() throws IOException, ClassNotFoundException {
    while (true) {
      acc = c.recvAccount();
      if (!accounts.contains(acc)) {
        c.sendObject("Wrong Account");
      }else{
        c.sendObject("Login Success");
        break;
      }
    }
  }

  /**
   * Join the game based on the client's choice,
   * if choice is 0, create a new game, if other, join the game.
   * 
   * @param choice
   */
  protected void joinGame(String choice) throws IOException, InterruptedException {
    if (choice.charAt(0) == '0') {// start a new game
      int num_players = choice.charAt(1) - '0';
      Game newGame = new Game(num_players, sockets, clientSocket, acc);
      allGames.add(newGame);
      System.out.println("A new game with id:" + newGame.getID() + " created");
      try {
        newGame.runGame();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      } finally {
        newGame.releaseResources();
        allGames.remove(newGame);
      }
    } else {
      int game_id = Integer.parseInt(choice);
      Game g = getGameByID(game_id);
      if (g == null) {
        throw new IllegalArgumentException("No game with id:" + game_id + " available");
      }
      g.joinGame(acc, clientSocket);
    }
  }

  protected ArrayList<Integer> getJoinableGame(Account acc) {
    ArrayList<Integer> ans = new ArrayList<>();
    for (Game g : allGames) {
      if (!g.gameStarted() && !g.belongToGame(acc)) {// game not started and not joined
        ans.add(g.getID());
      }
    }
    return ans;
  }

  protected ArrayList<Integer> getRejoinableGame(Account acc) {
    ArrayList<Integer> ans = new ArrayList<>();
    for (Game g : allGames) {
      if (g.canReJoin(acc)) {
        ans.add(g.getID());
      }
    }
    return ans;
  }

  protected Game getGameByID(int id) {
    for (Game g : this.allGames) {
      if (g.getID() == id) {
        return g;
      }
    }
    return null;
  }

}

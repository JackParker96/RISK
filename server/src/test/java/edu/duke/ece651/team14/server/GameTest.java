package edu.duke.ece651.team14.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.team14.shared.Account;

public class GameTest {

  private class JoinGameTest implements Runnable {
    Game g;// game to test
    ArrayList<Account> accounts;
    Socket s;

    public JoinGameTest(Game g) throws IOException {
      this.g = g;
      this.accounts = new ArrayList<>();
      accounts.add(new Account("1", "123"));
      accounts.add(new Account("2", "123"));
      accounts.add(new Account("3", "123"));
      s = mock(Socket.class);
      when(s.getInputStream()).thenReturn(new FileInputStream("test.txt"));
      when(s.getOutputStream()).thenReturn(new FileOutputStream("test.txt"));
    }

    private void join(int index) throws InterruptedException {
      g.joinGame(accounts.get(index), s);
    }

    @Override
    public void run() {
      System.out.print("thread running...");
      try {
        for (int i = 0; i < 3; i++) {
          join(i);
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void test_joingame() throws IOException, InterruptedException {
    ClientSockets sockets = new ClientSockets();
    Socket s = mock(Socket.class);
    when(s.getInputStream()).thenReturn(new FileInputStream("test.txt"));
    when(s.getOutputStream()).thenReturn(new FileOutputStream("test.txt"));
    Game g = new Game(4, sockets, s, new Account("0", "123"));
    JoinGameTest test = new JoinGameTest(g);
    Thread join_thread = new Thread(test, "join_thread");
    join_thread.start();
    g.acceptPlayersPhase();
    HashMap<Account,PlayerInfo> pinfos = g.getPlayerInfos();
    assertEquals(4, pinfos.size());
    assertTrue(g.gameStarted());
    for(Account acc:test.accounts){
      assertFalse(g.canReJoin(acc));
      assertTrue(g.belongToGame(acc));
    }
    
  }

}

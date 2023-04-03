package edu.duke.ece651.team14.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
  Account a1;
  Account a2;
  Account a3;

  @BeforeEach
  public void start(){
    a1 = new Account("Drew","123");
    a2 = new Account("Draw","123");
    a3 = new Account("Draw","123");
  }
  
  @Test
  public void test_account() { 
    assertEquals(a1.getPassword(),a2.getPassword());
    assertNotEquals(a1.getUserName(), a2.getUserName());
    assertEquals(a2, a3);
    assertNotEquals(a1, a2);
    assertNotEquals(a1, a2.getUserName());
  }

  @Test
  public void test_hashes(){
    HashSet<Account> accounts = new HashSet<>();
    accounts.add(a1);
    accounts.add(a2);
    accounts.add(a3);
    assertEquals(2, accounts.size());
    assertTrue(accounts.contains(new Account("Drew", "123")));
  }

}

package edu.duke.ece651.team14.shared;

import java.io.Serializable;

/** 
 * Represents a user account.
 */
public class Account implements Serializable{
  private final String userName;
  private final String password;

  public Account(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  @Override
  public int hashCode() {
    return (userName+password).hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Account otherAccount = (Account) other;
      return userName.equals(otherAccount.userName) && password.equals(otherAccount.password);
    }
    return false;
  }

  public String getPassword() {
    return password;
  }

  public String getUserName() {
    return userName;
  }

}

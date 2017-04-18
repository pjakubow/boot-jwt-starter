package net.simplewebapps.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AccountCredentials implements Serializable {

  private String username;
  private String password;

  @JsonCreator
  public AccountCredentials(@JsonProperty("username") String username, @JsonProperty("password") String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}

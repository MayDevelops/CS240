package com.MayDevelops.familymapclient.Models;

public class LoginModel {

  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;

  private String ipAddress;
  private String port;


  public LoginModel() {
    this.username = null;
    this.password = null;
    this.email = null;
    this.firstName = null;
    this.lastName = null;
    this.gender = null;
    this.ipAddress = null;
    this.port = null;
  }

  public String getUsername() {
    return this.username;
  }
  public String getPassword() {
    return this.password;
  }
  public String getEmail() {
    return this.email;
  }
  public String getFirstName() {
    return this.firstName;
  }
  public String getLastName() { return this.lastName; }
  public String getGender() { return this.gender; }
  public String getIpAddress() { return this.ipAddress; }
  public String getPort() { return this.port; }

  public void setUsername(String setUsername) {
    this.username = setUsername;
  }
  public void setPassword(String setPassword) {
    this.password = setPassword;
  }
  public void setEmail(String setEmail) {
    this.email = setEmail;
  }
  public void setFirstName(String setFirstName) {
    this.firstName = setFirstName;
  }
  public void setLastName(String setLastName) {
    this.lastName = setLastName;
  }
  public void setGender(String setGender) {
    this.gender = setGender;
  }
  public void setIpAddress(String setIPAddress) { this.ipAddress = setIPAddress; }
  public void setPort(String setPort) { this.port = setPort; }
}

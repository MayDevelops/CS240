package com.MayDevelops.familymapclient;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.EventResult;
import Results.LoginResult;
import Results.PersonResult;
import Results.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


public class ProxyServerTest {

  @Before
  public void setUp() {
    ProxyServer server = ProxyServer.initialize();
    RegisterRequest registerRequest = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
    RegisterResult registerResult = server.Register("127.0.0.1", "8080", registerRequest);
  }

  @Test
  public void LoginPassTest() {
    ProxyServer server = ProxyServer.initialize();
    LoginRequest loginRequest = new LoginRequest("sheila", "parker");
    LoginResult loginResult = server.Login("127.0.0.1", "8080", loginRequest);
    assertNotNull(loginResult.getUsername());
    assertEquals("sheila", loginResult.getUsername());
  }

  @Test
  public void LoginFailTest() {
    ProxyServer server = ProxyServer.initialize();
    LoginRequest loginRequest = new LoginRequest("sheilaa", "parkerr");
    LoginResult loginResult = server.Login("127.0.0.1", "8080", loginRequest);
    assertNotNull(loginResult.getMessage());
    assertNull(loginResult.getUsername());
  }

  @Test
  public void RegisterPassTest() {
    ProxyServer server = ProxyServer.initialize();
    RegisterRequest registerRequest = new RegisterRequest("New", "User", "x", "NewUserFirst", "NewUserLast", "f");
    RegisterResult registerResult = server.Register("127.0.0.1", "8080", registerRequest);
    assertNotNull(registerResult.getPersonID());
    assertNotNull(registerResult.getAuthtoken());
  }

  @Test
  public void RegisterFailTest() {
    ProxyServer server = ProxyServer.initialize();
    RegisterRequest registerRequest = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
    RegisterResult registerResult = server.Register("127.0.0.1", "8080", registerRequest);
    assertNotNull(registerResult.getMessage());
    assertNull(registerResult.getAuthtoken());
  }

  @Test
  public void GetPersonsPassTest() {
    ProxyServer server = ProxyServer.initialize();
    LoginRequest loginRequest = new LoginRequest("sheila", "parker");
    LoginResult loginResult = server.Login("127.0.0.1", "8080", loginRequest);
    String goodToken = loginResult.getAuthtoken();
    PersonResult allPersonResults = server.Persons("127.0.0.1", "8080", goodToken);
    assertNotNull(allPersonResults.getData());
  }

  @Test
  public void GetPersonsFailTest() {
    ProxyServer server = ProxyServer.initialize();
    String badToken = "Bacon";
    PersonResult personResult = server.Persons("127.0.0.1", "8080", badToken);
    assertNotNull(personResult.getMessage());
    assertNull(personResult.getAssociatedUsername());
  }

  @Test
  public void GetEventsPassTest() {
    ProxyServer server = ProxyServer.initialize();
    LoginRequest loginRequest = new LoginRequest("sheila", "parker");
    LoginResult loginResult = server.Login("127.0.0.1", "8080", loginRequest);
    String authTokenTest = loginResult.getAuthtoken();
    EventResult allEventsResults = server.Events("127.0.0.1", "8080", authTokenTest);
    assertNotNull(allEventsResults.getData());
  }

  @Test
  public void GetEventsFailTest() {
    ProxyServer server = ProxyServer.initialize();
    String bigBadBacon = "BigBadBacon";
    EventResult allEventsResults = server.Events("127.0.0.1", "8080", bigBadBacon);
    assertNotNull(allEventsResults.getMessage());
  }

}

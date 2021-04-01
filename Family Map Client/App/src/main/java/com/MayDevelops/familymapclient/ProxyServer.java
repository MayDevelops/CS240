package com.MayDevelops.familymapclient;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.EventResult;
import Results.LoginResult;
import Results.PersonResult;
import Results.RegisterResult;

public class ProxyServer {

  private static ProxyServer server;

  public static ProxyServer initialize() {
    if (server == null) {
      server = new ProxyServer();
    }
    return server;
  }


  public LoginResult Login(String ipAddress, String portNumber, LoginRequest loginRequest) {
    Gson gson = new Gson();
    try {
      URL url = new URL("http://" + ipAddress + ":" + portNumber + "/user/login");

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("POST");
      http.setDoOutput(true);
      http.addRequestProperty("Accept", "application/json");
      http.connect();

      String requestBody = gson.toJson(loginRequest);
      OutputStream httpOutputStream = http.getOutputStream();
      ToString(requestBody, httpOutputStream);

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

        InputStream httpInputStream = http.getInputStream();
        String response = StringConversion(httpInputStream);
        LoginResult loginResult = gson.fromJson(response, LoginResult.class);
        return loginResult;
      } else {
        return new LoginResult(http.getResponseMessage(), false);
      }
    } catch (IOException e) {
      e.printStackTrace();
      return new LoginResult("Error with Login", false);
    }
  }

  public RegisterResult Register(String ipAddress, String portNumber, RegisterRequest registerRequest) {
    Gson gson = new Gson();
    try {
      URL url = new URL("http://" + ipAddress + ":" + portNumber + "/user/register");

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("POST");
      http.setDoOutput(true);
      http.addRequestProperty("Accept", "application/json");
      http.connect();

      String requestBody = gson.toJson(registerRequest);

      OutputStream httpOutputStream = http.getOutputStream();
      ToString(requestBody, httpOutputStream);

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream httpInputStream = http.getInputStream();
        String response = StringConversion(httpInputStream);
        RegisterResult registerResult = gson.fromJson(response, RegisterResult.class);
        return registerResult;
      } else {
        return new RegisterResult(http.getResponseMessage(), false);
      }
    } catch (IOException e) {
      e.printStackTrace();
      return new RegisterResult("Error with Registering User", false);
    }
  }

  public PersonResult Persons(String serverHost, String serverPort, String authToken) {
    Gson gson = new Gson();
    try {
      URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("GET");
      http.setDoOutput(false);
      http.addRequestProperty("Authorization", authToken);
      http.addRequestProperty("Accept", "application/json");
      http.connect();

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

        InputStream httpInputStream = http.getInputStream();
        String resposne = StringConversion(httpInputStream);
        PersonResult personResult = gson.fromJson(resposne, PersonResult.class);
        return personResult;
      } else {
        return new PersonResult(http.getResponseMessage(), false);
      }
    } catch (IOException e) {
      e.printStackTrace();
      return new PersonResult("Error with retrieving all people", false);
    }
  }


  public EventResult Events(String serverHost, String serverPort, String authToken) {
    Gson gson = new Gson();
    try {
      URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("GET");
      http.setDoOutput(false);
      http.addRequestProperty("Authorization", authToken);
      http.addRequestProperty("Accept", "application/json");
      http.connect();

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

        InputStream httpInputStream = http.getInputStream();
        String response = StringConversion(httpInputStream);
        EventResult eventResult = gson.fromJson(response, EventResult.class);
        return eventResult;
      } else {
        return new EventResult(http.getResponseMessage(), false);
      }
    } catch (IOException e) {
      e.printStackTrace();
      return new EventResult("Error with retrieving all events", false);
    }
  }

  private static String StringConversion(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    InputStreamReader isr = new InputStreamReader(is);
    char[] buf = new char[1024];
    int length;
    while ((length = isr.read(buf)) > 0) {
      sb.append(buf, 0, length);
    }
    return sb.toString();
  }

  private static void ToString(String sentResponse, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(sentResponse);
    s.flush();
  }
}

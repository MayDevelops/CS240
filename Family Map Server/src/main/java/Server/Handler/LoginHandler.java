package Server.Handler;

import DataAccessObjects.DataAccessException;
import Service.Requests.LoginRequest;
import Service.Results.LoginResult;
import Service.Services.LoginService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginHandler implements HttpHandler {
  Gson gson = new Gson();
  LoginService service = new LoginService();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    boolean success = false;

    try {

      if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
        String requestBody = StringConversion(exchange.getRequestBody());
        LoginRequest loginRequest = gson.fromJson(requestBody, LoginRequest.class);
        LoginResult loginResult = service.login(loginRequest);
        String response = gson.toJson(loginResult);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream responseBody = exchange.getResponseBody();
        ToString(response, responseBody);
        responseBody.close();
        success = true;
      }

      if (! success) {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
      }
    } catch (IOException | DataAccessException | SQLException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
    }
  }

  private void ToString(String in, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(in);
    s.flush();
  }

  public String StringConversion(InputStream is) {
    Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }
}

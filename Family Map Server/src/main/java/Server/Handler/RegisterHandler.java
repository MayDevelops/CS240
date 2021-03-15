package Server.Handler;

import Service.Requests.RegisterRequest;
import Service.Results.RegisterResult;
import Service.Services.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RegisterHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    boolean success = false;
    RegisterService service = new RegisterService();
    try {
      Gson gson = new Gson();
      if(exchange.getRequestMethod().equalsIgnoreCase("post")) {

        String requestBody = exchange.getRequestBody().toString();

        RegisterRequest registerRequest = gson.fromJson(requestBody, RegisterRequest.class);
        JsonObject json = gson.fromJson(requestBody, JsonObject.class);
        RegisterResult registerResult = service.register(registerRequest);

        String response = gson.toJson(registerResult);
        String responseBody = exchange.getResponseBody().toString();

      }

    } catch (JsonSyntaxException e) {
      e.printStackTrace();
    }

  }


}

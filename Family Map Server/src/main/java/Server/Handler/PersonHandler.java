package Server.Handler;

import Service.Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class PersonHandler implements HttpHandler {

  PersonService personService = new PersonService();
  Gson gson = new Gson();
  String response = "Error: Request denied.";
  @Override
  public void handle(HttpExchange exchange) throws IOException {

    if(exchange.getRequestMethod().equalsIgnoreCase("get")) {
      if(exchange.getRequestHeaders().containsKey("Authorization")) {
        String authToken = exchange.getRequestHeaders().getFirst("Authorization");
        String uri = exchange.getRequestURI().toString();

        if(uri.equals("/person")) {

        }
      }
    }
  }
}

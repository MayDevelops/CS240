package Server.Handler;

import DataAccessObjects.DataAccessException;
import Service.Results.ClearResult;
import Service.Services.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    boolean success = false;

    try {
      System.out.println("Clear Handler has been called.\n");
      if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
        System.out.println("Calling clearService...\n");
        ClearService clearService = new ClearService();
        System.out.println("clearService is finished, creating result...\n");
        ClearResult clearResult = clearService.ClearDatabase();

        String response = "{ \"message\": \"" + clearResult.getMessage() + "\"}";
        System.out.println("Response created: \"" + response + "\" \n");


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
    } catch (IOException | DataAccessException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }

  private void ToString(String in, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(in);
    s.flush();
  }

}

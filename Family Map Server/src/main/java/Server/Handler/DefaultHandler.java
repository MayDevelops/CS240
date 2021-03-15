package Server.Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultHandler implements HttpHandler {
  /**
   * Handle the given request and generate an appropriate response.
   * See {@link HttpExchange} for a description of the steps
   * involved in handling an exchange.
   *
   * @param exchange the exchange containing the request from the
   *                 client and used to send the response
   * @throws NullPointerException if exchange is <code>null</code>
   */
  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try {
      String path;
      String uri = exchange.getRequestURI().toString();

      if (uri.equals("/")) {
        path = "web/index.html";
      } else {
        path = "web/" + uri;
      }

      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
      OutputStream response = exchange.getResponseBody();
      Path filePath = FileSystems.getDefault().getPath(path);
      Files.copy(filePath, response);
      response.close();

    } catch (IOException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }
}

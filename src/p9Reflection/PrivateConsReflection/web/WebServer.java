package p9Reflection.PrivateConsReflection.web;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpServer;
import p9Reflection.PrivateConsReflection.ServerConfig;

public class WebServer {
  public void startServer() throws IOException {
    HttpServer httpServer = HttpServer.create(ServerConfig.getServerConfig().getServerAddress(), 0);
    httpServer.createContext("/greeting").setHandler(exchange -> {
      String response = ServerConfig.getServerConfig().getGreetingMessage();
      exchange.sendResponseHeaders(200, response.length());
      OutputStream responseBody = exchange.getResponseBody();
      responseBody.write(response.getBytes());
      responseBody.flush();
      responseBody.close();
    });

    System.out.println("Server started on address %s:%d".formatted(
        ServerConfig.getServerConfig().getServerAddress().getHostName(),
        ServerConfig.getServerConfig().getServerAddress().getPort()));

    httpServer.start();
  }
}

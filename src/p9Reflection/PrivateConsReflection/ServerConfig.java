package p9Reflection.PrivateConsReflection;

import java.net.InetSocketAddress;

public class ServerConfig {
  private static ServerConfig serverConfig;
  private final InetSocketAddress serverAddress;
  private final String greetingMessage;

  private ServerConfig(int port, String greetingMessage) {
    this.greetingMessage = greetingMessage;
    this.serverAddress = new InetSocketAddress("localhost", port);
    if (serverConfig == null) {
      serverConfig = this;
    }
  }

  public static ServerConfig getServerConfig() {
    return serverConfig;
  }

  public InetSocketAddress getServerAddress() {
    return serverAddress;
  }

  public String getGreetingMessage() {
    return greetingMessage;
  }
}

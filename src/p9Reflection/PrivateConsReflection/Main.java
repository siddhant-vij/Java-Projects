package p9Reflection.PrivateConsReflection;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import p9Reflection.PrivateConsReflection.web.WebServer;

public class Main {
  public static void initConfig() throws NoSuchMethodException, SecurityException, InstantiationException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Constructor<ServerConfig> constructor = ServerConfig.class.getDeclaredConstructor(int.class, String.class);
    constructor.setAccessible(true);
    constructor.newInstance(8080, "Good Day!");
  }

  public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
    initConfig();
    WebServer server = new WebServer();
    server.startServer();
    // ServerConfig serverConfig = ServerConfig.getServerConfig(); // Singleton
    // ServerConfig serverConfig2 = new ServerConfig(8080, "Good Day!"); // Not allowed
  }
}

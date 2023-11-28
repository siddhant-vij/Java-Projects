package p9Reflection.PolymorphismDB;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p9Reflection.PolymorphismDB.database.DatabaseClient;
import p9Reflection.PolymorphismDB.http.HttpClient;
import p9Reflection.PolymorphismDB.logging.FileLogger;
import p9Reflection.PolymorphismDB.udp.UdpClient;

public class MainApp {
  public static void main(String[] args) throws Throwable {
    DatabaseClient databaseClient = new DatabaseClient();
    HttpClient httpClient1 = new HttpClient("123.456.789.0");
    HttpClient httpClient2 = new HttpClient("11.33.55.0");
    FileLogger fileLogger = new FileLogger();
    UdpClient udpClient = new UdpClient();

    String requestBody = "Some Test Data";
    Map<Object, Method> requestExecutors = groupExecutors(
        Arrays.asList(databaseClient, httpClient1, httpClient2, fileLogger, udpClient),
        Arrays.asList(new Class<?>[] { String.class }));
    executeAll(requestExecutors, requestBody);
  }

  public static void executeAll(Map<Object, Method> executors, String requestBody)
      throws Throwable {
    for (Object executor : executors.keySet()) {
      try {
        Method method = executors.get(executor);
        Boolean result;
        result = (Boolean) method.invoke(executor, requestBody);
        if (result != null && result.equals(Boolean.FALSE)) {
          System.out.println("Request processed unsuccessfully. Aborting!");
          return;
        }
      } catch (InvocationTargetException e) {
        throw e.getTargetException();
      }
    }
  }

  public static Map<Object, Method> groupExecutors(List<Object> executors, List<Class<?>> types) {
    Map<Object, Method> result = new HashMap<>();
    for (Object executor : executors) {
      Method[] allMethods = executor.getClass().getDeclaredMethods();
      for (Method method : allMethods) {
        if (Arrays.asList(method.getParameterTypes()).equals(types)) {
          result.put(executor, method);
        }
      }
    }
    return result;
  }
}

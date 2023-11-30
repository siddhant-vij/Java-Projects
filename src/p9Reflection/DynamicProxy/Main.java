package p9Reflection.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import p9Reflection.DynamicProxy.external.DBReader;
import p9Reflection.DynamicProxy.external.HttpClient;
import p9Reflection.DynamicProxy.external.impl.DBReaderImpl;
import p9Reflection.DynamicProxy.external.impl.HttpClientImpl;

public class Main {
  public static void main(String[] args) {
    // initWithoutDynamicProxy();
    initWithDynamicProxy();
  }

  public static void initWithDynamicProxy() {
    HttpClient httpClient = createDynamicProxy(new HttpClientImpl());
    useHttpClient(httpClient);

    System.out.println("---------------------------------------");

    DBReader dbReader = createDynamicProxy(new DBReaderImpl());
    try {
      useDBReader(dbReader);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static <T> T createDynamicProxy(Object originalObject) {
    @SuppressWarnings("unchecked")
    T t = (T) Proxy.newProxyInstance(
        originalObject.getClass().getClassLoader(),
        originalObject.getClass().getInterfaces(),
        new TimeMeasuringHandler(originalObject));
    return t;
  }

  public static class TimeMeasuringHandler implements InvocationHandler {
    private final Object originalObject;

    public TimeMeasuringHandler(Object originalObject) {
      this.originalObject = originalObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Object result;
      System.out.println("Method " + method.getName() + " started.");
      long start = System.currentTimeMillis();
      result = method.invoke(originalObject, args);
      long end = System.currentTimeMillis();
      System.out.println("\nMethod " + method.getName() + " took " + (end - start) + " ms.\n");
      return result;
    }

  }

  public static void initWithoutDynamicProxy() {
    HttpClient httpClient = new HttpClientImpl();
    useHttpClient(httpClient);

    System.out.println("---------------------------------------");

    DBReader dbReader = new DBReaderImpl();
    try {
      useDBReader(dbReader);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void useHttpClient(HttpClient httpClient) {
    httpClient.initialize();
    String response = httpClient.sendRequest("Some request");
    System.out.println("Response: " + response + "\n");
  }

  public static void useDBReader(DBReader dbReader) throws InterruptedException {
    int rowsInTable1 = dbReader.countRowsInTable("table1");
    System.out.println("Count of rows: " + rowsInTable1);
    String[] rows = dbReader.readRows("SELECT * FROM table1");
    System.out.println("Rows: " + String.join(", ", rows) + "\n");
  }
}

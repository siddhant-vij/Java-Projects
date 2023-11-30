package p0Projects.CachingProxy;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

import p0Projects.CachingProxy.external.DBReader;
import p0Projects.CachingProxy.external.impl.DBReaderImpl;

public class MainApp {
  public static void main(String[] args) {
    initWithoutDynamicProxy();
    System.out.println("---------------------------------------");
    initWithDynamicProxy();
  }

  private static void initWithDynamicProxy() {
    DBReader dbReader = createDynamicProxy(new DBReaderImpl());
    try {
      useDBReader(dbReader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static <T> T createDynamicProxy(Object originalObject) {
    @SuppressWarnings("unchecked")
    T t = (T) Proxy.newProxyInstance(
        originalObject.getClass().getClassLoader(),
        originalObject.getClass().getInterfaces(),
        new CachingInvocationHandler(originalObject));
    return t;
  }

  private static void initWithoutDynamicProxy() {
    DBReader dbReader = new DBReaderImpl();
    try {
      useDBReader(dbReader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void useDBReader(DBReader dbReader) throws IOException {
    dbReader.connectToDatabase();

    String customerIdByName = dbReader.readCustomerIdByName("John", "Doe");
    System.out.println("Customer id by name: " + customerIdByName + "\n");

    int rowsInCustomerTable = dbReader.countRowsInCustomersTable();
    System.out.println("Rows in customer table: " + rowsInCustomerTable + "\n");

    dbReader.addCustomer(customerIdByName, "JohnUpdated", "Doe");

    Date customerBirthday = dbReader.readCustomerBirthday(customerIdByName);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
    System.out.println("Customer birthday: " + dateFormat.format(customerBirthday) + "\n");
  }
}

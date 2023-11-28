package p9Reflection.PolymorphismDB.logging;

public class FileLogger {
  public void sendRequest(String data) {
    System.out.println(String.format("Data: %s was logged to file system", data));
  }
}

package p9Reflection.PolymorphismDB.database;

public class DatabaseClient {
  public boolean storeData(String data) {
    System.out.println(String.format("Storing data: %s in Database", data));
    return true;
  }
}

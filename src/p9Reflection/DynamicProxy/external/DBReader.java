package p9Reflection.DynamicProxy.external;

public interface DBReader {
  int countRowsInTable(String tableName) throws InterruptedException;
  String[] readRows(String sqlQuery) throws InterruptedException;
}

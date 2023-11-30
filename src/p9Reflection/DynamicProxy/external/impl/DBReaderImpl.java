package p9Reflection.DynamicProxy.external.impl;

import p9Reflection.DynamicProxy.external.DBReader;

public class DBReaderImpl implements DBReader {
  private final static int MAX_ROWS = 5;

  @Override
  public int countRowsInTable(String tableName) throws InterruptedException {
    System.out.println("Counting rows in table: " + tableName);
    Thread.sleep(1000);
    return MAX_ROWS;
  }

  @Override
  public String[] readRows(String sqlQuery) throws InterruptedException {
    System.out.println("Executing query: " + sqlQuery);
    Thread.sleep(1500);
    String[] rows = new String[MAX_ROWS];
    for (int i = 0; i < MAX_ROWS; i++) {
      rows[i] = "row" + (i + 1);
    }
    return rows;
  }
}

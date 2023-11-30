package p0Projects.CachingProxy.external.impl;

import java.io.IOException;
import java.util.Date;

import p0Projects.CachingProxy.external.DBReader;

public class DBReaderImpl implements DBReader {
  private static final int MAX_ROWS = 10;
  private static final int MAX_SLEEP_TIME = 2500;

  @Override
  public void connectToDatabase() {
    System.out.println("Connecting to database...");
    try {
      Thread.sleep((int) (Math.random() * MAX_SLEEP_TIME));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Connected to database\n");
  }

  @Override
  public String readCustomerIdByName(String firstName, String lastName) throws IOException {
    System.out.println("Reading customer id by name: " + firstName + " " + lastName);
    try {
      Thread.sleep((int) (Math.random() * MAX_SLEEP_TIME));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "customerId123";
  }

  @Override
  public int countRowsInCustomersTable() {
    System.out.println("Counting rows in customers table...");
    try {
      Thread.sleep((int) (Math.random() * MAX_SLEEP_TIME));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return MAX_ROWS;
  }

  @Override
  public void addCustomer(String id, String firstName, String lastName) throws IOException {
    System.out.println("Adding customer: " + id + " " + firstName + " " + lastName);
    try {
      Thread.sleep((int) (Math.random() * MAX_SLEEP_TIME));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Added customer: " + id + " " + firstName + " " + lastName + "\n");
  }

  @Override
  public Date readCustomerBirthday(String id) {
    System.out.println("Reading customer birthday: " + id);
    try {
      Thread.sleep((int) (Math.random() * MAX_SLEEP_TIME));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return new Date();
  }
}

package p0Projects.CachingProxy.external;

import java.io.IOException;
import java.util.Date;

import p0Projects.CachingProxy.Cacheable;

public interface DBReader {
  void connectToDatabase();

  @Cacheable
  String readCustomerIdByName(String firstName, String lastName) throws IOException;

  int countRowsInCustomersTable();

  void addCustomer(String id, String firstName, String lastName) throws IOException;

  @Cacheable
  Date readCustomerBirthday(String id);
}

package p6Threads.CompetableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

class DelayCls {
  public static void delay(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class DisplayUserAccountDetails {
  public String getUserID() {
    System.out.println("Getting user details...");
    DelayCls.delay(2);
    return "testUser001";
  }

  private int getIndexForFirstCapitalLetter(String name) {
    for (int i = 0; i < name.length(); i++) {
      if (Character.isUpperCase(name.charAt(i))) {
        return i;
      }
    }
    return -1;
  }

  public String getUserDetailsFromDB(String userID) {
    System.out.println("Getting user details from DB...");
    DelayCls.delay(3);
    String name = userID.substring(0, userID.length() - 3);
    int index = getIndexForFirstCapitalLetter(name);
    name = name.substring(0, index) + " " + name.substring(index);
    name = name.substring(0, 1).toUpperCase() + name.substring(1);
    return name;
  }

  public void displayUserDetails(String name) {
    System.out.println("Displaying user details: " + name);
  }
}

public class ChainingExample {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    /*
     * Synchronous method calls
     */
    DisplayUserAccountDetails displayUserAccountDetails = new DisplayUserAccountDetails();
    String userID = displayUserAccountDetails.getUserID();
    String name = displayUserAccountDetails.getUserDetailsFromDB(userID);
    displayUserAccountDetails.displayUserDetails(name);

    System.out.println("------------------------");

    /*
     * Asynchronous method calls
     */
    CompletableFuture<Void> userFuture = CompletableFuture.supplyAsync(
        () -> displayUserAccountDetails.getUserID()).thenCompose(
            id -> CompletableFuture.supplyAsync(
                () -> displayUserAccountDetails.getUserDetailsFromDB(id)))
        .thenAccept(
            str -> displayUserAccountDetails.displayUserDetails(str));
    userFuture.get();
  }
}

package p6Threads.CompetableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AnyAllOfExample {
  public static void delay(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static CompletableFuture<Integer> future1() {
    return CompletableFuture.supplyAsync(
        () -> {
          System.out.println(" future1 - " + Thread.currentThread().getName());
          delay(1);
          return Integer.valueOf(12);
        });
  }

  public static CompletableFuture<Double> future2() {
    return CompletableFuture.supplyAsync(
        () -> {
          System.out.println(" future2 - " + Thread.currentThread().getName());
          delay(2);
          return 1.23d;
        });
  }

  public static CompletableFuture<String> future3() {
    return CompletableFuture.supplyAsync(
        () -> {
          System.out.println(" future3 - " + Thread.currentThread().getName());
          delay(3);
          return "String";
        });
  }

  public static void main(String[] args) {
    /*
     * AllOf Example - wait for all three futures to complete
     * Time taken = max. of all three futures - 3
     */
    CompletableFuture<Integer> future1_1 = future1();
    CompletableFuture<Double> future2_1 = future2();
    CompletableFuture<String> future3_1 = future3();
    long startTime = System.currentTimeMillis();
    CompletableFuture.allOf(future1_1, future2_1, future3_1).join();
    long endTime = System.currentTimeMillis();
    System.out.println("Time Taken - " + (endTime - startTime) / 1000);

    System.out.println("--------------------------");

    /*
     * AnyOf Example - wait for any of the three futures to complete
     * Time taken = min. of all three futures - 1
     */
    CompletableFuture<Integer> future1_2 = future1();
    CompletableFuture<Double> future2_2 = future2();
    CompletableFuture<String> future3_2 = future3();
    startTime = System.currentTimeMillis();
    CompletableFuture.anyOf(future1_2, future2_2, future3_2).join();
    endTime = System.currentTimeMillis();
    System.out.println("Time Taken - " + (endTime - startTime) / 1000);
  }
}

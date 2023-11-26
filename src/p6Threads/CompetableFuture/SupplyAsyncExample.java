package p6Threads.CompetableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class SupplyAsyncExample {
  public static void delay(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Supplier<String> supplier = () -> {
      delay(2);
      System.out.println("Printing from " + Thread.currentThread().getName());
      return "Hello from " + Thread.currentThread().getName();
    };
    /*
     * SupplyAsync using ForkJoinPool.commonPool - no parameter
     * Main thread: main
     * Printing from ForkJoinPool.commonPool-worker-1
     * Hello from ForkJoinPool.commonPool-worker-1
     */
    CompletableFuture<String> future1 = CompletableFuture.supplyAsync(supplier);
    System.out.println("Main thread: " + Thread.currentThread().getName());
    String result = future1.join();
    System.out.println(result);

    /*
     * SupplyAsync using ExecutorService - with parameter
     * Main thread: main
     * Printing from pool-1-thread-1
     * Hello from pool-1-thread-1
     */
    ExecutorService es = Executors.newCachedThreadPool();
    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(supplier, es);
    System.out.println("Main thread: " + Thread.currentThread().getName());
    result = future2.join();
    System.out.println(result);
    es.shutdown();
  }
}

package p6Threads.CompetableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunAsyncExample {
  public static void delay(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Runnable runnable = () -> {
      delay(2);
      System.out.println("Hello from " + Thread.currentThread().getName());
    };
    /*
     * RunAsync using ForkJoinPool.commonPool - no parameter
     * Main thread: main
     * Hello from ForkJoinPool.commonPool-worker-1
     */
    CompletableFuture<Void> future1 = CompletableFuture.runAsync(runnable);
    System.out.println("Main thread: " + Thread.currentThread().getName());
    future1.join();

    /*
     * RunAsync using ExecutorService - with parameter
     * Main thread: main
     * Hello from pool-1-thread-1
     */
    ExecutorService es = Executors.newCachedThreadPool();
    CompletableFuture<Void> future2 = CompletableFuture.runAsync(runnable, es);
    System.out.println("Main thread: " + Thread.currentThread().getName());
    future2.join();
    es.shutdown();
  }
}

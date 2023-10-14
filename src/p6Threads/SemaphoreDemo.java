/*
 * Semaphore is used to control access to a shared resource
 * that uses a counter variable to keep track of the number of
 * permits available.
 * 
 * - acquire(): if a permit is available, acquire it
 * - release(): release a permit & increment the counter
 * 
 * Semaphore just keeps a count of the number of permits available
 *   new semaphore(int permits, boolean fairness)
 */

package p6Threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

enum Downloader {
  INSTANCE;

  private Semaphore semaphore = new Semaphore(3, true);

  public void download() {
    try {
      semaphore.acquire();
      downloadDataSimulation();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
    }
  }

  private void downloadDataSimulation() {
    try {
      System.out.println("Downloading data... Thread: " + Thread.currentThread().getName());
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

public class SemaphoreDemo {
  public static void main(String[] args) {
    int numThreads = 12;
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    for (int i = 0; i < numThreads; i++) {
      executor.submit(() -> Downloader.INSTANCE.download());
    }
    executor.shutdown();
  }
}

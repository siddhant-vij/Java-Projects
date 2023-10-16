package p6Threads;

import java.util.concurrent.CountDownLatch;

class MyThreadCDL extends Thread {
  CountDownLatch latch;

  MyThreadCDL(CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      System.out.println(i);
      latch.countDown();
    }
  }
}

public class CountDownLatchDemo {
  public static void main(String[] args) {
    CountDownLatch latch = new CountDownLatch(5);
    System.out.println("Starting");
    new Thread(new MyThreadCDL(latch)).start();
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Completed");
  }
}

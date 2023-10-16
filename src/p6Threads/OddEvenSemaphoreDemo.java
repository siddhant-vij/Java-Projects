package p6Threads;

import java.util.concurrent.Semaphore;

class Shared {
  static int counter = 0;
}

class OddEven extends Thread {
  String name;
  Semaphore odd;
  Semaphore even;
  int n;

  public OddEven(Semaphore odd, Semaphore even, String name, int n) {
    this.odd = odd;
    this.even = even;
    this.name = name;
    this.n = n;
  }

  public void odd() throws InterruptedException {
    odd.acquire();
    Shared.counter++;
    System.out.println("Odd counter = " + Shared.counter);
    Thread.sleep(10);
    even.release();
  }

  public void even() throws InterruptedException {
    even.acquire();
    Shared.counter++;
    System.out.println("Even counter = " + Shared.counter);
    Thread.sleep(10);
    odd.release();
  }

  @Override
  public void run() {
    try {
      for (int i = 0; 2 * i < n; i++) {
        if (name.equals("Odd")) {
          odd();
        } else {
          even();
        }
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupted");
    }
  }
}

public class OddEvenSemaphoreDemo {
  public static void main(String[] args) {
    Semaphore odd = new Semaphore(1);
    Semaphore even = new Semaphore(0); // 0 - assures that odd runs first
    int n = 50;
    Thread oddThread = new OddEven(odd, even, "Odd", n);
    Thread evenThread = new OddEven(odd, even, "Even", n);
    oddThread.start();
    evenThread.start();
  }
}

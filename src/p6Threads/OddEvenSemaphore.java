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

  public void odd() {
    try {
      odd.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Shared.counter++;
    System.out.println(name + " counter = " + Shared.counter);
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    even.release();
  }

  public void even() {
    try {
      even.acquire();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Shared.counter++;
    System.out.println(name + " counter = " + Shared.counter);
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    odd.release();
  }

  @Override
  public void run() {
    for (int i = 0; 2 * i < n; i++) {
      if (name.equals("Odd")) {
        odd();
      } else {
        even();
      }
    }
  }
}

public class OddEvenSemaphore {
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

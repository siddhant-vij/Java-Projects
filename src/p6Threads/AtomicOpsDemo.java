package p6Threads;

import java.util.concurrent.atomic.AtomicInteger;

class SharedAtomic {
  static AtomicInteger counter = new AtomicInteger(0);
}

class AddOne implements Runnable {
  @Override
  public void run() {
    for (int i = 0; i < 10000; i++) {
      SharedAtomic.counter.getAndIncrement();
    }
  }
}

class MinusOne implements Runnable {
  @Override
  public void run() {
    for (int i = 0; i < 10000; i++) {
      SharedAtomic.counter.getAndDecrement();
    }
  }
}

public class AtomicOpsDemo {
  public static void main(String[] args) throws InterruptedException {
    System.out.println(SharedAtomic.counter.get());
    Thread add = new Thread(new AddOne());
    add.start();
    add.join();
    System.out.println(SharedAtomic.counter.get());
    Thread minus = new Thread(new MinusOne());
    minus.start();
    minus.join();
    System.out.println(SharedAtomic.counter.get());
  }
}

package p6Threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class MyThreadCB implements Runnable {
  String name;
  CyclicBarrier cb;

  MyThreadCB(String name, CyclicBarrier cb) {
    this.name = name;
    this.cb = cb;
  }

  @Override
  public void run() {
    System.out.println(name + " started");
    try {
      cb.await();
    } catch (BrokenBarrierException | InterruptedException e) {
      System.out.println(e);
    }
  }
}

class NextRunningThread implements Runnable {
  @Override
  public void run() {
    System.out.println("Barrier reached...");
  }
}

public class CyclicBarrierDemo {
  public static void main(String[] args) {
    CyclicBarrier cb = new CyclicBarrier(2, new NextRunningThread());
    System.out.println("Starting...");
    new Thread(new MyThreadCB("Thread-1", cb)).start();
    new Thread(new MyThreadCB("Thread-2", cb)).start();
    new Thread(new MyThreadCB("Thread-3", cb)).start();
    new Thread(new MyThreadCB("Thread-4", cb)).start();
    new Thread(new MyThreadCB("Thread-5", cb)).start();
    new Thread(new MyThreadCB("Thread-6", cb)).start();
  }
}

// Producer Consumer Problem

package p6Threads;

class ThreadTest extends Thread {
  int total = 0;

  @Override
  public void run() {
    synchronized (this) {
      for (int i = 1; i <= 100; i++) {
        total += i;
      }
      notify();
    }
  }
}

public class InterThreadCommunication {
  public static void main(String[] args) throws InterruptedException {
    ThreadTest t1 = new ThreadTest();
    t1.start();
    // Thread.sleep(0,1); ----> Not recommended as time depends on CPU

    // t1.join(); ----> Not recommended as we're not sure if there is any other code
    // after actual updation

    synchronized (t1) {
      t1.wait();
      System.out.println(t1.total);
    }
  }
}

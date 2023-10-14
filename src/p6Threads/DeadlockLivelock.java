package p6Threads;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Deadlock {

  Lock lock1 = new ReentrantLock();
  Lock lock2 = new ReentrantLock();

  public void worker1() {
    lock1.lock();
    System.out.println("worker1 acquired lock1");

    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    lock2.lock();
    System.out.println("worker1 acquired lock2");

    lock1.unlock();
    lock2.unlock();
  }

  public void worker2() {
    lock2.lock();
    System.out.println("worker2 acquired lock2");

    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    lock1.lock();
    System.out.println("worker2 acquired lock1");

    lock2.unlock();
    lock1.unlock();
  }
}

class Livelock {

  Lock lock1 = new ReentrantLock();
  Lock lock2 = new ReentrantLock();

  public void worker1() {
    while (true) {
      try {
        lock1.tryLock(50, TimeUnit.MILLISECONDS);
        System.out.println("worker1 acquired lock1...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println("worker1 trying to acquire lock2");
      if (lock2.tryLock()) {
        System.out.println("worker1 acquired lock2");
        lock2.unlock();
      } else {
        System.out.println("worker1 failed to acquire lock2");
        continue;
      }
      break;
    }
    lock1.unlock();
    lock2.unlock();
  }

  public void worker2() {
    while (true) {
      try {
        lock2.tryLock(50, TimeUnit.MILLISECONDS);
        System.out.println("worker2 acquired lock2...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.println("worker2 trying to acquire lock1");
      if (lock1.tryLock()) {
        System.out.println("worker2 acquired lock1");
        lock1.unlock();
      } else {
        System.out.println("worker2 failed to acquire lock1");
        continue;
      }
      break;
    }
    lock1.unlock();
    lock2.unlock();
  }
}

public class DeadlockLivelock {
  public static void choice(String choice) {
    switch (choice) {
      case "deadlock":
        Deadlock d = new Deadlock();
        new Thread(d::worker1, "d-worker1").start();
        new Thread(d::worker2, "d-worker2").start();
        break;
      case "livelock":
        Livelock l = new Livelock();
        new Thread(l::worker1, "l-worker1").start();
        new Thread(l::worker2, "l-worker2").start();
        break;
      default:
        System.out.println("Invalid choice");
    }
  }

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Enter choice (deadlock/livelock): ");
      String choice = sc.next();
      choice(choice);
    }
  }
}

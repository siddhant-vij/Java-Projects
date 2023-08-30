// How to use ReentrantLock in place of Synchronized?
package p6Threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class WishLock {
  // Without synchronized - irregular output - random mixed order
  // To get regular output - one thread at a time
  // 1. Adding synchronized to method definition
  // 2. Handling it with ReentrantLock Object (w/o sync)
  ReentrantLock l = new ReentrantLock();

  public void wish(String name) {
    l.lock();
    for (int i = 0; i < 5; i++) {
      System.out.print("Good Morning: ");
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
      System.out.println(name);
    }
    l.unlock();
    // Only after a thread which acquired lock in this method unlocks it
    // can another thread acquire it - regular output - one thread at a time
  }

  public void wishTryLock(String name) {
    if (l.tryLock()) {
      // Any of the threads can acquire the lock
      System.out.println("Good Morning! Acquired lock by " + Thread.currentThread().getName());
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
      l.unlock();
    } else {
      // Do this instead of waiting for the lock to be acquired
      System.out.println(Thread.currentThread().getName() + " unable to acquire lock");
    }
  }

  public void wishTryLockWithTime(String name) {
    do {
      try {
        if (l.tryLock(500, TimeUnit.MILLISECONDS)) {
          // Any of the threads can acquire the lock
          System.out.println("Good Morning! Acquired lock by " + Thread.currentThread().getName());
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            System.out.println("Interrupted");
          }
          l.unlock();
          break;
        } else {
          // Do this instead of waiting for the lock to be acquired
          System.out.println(Thread.currentThread().getName() + " unable to acquire lock & will try again!");
        }
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
    } while (true);
  }
}

class MyThreadWish extends Thread {
  private final WishLock display;
  private final String name;

  MyThreadWish(String name, WishLock display) {
    this.name = name;
    this.display = display;
  }

  @Override
  public void run() {
    // display.wish(name);
    // display.wishTryLock(name);
    display.wishTryLockWithTime(name);
  }
}

public class ReentrantLockDemo {
  public static void main(String[] args) {
    // ReentrantLock l = new ReentrantLock(true);
    // l.lock();
    // l.lock();
    // // Main thread acquires two locks here
    // System.out.println(l.getHoldCount()); // 2
    // System.out.println(l.isLocked()); // true
    // System.out.println(l.isHeldByCurrentThread()); // true
    // System.out.println(l.getQueueLength()); // 0
    // l.unlock();
    // // Lock Count = 1 - main thread holds a lock here
    // System.out.println(l.getHoldCount()); // 1
    // System.out.println(l.isLocked()); // true
    // l.unlock();
    // // Lock Count = 0, Released here
    // System.out.println(l.isLocked()); // false
    // System.out.println(l.isFair()); // true

    WishLock display = new WishLock();
    MyThreadWish t1 = new MyThreadWish("Test1", display);
    t1.setName("Thread-1");
    MyThreadWish t2 = new MyThreadWish("Test2", display);
    t2.setName("Thread-2");
    MyThreadWish t3 = new MyThreadWish("Test3", display);
    t3.setName("Thread-3");
    t1.start();
    t2.start();
    t3.start();
  }
}

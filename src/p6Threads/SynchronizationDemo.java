// Synchronized keyword
// Advantage & disadvantage of synchronization
// Race condition - Data inconsistency problem
// Object lock
// Class level lock
// Difference between Object & Class level lock
// Synchronized block
// Synchronized block - Current or any other object lock
// Synchronized block - Class level lock
// Advantage of synchronized block over synchronized method

package p6Threads;

class DisplayWish {
  public synchronized void wish(String name) {
    // ------------------- For the same DisplayWish Object -------------------
    // If not declared as synchronized, the display.wish() method is not thread-safe
    // All the threads t1, t2, t3 and t4 will execute the display.wish() method:
    // ----------> All threads at the same time
    // This will lead to irrregular output

    // If declared as synchronized, the display.wish() method is thread-safe
    // All the threads t1, t2, t3 and t4 will execute the display.wish() method:
    // ----------> Only one thread at a time
    // Which thread is executing the display.wish() method is not known
    // Internal JVM dependency (Thread Scheduler)
    // And that's the regular output

    // Similarly for different DisplayWish objects - irregular output
    // No impact of even using synchronized or not
    // But if declared as static synchronized,
    // then the display.wish() method becomes thread-safe
    // and regular output - static makes it class level lock (not object lock)
    for (int i = 0; i < 5; i++) {
      System.out.print("Good Morning: ");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
      System.out.println(name);
    }
  }
}

class MyThreadWishX extends Thread {
  private final DisplayWish display;
  private final String name;

  MyThreadWishX(String name, DisplayWish display) {
    this.name = name;
    this.display = display;
  }

  @Override
  public void run() {
    display.wish(name);
  }
}

class Display {
  // ------------------- For the same Display Object -------------------
  // Synchronized story same as above.
  // Which method gets executed first is not known
  // IOW: Which thread is executing and displaying the output first is not known
  // Multiple methods - Regular vs Irregular output example

  public synchronized void displayNumbers() {
    for (int i = 0; i < 5; i++) {
      System.out.print(i + 1);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
    }
  }

  public synchronized void displayCharacters() {
    for (int i = 0; i < 5; i++) {
      System.out.print((char) (i + 'A'));
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
    }
  }
}

class MyThreadDisplayNumbers extends Thread {
  private Display display;

  MyThreadDisplayNumbers(Display display) {
    this.display = display;
  }

  @Override
  public void run() {
    display.displayNumbers();
  }
}

class MyThreadDisplayCharacters extends Thread {
  private Display display;

  MyThreadDisplayCharacters(Display display) {
    this.display = display;
  }

  @Override
  public void run() {
    display.displayCharacters();
  }
}

class BigWish {
  public void bigWishMethod(String name) {
    ;
    ;
    ;
    ;
    ;
    // One lakh line of code

    // Without synchronized - irregular output
    // Approach 1: Synchronized for the entire method
    // This is a waste
    // Approach 2: Synchronized for only few line which requires synchronization
    // Do this instead
    synchronized (this) {
      // Instead of synchronized(this) - to get current object level lock,
      // we can also use
      // synchronized (BigWish.class) to get class level lock
      // synchronized (obj) to get another object (obj's) level lock
      for (int i = 0; i < 5; i++) {
        System.out.print("Good Morning: ");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.out.println("Interrupted");
        }
        System.out.println(name);
      }
    }
    ;
    ;
    ;
    ;
    ;
    // One lakh line of code
  }
}

class MyThreadBigWish extends Thread {
  private final BigWish bigWish;
  private final String name;

  MyThreadBigWish(String name, BigWish bigWish) {
    this.name = name;
    this.bigWish = bigWish;
  }

  @Override
  public void run() {
    bigWish.bigWishMethod(name);
  }
}

public class SynchronizationDemo {
  public static void main(String[] args) {
    // --------- Example 1: synchronized - single method ------------
    // DisplayWish display = new DisplayWish();
    // MyThreadWish t1 = new MyThreadWish("Test1", display);
    // MyThreadWish t2 = new MyThreadWish("Test2", display);
    // MyThreadWish t3 = new MyThreadWish("Test3", display);
    // MyThreadWish t4 = new MyThreadWish("Test4", display);
    // t1.start();
    // t2.start();
    // t3.start();
    // t4.start();

    // --------- Example 2: synchronized - multiple methods ------------
    // Display displayNew = new Display();
    // MyThreadDisplayNumbers t5 = new MyThreadDisplayNumbers(displayNew);
    // MyThreadDisplayCharacters t6 = new MyThreadDisplayCharacters(displayNew);
    // t5.start();
    // t6.start();

    // --------- Example 3: synchronized block ------------
    BigWish bigWish = new BigWish();
    MyThreadBigWish t7 = new MyThreadBigWish("Test7", bigWish);
    MyThreadBigWish t8 = new MyThreadBigWish("Test8", bigWish);
    MyThreadBigWish t9 = new MyThreadBigWish("Test9", bigWish);
    MyThreadBigWish t10 = new MyThreadBigWish("Test10", bigWish);
    t7.start();
    t8.start();
    t9.start();
    t10.start();
  }
}

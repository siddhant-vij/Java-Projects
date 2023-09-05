package p6Threads;

// import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
// import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Iterator;
import java.util.Set;
// import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCollectionsDemo extends Thread {

  // ------------------------ For Maps: Segment Level Locking (Concurrency Level) ------------------------

  // static HashMap<Integer, Integer> map = new HashMap<>();
  static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

  void mapRunDriver() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Child Thread trying to modify the map - while Main Thread iterating on it");
    map.put(5, 5);
  }

  // Difference beteween Hashtable, synchronizedMap() and ConcurrentHashMap - How is thread safety achieved?

  // For a Traditional HashMap
  // Set: [0, 1, 2, 3, 4]
  // 0 - 0
  // Child Thread trying to modify the map-while Main Thread iterating on it
  // Exception in thread "main" java.util.ConcurrentModificationException
  //         at java.base/java.util.HashMap$HashIterator.nextNode(HashMap.java:1605)
  //         at java.base/java.util.HashMap$KeyIterator.next(HashMap.java:1628)
  //         at p6Threads.ConcurrentCollectionsDemo.mapDemoDriver(ConcurrentCollectionsDemo.java:124)
  //         at p6Threads.ConcurrentModificationDemo.main(ConcurrentModificationDemo.java:229)

  // For a ConcurrentHashMap
  // Set: [0, 1, 2, 3, 4]
  // 0 - 0
  // Child Thread trying to modify the map - while Main Thread iterating on it
  // 1 - 1
  // 2 - 2
  // 3 - 3
  // 4 - 4
  // 5 - 5
  // End of main thread
  

  
  // ------------------------ For Lists & Sets: Update performed on seperate cloned copy ------------------------

  // static ArrayList<Integer> list = new ArrayList<>();
  static CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
  // static HashSet<Integer> set = new HashSet<>();
  static CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet<>();

  void listRunDriver() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Child Thread trying to modify the list - while Main Thread iterating on it");
    list.add(5);
  }
  void setRunDriver() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Child Thread trying to modify the set - while Main Thread iterating on it");
    set.add(5);
  }

  // Difference beteween Vector, synchronizedList() and CopyOnWriteArrayList - How is thread safety achieved?

  // For a Traditional ArrayList <-> Similarly for Traditional HashSet (line no: 167/232)
  // 0
  // Child Thread trying to modify the list - while Main Thread iterating on it
  // Exception in thread "main" java.util.ConcurrentModificationException
  //         at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
  //         at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
  //         at p6Threads.ConcurrentCollectionsDemo.listDemoDriver(ConcurrentCollectionsDemo.java:145)
  //         at p6Threads.ConcurrentCollectionsDemo.main(ConcurrentCollectionsDemo.java:230)


  // For a CopyOnWriteArrayList <-> Similarly for CopyOnWriteArraySet
  // 0
  // Child Thread trying to modify the list - while Main Thread iterating on it
  // 1
  // 2
  // 3
  // 4
  // [0, 1, 2, 3, 4, 5]
  // End of main thread

  @Override
  public void run() {
    // mapRunDriver();
    // listRunDriver();
    setRunDriver();
  }

  static void mapDemoDriver() throws InterruptedException {
    map.put(0, 0);
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);

    ConcurrentCollectionsDemo th = new ConcurrentCollectionsDemo();
    th.start();

    Set<Integer> set = map.keySet();
    System.out.println("Set: " + set);

    Iterator<Integer> itr = set.iterator();
    while (itr.hasNext()) {
      int x = itr.next();
      System.out.println(x + " - " + map.get(x));
      Thread.sleep(3000);
    }
    // Update to the list - from the child thread, impacts the ConcurrentHashMap - while iterating
    // map.put(5, 5); - Entry shown in the output during iteration 
    System.out.println("End of main thread");
  }

  static void listDemoDriver1() throws InterruptedException {
    list.add(0);
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);

    ConcurrentCollectionsDemo th = new ConcurrentCollectionsDemo();
    th.start();

    Iterator<Integer> itr = list.iterator();
    while (itr.hasNext()) {
      System.out.println(itr.next());
      Thread.sleep(3000);
    }
    // ------------------------ For CopyOnWriteArrayList ------------------------
    // A cloned copy is created when child thread tries to update the list - Write operation
    // And the original list is not modified - that's why iterator is not showing 5
    // Printing the full list here - will show 5 in the list (JVM merges the list with cloned copy)
    System.out.println(list);
    System.out.println("End of main thread");
  }
  static void setDemoDriver1() throws InterruptedException {
    set.add(0);
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);

    ConcurrentCollectionsDemo th = new ConcurrentCollectionsDemo();
    th.start();

    Iterator<Integer> itr = set.iterator();
    while (itr.hasNext()) {
      System.out.println(itr.next());
      Thread.sleep(3000);
    }
    // ------------------------ For CopyOnWriteArraySet ------------------------
    // A cloned copy is created when child thread tries to update the set - Write operation
    // And the original set is not modified - that's why iterator is not showing 5
    // Printing the full set here - will show 5 in the set (JVM merges the set with cloned copy)
    System.out.println(set);
    System.out.println("End of main thread");
  }
  static void listDemoDriver2() throws InterruptedException {
    list.add(0);
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);

    Iterator<Integer> itr = list.iterator();
    while (itr.hasNext()) {
      int x = itr.next();
      if (x % 2 == 0) {
        itr.remove();
      }
      Thread.sleep(300);
    }
    // Iterator can perform remove() operation only if the list is Traditional ArrayList

    // But raises UnsupportedOperationException if the list is CopyOnWriteArrayList:
    // Exception in thread "main" java.lang.UnsupportedOperationException
    //     at java.base/java.util.concurrent.CopyOnWriteArrayList$COWIterator.remove(CopyOnWriteArrayList.java:1124)
    //     at p6Threads.ConcurrentCollectionsDemo.listDemoDriver2(ConcurrentCollectionsDemo.java:188)
    //     at p6Threads.ConcurrentCollectionsDemo.main(ConcurrentCollectionsDemo.java:231)
    System.out.println(list);
    System.out.println("End of main thread");
  }
  static void setDemoDriver2() throws InterruptedException {
    set.add(0);
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);

    Iterator<Integer> itr = set.iterator();
    while (itr.hasNext()) {
      int x = itr.next();
      if (x % 2 == 0) {
        itr.remove();
      }
      Thread.sleep(300);
    }
    // Iterator can perform remove() operation only if the set is Traditional HashSet

    // But raises UnsupportedOperationException if the set is CopyOnWriteArraySet:
    // Exception in thread "main" java.lang.UnsupportedOperationException
    //     at java.base/java.util.concurrent.CopyOnWriteArrayList$COWIterator.remove(CopyOnWriteArrayList.java:1124)
    //     at p6Threads.ConcurrentCollectionsDemo.listDemoDriver2(ConcurrentCollectionsDemo.java:213)
    //     at p6Threads.ConcurrentCollectionsDemo.main(ConcurrentCollectionsDemo.java:233)
    System.out.println(set);
    System.out.println("End of main thread");
  }

  public static void main(String[] args) throws InterruptedException {
    // mapDemoDriver(); // One Thread tring to modify the map - while Main Thread iterating on it
    // listDemoDriver1(); // One Thread tring to modify the list - while Main Thread iterating on it
    // listDemoDriver2(); // Behaviour of both list types - for remove() method
    setDemoDriver1(); // One Thread tring to modify the set - while Main Thread iterating on it
    // setDemoDriver2(); // Behaviour of both set types - for remove() method
  }
}
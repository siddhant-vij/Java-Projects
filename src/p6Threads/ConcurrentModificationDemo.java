package p6Threads;

// import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentModificationDemo extends Thread {
// For a Traditional HashMap
// 0-0
// Child Thread trying to modify the map-while Main Thread iterating on it
// Exception in thread "main" java.util.ConcurrentModificationException
//         at java.base/java.util.HashMap$HashIterator.nextNode(HashMap.java:1605)
//         at java.base/java.util.HashMap$KeyIterator.next(HashMap.java:1628)
//         at p6Threads.ConcurrentModificationDemo.main(ConcurrentModificationDemo.java:55)

// For a ConcurrentHashMap
// 0-0
// Child Thread trying to modify the map-while Main Thread iterating on it
// 1-1
// 2-2
// 3-3
// 4-4
// 5-5
// End of main thread

  // static HashMap<Integer, Integer> map = new HashMap<>();
  static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

  @Override
  public void run() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Child Thread trying to modify the map - while Main Thread iterating on it");
    map.put(5, 5);
  }

  public static void main(String[] args) throws InterruptedException {
    map.put(0, 0);
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);

    ConcurrentModificationDemo th = new ConcurrentModificationDemo();
    th.start();

    Set<Integer> set = map.keySet();

    Iterator<Integer> itr = set.iterator();
    while (itr.hasNext()) {
      int x = itr.next();
      System.out.println(x + " - " + map.get(x));
      Thread.sleep(3000);
    }
    System.out.println("End of main thread");
  }
}
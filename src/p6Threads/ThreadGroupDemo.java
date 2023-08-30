package p6Threads;

class MyThread extends Thread {
  MyThread(ThreadGroup g, String name) {
    super(g, name);
  }

  @Override
  public void run() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

public class ThreadGroupDemo {
  public static void main(String[] args) throws InterruptedException {
    ThreadGroup pg = new ThreadGroup("Parent Group");
    ThreadGroup cg = new ThreadGroup(pg, "Child Group");

    MyThread t1 = new MyThread(pg, "Thread-1");
    MyThread t2 = new MyThread(pg, "Thread-2");
    MyThread t3 = new MyThread(cg, "Thread-3");
    t1.start();
    t2.start();
    t3.start();

    System.out.println("Active Thread Count in PG: " + pg.activeCount()); // 3
    // Counts the number of active threads in the specified thread group -
    // including the threads in all its subgroups - Hence, output = 3
    System.out.println("Active Sub Group Count in PG: " + pg.activeGroupCount()); // 1
    System.out.println("Active Thread Count in CG: " + cg.activeCount()); // 1
    System.out.println("Active Sub Group Count in CG: " + cg.activeGroupCount()); // 0
    System.out.println("PG - list()");
    pg.list();

    Thread.sleep(1000);
    // Wait for 1 second
    // All the threads in the Parent Group will be terminated after 500ms sleep

    System.out.println("\nActive Thread Count in PG: " + pg.activeCount()); // 0
    System.out.println("Active Sub Group Count in PG: " + pg.activeGroupCount()); // 1
    System.out.println("Active Thread Count in CG: " + cg.activeCount()); // 0
    System.out.println("Active Sub Group Count in CG: " + cg.activeGroupCount()); // 0
    System.out.println("PG - list()");
    pg.list();

    // To get names of all the threads in the System Group
    ThreadGroup system = Thread.currentThread().getThreadGroup().getParent();
    Thread[] tAll = new Thread[system.activeCount()];
    system.enumerate(tAll);
    System.out.println("\n\nNames of all the threads in the System Group:");
    for (Thread t : tAll) {
      System.out.println(t.getName());
    }
  }
}

package p6Threads;

class CustomerThread extends Thread {
  private static int customerId = 0;
  public static ThreadLocal<Integer> myThreadLocal = new ThreadLocal<>() {
    // If this is not declared static,
    // then all threads will have the same value for customerId (1)
    @Override
    protected Integer initialValue() {
      return ++customerId;
    }
  };

  public CustomerThread(String name) {
    super(name);
  }

  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + " exeuting with Customer ID: " + myThreadLocal.get());
  }
}

class ParentThread extends Thread {
  // public static ThreadLocal<String> xxThreadLocal = new ThreadLocal<>();
  // Parent Thread's local values not available to the Child Thread - hence, null
  public static InheritableThreadLocal<String> xxThreadLocal = new InheritableThreadLocal<>();
  // By default, child thread inherits the parent thread's value as well. But can be customized
  public static InheritableThreadLocal<String> xxThreadLocalChildValue = new InheritableThreadLocal<>() {
    @Override
    public String childValue(String p) {
      return "Child Value";
    }
  };

  @Override
  public void run() {
    xxThreadLocal.set("Parent Thread");
    xxThreadLocalChildValue.set("Parent Thread");
    ChildThread ct = new ChildThread();
    ct.start();
  }
}

class ChildThread extends Thread {
  @Override
  public void run() {
    System.out.println(ParentThread.xxThreadLocal.get());
    // null with ThreadLocal
    // Parent Thread with InheritableThreadLocal
    System.out.println(ParentThread.xxThreadLocalChildValue.get());
    // Child Value with InheritableThreadLocal
    // overriding childValue() method
  }
}

public class ThreadLocalDemo {
  public static void main(String[] args) {
    // CustomerThread c1 = new CustomerThread("Customer 1");
    // CustomerThread c2 = new CustomerThread("Customer 2");
    // CustomerThread c3 = new CustomerThread("Customer 3");
    // CustomerThread c4 = new CustomerThread("Customer 4");
    // c1.start();
    // c2.start();
    // c3.start();
    // c4.start();

    ParentThread pt = new ParentThread();
    pt.start();
  }
}

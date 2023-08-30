// Use synchronized responsibly.

package p6Threads;

class A {
  public synchronized void d1(B b) {
    System.out.println("t1 starts execution of d1()");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.println("t1 interrupted");
    }
    System.out.println("t1 trying to call B's last()");
    b.last();
  }

  public synchronized void last() {
    System.out.println("Inside A's last()");
  }
}

class B {
  public synchronized void d2(A a) {
    System.out.println("t2 starts execution of d2()");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.println("t2 interrupted");
    }
    System.out.println("t2 trying to call A's last()");
    a.last();
  }

  public synchronized void last() {
    System.out.println("Inside B's last()");
  }
}

class Thread1 extends Thread {
  A a;
  B b;

  Thread1(A a, B b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public void run() {
    a.d1(b);
  }
}

class Thread2 extends Thread {
  A a;
  B b;

  Thread2(A a, B b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public void run() {
    b.d2(a);
  }
}

public class DeadlockDemo {
  public static void main(String[] args) {
    A a = new A();
    B b = new B();
    Thread1 t1 = new Thread1(a, b);
    Thread2 t2 = new Thread2(a, b);
    t1.start();
    t2.start();
    System.out.println("Main thread ends");
  }
}

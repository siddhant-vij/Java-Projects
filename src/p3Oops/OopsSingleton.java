// Remember the key points while defining class as a singleton class that is while designing a singleton class: 
// 1. Make a constructor private.
// 2. Write a static method that has the return type object of this singleton class.

package p3Oops;

class Singleton {
  private static Singleton single_instance = null;
  public String s;

  private Singleton() {
    s = "Hello I am a string part of Singleton class";
  }

  public static Singleton getInstance() {
    if (single_instance == null)
      single_instance = new Singleton();

    return single_instance;
  }
}

public class OopsSingleton {

  public static void main(String args[]) {
    Singleton x = Singleton.getInstance();
    Singleton y = Singleton.getInstance();
    Singleton z = Singleton.getInstance();

    if (x == y && y == z) {
      System.out.println("Three objects point to the same memory location on the heap i.e, to the same object");
    } else {
      System.out.println("Three objects DO NOT point to the same memory location on the heap");
    }

    System.out.println();

    x.s = (x.s).toUpperCase();
    System.out.println("String from x is " + x.s);
    System.out.println("String from y is " + y.s);
    System.out.println("String from z is " + z.s);

    System.out.println();

    z.s = (z.s).toLowerCase();
    System.out.println("String from x is " + x.s);
    System.out.println("String from y is " + y.s);
    System.out.println("String from z is " + z.s);
  }
}
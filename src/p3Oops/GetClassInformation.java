package p3Oops;

import java.lang.reflect.Method;

class Test {
  public void m1() {
    System.out.println("Test m1");
  }
}

public class GetClassInformation {
  public static void main(String[] args) {
    Object o1 = new String("Test String");
    Class<?> c1 = o1.getClass();
    System.out.println("Fully qualified name of c1: " + c1.getName());
    Method[] methods = c1.getMethods();
    int count = 0;
    for (Method method : methods) {
      count++;
      System.out.println(method.getName());
    }
    System.out.println("Number of methods in String: " + count);

    System.out.println();

    Object o2 = new Test();
    Class<?> c2 = o2.getClass();
    System.out.println("Fully qualified name of c2: " + c2.getName());
    Method[] methods2 = c2.getMethods();
    count = 0;
    for (Method method : methods2) {
      count++;
      System.out.println(method.getName());
    }
    System.out.println("Number of methods in Test: " + count);
  } 
}

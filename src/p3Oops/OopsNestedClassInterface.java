// Java Inner Classes (Nested Classes)
// Java inner class or nested class is a class that is declared inside the class or interface.
// We use inner classes to logically group classes and interfaces in one place to be more readable and maintainable.
// Additionally, it can access all the members of the outer class, including private data members and methods.

// Member Inner Class: A class created within class and outside method.
// Anonymous Inner Clas: A class created for implementing an interface or extending class. The java compiler decides its name.
// Local Inner Class: A class created within a method.
// Static Nested Class: A static class created within the class.

package p3Oops;

class TestInnerClass {
  private int data;
  private static int value = 25;

  public TestInnerClass(int data) {
    this.data = data;
  }

  class InnerClass {
    public void msg() {
      System.out.println(data);
    }

    public static void showMsg() {
      System.out.println(value);
    }
  }
}

interface Test {
  void test();
}

class TestAnonymousInnerClass {
  private int data;
  private static int value = 50;

  public TestAnonymousInnerClass(int data) {
    this.data = data;
  }

  public void msgA() {
    Test test = new Test() {
      @Override
      public void test() {
        System.out.println(data);
      }
    };
    test.test();
  }

  public static void msgAT() {
    Test test = new Test() {
      @Override
      public void test() {
        System.out.println(value);
      }
    };
    test.test();
  }
}

class TestLocalInnerClass {
  private int data;
  static private int value = 100;

  public TestLocalInnerClass(int data) {
    this.data = data;
  }

  void display() {
    class Local {
      void msg() {
        System.out.println(data);
      }
    }
    Local local = new Local();
    local.msg();
  }

  static void displayNS() {
    class Local {
      void msg() {
        System.out.println(value);
      }
    }
    Local local = new Local();
    local.msg();
  }

  void displayS() {
    class Local {
      static void msg() {
        System.out.println(value);
      }
    }
    Local.msg();
  }

  static void displaySS() {
    class Local {
      static void msg() {
        System.out.println(value);
      }
    }
    Local.msg();
  }
}

class TestStaticInnerClass {
  private static int value = 200;

  static class InnerStatic {

    void msg() {
      System.out.println(value);
    }

    static void msgS() {
      System.out.println(value);
    }
  }
}

public class OopsNestedClassInterface {
  public static void main(String[] args) {
    // Java Member Inner class
    TestInnerClass obj = new TestInnerClass(5);
    TestInnerClass.InnerClass inObj = obj.new InnerClass();
    inObj.msg();
    TestInnerClass.InnerClass.showMsg();

    // Java Anonymous class
    TestAnonymousInnerClass objA = new TestAnonymousInnerClass(10);
    objA.msgA();
    TestAnonymousInnerClass.msgAT();

    // Java Local Inner class
    TestLocalInnerClass objI = new TestLocalInnerClass(20);
    objI.display();
    objI.displayS();
    TestLocalInnerClass.displaySS();
    TestLocalInnerClass.displayNS();

    // Java Static Inner class
    TestStaticInnerClass.InnerStatic objS = new TestStaticInnerClass.InnerStatic();
    objS.msg();
    TestStaticInnerClass.InnerStatic.msgS();
  }
}

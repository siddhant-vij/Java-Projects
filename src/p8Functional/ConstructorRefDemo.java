package p8Functional;

@FunctionalInterface
interface MyInterf<R, T> {
  R myMethod(T t);
}

class MyClass<T> {
  T val;

  public MyClass(T val) {
    this.val = val;
  }

  public T getVal() {
    return val;
  }
}

class MyClassFactory {
  public static <R, T> R create(MyInterf<R, T> myInterf, T t) {
    return myInterf.myMethod(t);
  }
}

public class ConstructorRefDemo {
  public static void main(String[] args) {
    // Using Consructor Reference to create an object of MyClass<String>
    MyInterf<MyClass<String>, String> myInterf = MyClass::new;
    MyClass<String> myClass = MyClassFactory.create(myInterf, "Hello");
    System.out.println(myClass.getVal());

    // Using Consructor Reference to create an object of MyClass<Integer>
    MyInterf<MyClass<Integer>, Integer> myInterf2 = MyClass::new;
    MyClass<Integer> myClass2 = MyClassFactory.create(myInterf2, 10);
    System.out.println(myClass2.getVal());
  }
}

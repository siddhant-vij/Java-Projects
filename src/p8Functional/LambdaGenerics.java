package p8Functional;

@FunctionalInterface
interface MyInter<T> {
  T myMethod(T t);
}

class TestClass {
  int val;

  TestClass(int val) {
    this.val = val;
  }

  public TestClass testMethod(TestClass t) {
    return this.val > t.val ? this : t;
  }

  @Override
  public String toString() {
    return String.valueOf(this.val);
  }
}

public class LambdaGenerics {
  public static void main(String[] args) {
    // Using a String type<T> lambda to operate on a String (eg. reverse)
    MyInter<String> reverse = (str) -> new StringBuilder(str).reverse().toString();
    System.out.println(reverse.myMethod("Hello"));

    // Using an Integer type<T> lambda to operate on an Integer (eg. square)
    MyInter<Integer> square = (num) -> num * num;
    System.out.println(square.myMethod(10));

    // Using a TestClass type<T> lambda to operate on a TestClass (eg. max)
    MyInter<TestClass> max = (t1) -> t1.testMethod(new TestClass(10));
    System.out.println(max.myMethod(new TestClass(5)));
  }
}

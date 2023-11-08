package p8Functional;

import java.util.Arrays;

@FunctionalInterface
interface MyInterAdv<T> {
  boolean myMethod(T v1, T v2);
}

class Temperature {
  private int temp;

  public Temperature(int temp) {
    this.temp = temp;
  }

  public boolean sameTemp(Temperature t) {
    return this.temp == t.temp;
  }

  public boolean lessThan(Temperature t) {
    return this.temp < t.temp;
  }

  public boolean greaterThan(Temperature t) {
    return this.temp > t.temp;
  }

  @Override
  public String toString() {
    return String.valueOf(this.temp);
  }
}

class Comparison {
  public static <T> int count(MyInterAdv<T> m, T[] arr, T t) {
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
      if (m.myMethod(arr[i], t)) {
        count++;
      }
    }
    return count;
  }
}

public class MethRefGenerics1 {
  public static void main(String[] args) {
    Temperature[] arr = new Temperature[10];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = new Temperature((int) (Math.random() * 100));
    }
    System.out.println("Array: " + Arrays.toString(arr));
    System.out.println("Same as 50: " + Comparison.count(Temperature::sameTemp, arr, new Temperature(50)));
    System.out.println("Less than 50: " + Comparison.count(Temperature::lessThan, arr, new Temperature(50)));
    System.out.println("Greater than 50: " + Comparison.count(Temperature::greaterThan, arr, new Temperature(50)));
  }
}

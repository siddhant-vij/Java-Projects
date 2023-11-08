package p8Functional;

import java.util.Arrays;

@FunctionalInterface
interface MyInterAdvd<T> {
  int myMethod(T[] vals, T t);
}

class ArrayCounter {
  public static <T extends Comparable<T>> int sameCount(T[] arr, T t) {
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].equals(t)) {
        count++;
      }
    }
    return count;
  }

  public static <T extends Comparable<T>> int lessThanCount(T[] arr, T t) {
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].compareTo(t) < 0) {
        count++;
      }
    }
    return count;
  }

  public static <T extends Comparable<T>> int greaterThanCount(T[] arr, T t) {
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].compareTo(t) > 0) {
        count++;
      }
    }
    return count;
  }
}

public class MethRefGenerics2 {
  private static <T> int counter(MyInterAdvd<T> m, T[] arr, T t) {
    return m.myMethod(arr, t);
  }
  public static void main(String[] args) {
    Integer[] arr = new Integer[10];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = (int) (Math.random() * 100);
    }
    System.out.println("Array: " + Arrays.toString(arr));
    System.out.println("Same as 50: " + counter(ArrayCounter::sameCount, arr, 50));
    System.out.println("Less than 50: " + counter(ArrayCounter::lessThanCount, arr, 50));
    System.out.println("Greater than 50: " + counter(ArrayCounter::greaterThanCount, arr, 50));
  }  
}

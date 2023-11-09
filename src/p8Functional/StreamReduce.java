package p8Functional;

import java.util.Arrays;
import java.util.List;

public class StreamReduce {
  private static void sumOfListElements(List<Integer> list) {
    // Use reduce() method to get the sum of all elements in the list
    int sum1 = list.stream().reduce(Integer::sum).get();
    System.out.println("Sum: " + sum1);

    int sum2 = list.stream().reduce(0, Integer::sum);
    System.out.println("Sum: " + sum2);
  }

  private static void integerProductOfListElements(List<Integer> list) {
    // Use reduce() method to get the product of all elements in the list
    int product1 = list.stream().reduce(1, (x, y) -> x * y);
    System.out.println("Product: " + product1);

    int product2 = list.stream().reduce(1, (x, y) -> x * y, (x, y) -> x * y);
    System.out.println("Product: " + product2);
  }

  private static void productOfSqrtOfListElements(List<Integer> list) {
    // Use reduce() method to get the product of SQRT ofall elements in the list
    double product1 = list.parallelStream().reduce(1.0, (x, y) -> x * Math.sqrt(y), (x, y) -> x * y);
    System.out.println("Product: " + product1);

    // Using map to get the SQRT of each element - chained with product reduction
    double product2 = list.stream().map(x -> Math.sqrt(x)).reduce(1.0, (x, y) -> x * y);
    System.out.println("Product: " + product2);
  }

  public static void main(String[] args) {
    List<Integer> list = Arrays.asList(56, 32, 12, 78, 65);
    System.out.println("Original List: " + list);

    sumOfListElements(list);
    integerProductOfListElements(list);
    productOfSqrtOfListElements(list);
  }
}

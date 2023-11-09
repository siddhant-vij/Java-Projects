package p8Functional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamBasics {
  public static void main(String[] args) {
    List<Integer> list = Arrays.asList(56, 32, 12, 78, 65, 20, 15, 89, 74, 70);
    System.out.println("Original List: " + list);

    // Obtain a Stream from a List
    Stream<Integer> stream = list.stream();

    // Get the Minimum value - Terminal Operation
    Integer min = stream.min(Integer::compare).get();
    System.out.println("Minimum value: " + min);

    // Get the Maximum value - Terminal Operation
    stream = list.stream();
    Integer max = stream.max(Integer::compare).get();
    System.out.println("Maximum value: " + max);

    // Sort the Stream
    Stream<Integer> sortedStream1 = list.stream().sorted();

    // Using forEach() method to print the sorted Stream - Terminal
    sortedStream1.forEach(System.out::println); // Method Reference

    Stream<Integer> sortedStream2 = list.stream().sorted();
    sortedStream2.forEach((x) -> System.out.print(x + " ")); // Lambda Expression
    System.out.println();

    // Filter the Stream based on Predicate (Even Numbers)
    Stream<Integer> filteredStream = list.stream().filter((x) -> x % 2 == 0);
    filteredStream.forEach((x) -> System.out.print(x + " "));
    System.out.println();

    // Filter the Stream based on Predicates (Even Numbers Greater Than 50)
    filteredStream = list.stream()
        .filter((x) -> x % 2 == 0)
        .filter((x) -> x > 50);
    filteredStream.forEach((x) -> System.out.print(x + " "));
    System.out.println();
  }
}

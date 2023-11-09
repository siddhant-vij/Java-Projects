package p8Functional;

import java.util.Iterator;
import java.util.List;
// import java.util.ListIterator;
import java.util.Spliterator;

public class StreamIterators {
  public static void main(String[] args) {
    List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    System.out.print("Using Iterator: ");
    Iterator<Integer> iterator = list.stream().iterator();
    while (iterator.hasNext()) {
      System.out.print(iterator.next() + " ");
    }

    System.out.println();

    System.out.print("\nUsing ListIterator: Not Supported");
    // ListIterator<Integer> listIterator = list.stream().listIterator();
    // ListIterator is not supported for streams

    System.out.println("\n");

    System.out.print("Using Spliterator (tryAdvance): ");
    Spliterator<Integer> spliterator = list.stream().spliterator();
    while (spliterator.tryAdvance((x) -> System.out.print(x + " ")))
      ;

    System.out.println("\n");

    System.out.print("Using Spliterator (forEachRemaining): ");
    Spliterator<Integer> spliterator2 = list.stream().spliterator();
    spliterator2.forEachRemaining((x) -> System.out.print(x + " "));

    System.out.println("\n");

    System.out.print("Using Spliterator (trySplit): ");
    Spliterator<Integer> spliteratorM = list.stream().spliterator();
    Spliterator<Integer> spliteratorN = spliteratorM.trySplit();
    System.out.print("\n\tUsing SpliteratorN: ");
    spliteratorN.forEachRemaining((x) -> System.out.print(x + " "));
    System.out.print("\n\tUsing SpliteratorM: ");
    spliteratorM.forEachRemaining((x) -> System.out.print(x + " "));
  }
}

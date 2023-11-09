package p8Functional;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class EFG {
  int e;
  int f;
  int g;

  public EFG(int e, int f, int g) {
    this.e = e;
    this.f = f;
    this.g = g;
  }
}

class EF implements Comparable<EF> {
  int e;
  int f;

  public EF(int e, int f) {
    this.e = e;
    this.f = f;
  }

  @Override
  public int compareTo(EF o) {
    return this.e - o.e;
  }
}

public class StreamCollectors {
  public static void main(String[] args) {
    List<EFG> list = List.of(new EFG(1, 2, 3), new EFG(4, 5, 6), new EFG(7, 8, 9));
    list.forEach(x -> System.out.println(x.e + " " + x.f + " " + x.g));

    System.out.println("\nGetting EF from EFG...");

    List<EF> listCollect = list.stream().map(x -> new EF(x.e, x.f)).collect(Collectors.toList());
    System.out.println("\nList: ");
    System.out.println(listCollect.getClass());
    listCollect.forEach(x -> System.out.println(x.e + " " + x.f));

    Set<EF> setCollect = list.stream().map(x -> new EF(x.e, x.f)).collect(Collectors.toSet());
    System.out.println("\nSet: ");
    System.out.println(setCollect.getClass());
    setCollect.forEach(x -> System.out.println(x.e + " " + x.f));

    // Getting a LinkedList from the List using collect - Using Lambda Expressions
    List<EF> listCollect2 = list.stream().map(x -> new EF(x.e, x.f)).collect(
        () -> new LinkedList<>(),
        (listC, element) -> listC.add(element),
        (list1, list2) -> list1.addAll(list2));
    System.out.println("\nLinked List: ");
    System.out.println(listCollect2.getClass());
    listCollect2.forEach(x -> System.out.println(x.e + " " + x.f));

    // Getting a TreeSet from the List using collect - Using Method References
    Set<EF> setCollect2 = list.stream().map(x -> new EF(x.e, x.f)).collect(
        TreeSet::new,
        TreeSet::add,
        TreeSet::addAll);
    System.out.println("\nTree Set: ");
    System.out.println(setCollect2.getClass());
    setCollect2.forEach(x -> System.out.println(x.e + " " + x.f));
  }
}

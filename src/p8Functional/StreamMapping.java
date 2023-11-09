package p8Functional;

import java.util.List;

class ABC {
  int a;
  int b;
  int c;

  public ABC(int a, int b, int c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }
}

class AB {
  int a;
  int b;

  public AB(int a, int b) {
    this.a = a;
    this.b = b;
  }
}

class DoubleToIntList {
  public static void convertDoubleToInt(List<Double> list) {
    list.stream().mapToInt(x -> (int) Math.ceil(x)).forEach(x -> System.out.print(x + " "));
  }
}

public class StreamMapping {
  public static void main(String[] args) {
    List<ABC> list = List.of(new ABC(1, 2, 3), new ABC(4, 5, 6), new ABC(7, 8, 9));
    list.stream().forEach(x -> System.out.println(x.a + " " + x.b + " " + x.c));

    System.out.println("\nGetting AB from ABC...");

    list.stream().map(x -> new AB(x.a, x.b)).forEach(x -> System.out.println(x.a + " " + x.b));

    List<Double> listD = List.of(1.1, 3.6, 2.7, 9.2, 4.8, 12.1, 5.0);
    System.out.println("\n\nList of Doubles: " + listD);
    System.out.println("\nConverting Double to Int (Ceil)...");
    DoubleToIntList.convertDoubleToInt(listD);
  }
}

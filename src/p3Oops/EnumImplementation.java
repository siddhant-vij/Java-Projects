// Java Enumerations - list of named constants

package p3Oops;

public enum EnumImplementation {
  Apple(6, 110),
  Mango(3, 85),
  Banana(12, 25),
  Melon(2, 56),
  Papaya(6, 78);

  private int price;
  private int units;

  EnumImplementation(int units, int price) {
    this.units = units;
    this.price = price;
  }

  int totalCost() {
    return this.units * this.price;
  }

  public static void main(String[] args) {
    for (EnumImplementation fruit : EnumImplementation.values()) {
      System.out.println((fruit.ordinal() + 1) + ". " + fruit + ": " + fruit.totalCost());
    }
    EnumImplementation fruit = EnumImplementation.Apple;
    switch (fruit) {
      case Apple:
        System.out.println("Apple");
        break;
      case Banana:
        System.out.println("Banana");
        break;
      case Mango:
        System.out.println("Mango");
        break;
      case Melon:
        System.out.println("Melon");
        break;
      case Papaya:
        System.out.println("Papaya");
        break;
      default:
        System.out.println("It's not a fruit - not in the list");
        break;
    }
  }
}

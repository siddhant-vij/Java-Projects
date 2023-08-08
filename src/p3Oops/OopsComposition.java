// OOPs (Object-Oriented Programming System)
// Inheritance, Polymorphism, Abstraction, Encapsulation.

// Apart from these concepts,there are some other terms which are used in Object-Oriented design:
// Coupling: Coupling refers to the knowledge or information or dependency of another class. It arises when classes are aware of each other. If a class has the details information of another class, there is strong coupling. In Java, we use private, protected, and public modifiers to display the visibility level of a class, method, and field. You can use interfaces for the weaker coupling because there is no concrete implementation.

// Cohesion: Cohesion refers to the level of a component which performs a single well-defined task. A single well-defined task is done by a highly cohesive method. The weakly cohesive method will split the task into separate parts. The java.io package is a highly cohesive package because it has I/O related classes and interface. However, the java.util package is a weakly cohesive package because it has unrelated classes and interfaces.

// Association: Association represents the relationship between the objects. Here, one object can be associated with one object or many objects. There can be four types of association between the objects: One to One, One to Many, Many to One, and Many to Many. Association can be undirectional or bidirectional.

// Aggregation: Aggregation is a way to achieve Association. Aggregation represents the relationship where one object contains other objects as a part of its state. It represents the weak relationship between objects. It is also termed as a has-a relationship in Java. Like, inheritance represents the is-a relationship. It is another way to reuse objects.

// Composition: The composition is also a way to achieve Association. The composition represents the relationship where one object contains other objects as a part of its state. There is a strong relationship between the containing object and the dependent object. It is the state where containing objects do not have an independent existence. If you delete the parent object, all the child objects will be deleted automatically.

package p3Oops;

import java.util.Scanner;

class SmartKitchen {

  private CoffeeMaker brewMaster;
  private Refrigerator iceBox;
  private DishWasher dishWasher;

  public SmartKitchen() {
    brewMaster = new CoffeeMaker();
    iceBox = new Refrigerator();
    dishWasher = new DishWasher();
  }

  public CoffeeMaker getBrewMaster() {
    return brewMaster;
  }

  public Refrigerator getIceBox() {
    return iceBox;
  }

  public DishWasher getDishWasher() {
    return dishWasher;
  }

  public void setKitchenState(boolean coffeeFlag, boolean fridgeFlag,
      boolean dishWasherFlag) {

    brewMaster.setHasWorkToDo(coffeeFlag);
    iceBox.setHasWorkToDo(fridgeFlag);
    dishWasher.setHasWorkToDo(dishWasherFlag);
  }

  public void doKitchenWork() {
    brewMaster.brewCoffee();
    iceBox.orderFood();
    dishWasher.doDishes();
  }
}

class CoffeeMaker {

  private boolean hasWorkToDo;

  public void setHasWorkToDo(boolean hasWorkToDo) {
    this.hasWorkToDo = hasWorkToDo;
  }

  public void brewCoffee() {

    if (hasWorkToDo) {
      System.out.println("Brewing Coffee");
      hasWorkToDo = false;
    }
  }
}

class Refrigerator {

  private boolean hasWorkToDo;

  public void setHasWorkToDo(boolean hasWorkToDo) {
    this.hasWorkToDo = hasWorkToDo;
  }

  public void orderFood() {

    if (hasWorkToDo) {
      System.out.println("Ordering Food");
      hasWorkToDo = false;
    }
  }
}

class DishWasher {

  private boolean hasWorkToDo;

  public void setHasWorkToDo(boolean hasWorkToDo) {
    this.hasWorkToDo = hasWorkToDo;
  }

  public void doDishes() {

    if (hasWorkToDo) {
      System.out.println("Washing Dishes");
      hasWorkToDo = false;
    }
  }
}

public class OopsComposition {

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      SmartKitchen kitchen = new SmartKitchen();
      // kitchen.getDishWasher().setHasWorkToDo(true);
      // kitchen.getIceBox().setHasWorkToDo(true);
      // kitchen.getBrewMaster().setHasWorkToDo(true);
      // kitchen.getDishWasher().doDishes();
      // kitchen.getIceBox().orderFood();
      // kitchen.getBrewMaster().brewCoffee();
      kitchen.setKitchenState(true, false, true);
      kitchen.doKitchenWork();
    }
  }
}

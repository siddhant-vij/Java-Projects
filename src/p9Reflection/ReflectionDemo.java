package p9Reflection;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

final class Employee extends Thread implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;
  static String name;
  private int age;
  protected String address;
  public static double salary;

  static {
    name = "John Doe";
    salary = 1000.0;
  }

  Employee() throws ArithmeticException {
  }

  public Employee(int age, String address) {
    this.age = age;
    this.address = address;
  }

  public void bonus() throws ArithmeticException {
    System.out.println("Employee bonus (from class): " + getBonus(salary));
  }

  private double getBonus(double salary) {
    return salary * 0.1;
  }

  int getAge() throws IllegalArgumentException {
    if (age > 0) {
      return age;
    } else {
      throw new IllegalArgumentException("Age must be greater than 0");
    }
  }

  @Override
  public String toString() {
    return "Employee [age=" + age + ", address=" + address + "]";
  }
}

public class ReflectionDemo {
  public static void main(String[] args) throws Exception {
    Class<?> c = Employee.class;
    /*
     * Print class information - name, package, superclass, interfaces, modifiers
     */
    System.out.println("Class Meta Information:");
    System.out.println(" - Class: " + c.getSimpleName());
    System.out.println(" - Package: " + c.getPackage().getName());
    System.out.println(" - Superclass: " + c.getSuperclass().getName());
    System.out.print(" - Interfaces: ");
    for (Class<?> i : c.getInterfaces()) {
      System.out.print(i.getName() + " ");
    }
    System.out.println();
    System.out.println(" - Modifiers: " + Modifier.toString(c.getModifiers()));

    System.out.println("-----------------------------");

    /*
     * Print field information - name, type, value, modifiers
     */
    System.out.println("Fields Meta Information:");
    for (Field f : c.getDeclaredFields()) {
      f.setAccessible(true);
      System.out.print(" - Name: " + f.getName() + ", Type: " + f.getType().getName());
      System.out.print(", Value: " + f.get(new Employee()));
      System.out.println(", Modifiers: " + Modifier.toString(f.getModifiers()));
    }

    System.out.println("-----------------------------");

    /*
     * Print method & constructor information - name, modifiers, parameter type,
     * return type (if applicable), exceptions
     */
    System.out.println("Methods Meta Information:");
    for (Method m : c.getDeclaredMethods()) {
      System.out.print(" - Name: " + m.getName());
      System.out.print(", Modifiers: " + Modifier.toString(m.getModifiers()));
      System.out.print(", Parameter Types: ");
      for (Class<?> p : m.getParameterTypes()) {
        System.out.print(p.getName() + " ");
      }
      System.out.print(", Return Type: " + m.getReturnType().getName());
      System.out.print(", Exceptions: ");
      for (Class<?> e : m.getExceptionTypes()) {
        System.out.print(e.getName() + " ");
      }
      System.out.println();
    }

    System.out.println("-----------------------------");

    System.out.println("Constructor Meta Information:");
    for (Constructor<?> constructor : c.getDeclaredConstructors()) {
      System.out.print(" - Name: " + constructor.getName());
      System.out.print(", Modifiers: " + Modifier.toString(constructor.getModifiers()));
      System.out.print(", Parameter Types: ");
      for (Class<?> p : constructor.getParameterTypes()) {
        System.out.print(p.getName() + " ");
      }
      System.out.print(", Exceptions: ");
      for (Class<?> e : constructor.getExceptionTypes()) {
        System.out.print(e.getName() + " ");
      }
      System.out.println();
    }

    System.out.println("-----------------------------");
  }
}

package p8Functional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class DEmployee {
  String name;
  int salary;
  int experience;

  public DEmployee(String name, int salary, int experience) {
    this.name = name;
    this.salary = salary;
    this.experience = experience;
  }
}

public class ConsumerSupplierDemo {
  static int counter = 0;

  public static void main(String[] args) {
    /*
     * Same requirements as FunctionDemo
     * Using Function<T, R> to get bonus of an employee
     * - based on salary & experience conditional checks
     * Using a Predicate<T> to filter employees if bonus > 1500
     * --------------------
     * Use Supplier<T> to create the list of employees
     * Use Consumer<T> to print the details of employees
     */

    Function<DEmployee, Double> bonus = (emp) -> {
      if (emp.experience > 5 && emp.salary > 50000) {
        return 0.1 * emp.salary;
      } else if (emp.experience > 5) {
        return 0.05 * emp.salary;
      } else if (emp.salary > 50000) {
        return 0.02 * emp.salary;
      } else {
        return 0.01 * emp.salary;
      }
    };

    Predicate<Double> filter = (num) -> num > 1500;

    String[] employeeNames = { "John", "Mary", "Mike", "Jane", "Sarah" };
    Supplier<DEmployee> supplier = () -> new DEmployee(employeeNames[counter++], (int) (Math.random() * 100000),
        (int) (Math.random() * 10));

    Consumer<DEmployee> consumer = (emp) -> {
      System.out.println(emp.name + " - " + emp.salary + " - " + emp.experience + " - " + bonus.apply(emp));
    };
    // Consumers can be chained using andThen(after)
    // c1.andThen(c2).andThen(c3).andThen(c4)...

    DEmployee[] employees = new DEmployee[5];
    for (int i = 0; i < employees.length; i++) {
      employees[i] = supplier.get();
      if (filter.test(bonus.apply(employees[i]))) {
        consumer.accept(employees[i]);
      }
    }
  }
}

package p8Functional;

import java.util.function.Function;
import java.util.function.Predicate;

class CEmployee {
  String name;
  int salary;
  int experience;

  public CEmployee(String name, int salary, int experience) {
    this.name = name;
    this.salary = salary;
    this.experience = experience;
  }
}

public class FunctionDemo {
  public static void main(String[] args) {
    /*
     * Using Function<T, R> to get factorial of a number n
     */
    Function<Integer, Integer> factorial = (n) -> {
      int result = 1;
      for (int i = 2; i <= n; i++) {
        result *= i;
      }
      return result;
    };
    System.out.println(factorial.apply(0));
    System.out.println(factorial.apply(1));
    System.out.println(factorial.apply(2));
    System.out.println(factorial.apply(5));
    System.out.println(factorial.apply(10));

    System.out.println("=====================================");

    /*
     * Using Function<T, R> to get bonus of an employee
     * - based on salary & experience conditional checks
     * Using a Predicate<T> to filter employees if bonus > 1500
     */
    Function<CEmployee, Double> bonus = (emp) -> {
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

    CEmployee[] employees = {
        new CEmployee("John", 40000, 10),
        new CEmployee("Mary", 60000, 2),
        new CEmployee("Jack", 30000, 1),
        new CEmployee("Mike", 70000, 12)
    };
    for (CEmployee emp : employees) {
      double bonusVal = bonus.apply(emp);
      if (filter.test(bonusVal)) {
        System.out.println(emp.name + " " + emp.salary + " " + bonusVal);
      }
    }

    System.out.println("=====================================");

    /*
     * Function Chaining: andThen() & compose()
     */
    Function<Integer, Integer> f1 = (n) -> n * n;
    Function<Integer, Integer> f2 = (n) -> n + 1;

    System.out.println(f1.andThen(f2).apply(5));
    /*
     * andThen() - ((5 * 5) + 1) = 26
     * - andThen() applies f1 first and then f2
     * compose() - ((5 + 1) * (5 + 1)) = 36
     * - compose() applies f2 first and then f1
     */
    System.out.println(f1.compose(f2).apply(5));

  }
}

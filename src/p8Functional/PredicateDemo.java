package p8Functional;

import java.util.function.Predicate;

class Employee {
  String name;
  int salary;
  int experience;

  public Employee(String name, int salary, int experience) {
    this.name = name;
    this.salary = salary;
    this.experience = experience;
  }
}

public class PredicateDemo {
  public static void main(String[] args) {
    /*
     * List the employees who have more than 5 years of experience
     * and salary is greater than 50000 - print the name
     */

    Employee[] employees = {
        new Employee("John", 40000, 10),
        new Employee("Mary", 60000, 2),
        new Employee("Mike", 70000, 12),
        new Employee("Jane", 55000, 6)
    };

    // Both conditional checks in one predicate
    Predicate<Employee> pr = emp -> emp.experience > 5 && emp.salary > 50000;
    for (Employee emp : employees) {
      if (pr.test(emp)) {
        System.out.println(emp.name);
      }
    }

    System.out.println("=====================================");

    // Two predicates & joining using and() - default method in Predicate<T>
    Predicate<Employee> p1 = emp -> emp.experience > 5;
    Predicate<Employee> p2 = emp -> emp.salary > 50000;
    // p2 could be based on any other type - independent of p1
    for (Employee emp : employees) {
      if (p1.and(p2).test(emp)) {
        System.out.println(emp.name);
      }
    }
  }
}

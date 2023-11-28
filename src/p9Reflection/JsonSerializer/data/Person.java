package p9Reflection.JsonSerializer.data;

import java.util.Arrays;

public class Person {
  private final String name;
  private final boolean employed;
  private final int age;
  private final float salary;
  private final Address address;
  private final Relative[] relatives;
  private final Job job;

  public Person(String name, boolean employed, int age, float salary, Address address, Relative[] relatives, Job job) {
    this.name = name;
    this.employed = employed;
    this.age = age;
    this.salary = salary;
    this.address = address;
    this.relatives = relatives;
    this.job = job;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", employed=" + employed +
        ", age=" + age +
        ", salary=" + salary +
        ", address=" + address +
        ", relatives=" + Arrays.toString(relatives) +
        ", job=" + job +
        '}';
  }
}

package p9Reflection.JsonSerializer.data;

import java.util.Arrays;

public class Relative {
  private final String name;
  private final int age;
  private final String relation;
  private final double[]heightWeight;

  public Relative(String name, int age, String relation, double[]heightWeight) {
    this.name = name;
    this.age = age;
    this.relation = relation;
    this.heightWeight = heightWeight;
  }

  @Override
  public String toString() {
    return "Relative{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", relation='" + relation + '\'' +
        ", heightWeight=" + Arrays.toString(heightWeight) +
        '}';
  }
}

package p5Files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationDeserialization {
  public static class Person implements Serializable {
    private String name;
    private int age;
    private double height;
    private double weight;

    public Person(String name, int age, double height, double weight) {
      this.name = name;
      this.age = age;
      this.height = height;
      this.weight = weight;
    }

    @Override
    public String toString() {
      return "Person{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", height=" + height +
          ", weight=" + weight +
          '}';
    }
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    String file;
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      file = br.readLine();
    }
    // Serialize the Person Object
    Person person = new Person("John", 30, 1.75, 80.0);
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
      oos.writeObject(person);      
      System.out.println("Serialized data is saved in " + file);
      System.out.println("Object 1: " + person);
    }

    // Deserialize the Person Object
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
      System.out.println("Object 2: " + (Person) ois.readObject());
    }
  }
}

package p9Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class Address {
  String street;
  String city;
  String state;

  public Address(String street, String city, String state) {
    this.street = street;
    this.city = city;
    this.state = state;
  }

  @Override
  public String toString() {
    return "Address{" +
        "street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        '}';
  }
}

class Person {
  String name;
  int age;
  Address address;

  public Person() {
    this.name = null;
    this.age = 0;
    this.address = null;
  }

  public Person(String name) {
    this.name = name;
    this.age = 0;
    this.address = null;
  }

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
    this.address = null;
  }

  public Person(String name, int age, Address address) {
    this.name = name;
    this.age = age;
    this.address = address;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", address=" + address +
        '}';
  }
}

public class GenericFactoryMethod {
  public static <T> T create(Class<T> inputClass, Object... args)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    for (Constructor<?> constructor : inputClass.getConstructors()) {
      if (constructor.getParameterCount() == args.length) {
        constructor.setAccessible(true);
        return inputClass.cast(constructor.newInstance(args));
      }
    }
    System.out.println("Constructor not found");
    return null;
  }

  public static void main(String[] args)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Person personWithZeroArgs = create(Person.class);
    System.out.println(personWithZeroArgs);

    Person personWithOneArg = create(Person.class, "Siddhant");
    System.out.println(personWithOneArg);

    Person personWithTwoArgs = create(Person.class, "Siddhant", 21);
    System.out.println(personWithTwoArgs);

    Person personWithThreeArgs = create(Person.class, "Siddhant", 21, new Address("123 Main Street", "New York", "NY"));
    System.out.println(personWithThreeArgs);
  }
}

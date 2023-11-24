package p9Reflection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem: Finding all Implemented Interfaces of a class
 * Returns all the interfaces that the current input class implements.
 * Note: If the input is an interface itself, the method returns all the
 * interfaces the input interface extends.
 */

interface E {}
interface F {}
interface G {}
interface B extends E {}
interface C extends F {}
interface D extends F, G {}

class A implements B, C, D {}

public class FiboReflection {
  public static Set<Class<?>> findAllImplementedInterfaces(Class<?> input) {
    Set<Class<?>> allImplementedInterfaces = new HashSet<>();
    Class<?>[] inputInterfaces = input.getInterfaces();
    for (Class<?> currentInterface : inputInterfaces) {
      allImplementedInterfaces.add(currentInterface);
      allImplementedInterfaces.addAll(findAllImplementedInterfaces(currentInterface));
    }
    return allImplementedInterfaces;
  }
  
  public static void main(String[] args) {
    System.out.println(findAllImplementedInterfaces(A.class));
    System.out.println(findAllImplementedInterfaces(B.class));
    System.out.println(findAllImplementedInterfaces(ArrayList.class));
  }
}

package p9Reflection.JsonSerializer.jsonwriter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import p9Reflection.JsonSerializer.data.Address;
import p9Reflection.JsonSerializer.data.Job;
import p9Reflection.JsonSerializer.data.Person;
import p9Reflection.JsonSerializer.data.Relative;

public class MainApp {
  public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
    Address address = new Address("123 Main Street", (short) 123);
    Address companyAddress = new Address("456 Main Street", (short) 456);
    double[][] heightWeight = new double[][] {{1.8, 80}, {1.75, 75}, {1.5, 50}};
    Relative[] relatives = new Relative[] {
        new Relative("Mary", 25, "Wife", heightWeight[0]),
        new Relative("Jane", 5, "Daughter", heightWeight[1]),
        new Relative("John Jr.", 10, "Son", heightWeight[2]),
    };
    Job job = new Job("Software Developer", "Google", companyAddress);
    Person person = new Person("John", true, 30, 45000.50f, address, relatives, job);

    String json = objectToJson(person, 0);
    System.out.println(json);
  }

  public static String objectToJson(Object instance, int indentSize)
      throws IllegalArgumentException, IllegalAccessException {
    Field[] fields = instance.getClass().getDeclaredFields();
    StringBuilder sb = new StringBuilder();
    sb.append(indent(indentSize));
    sb.append("{");
    sb.append("\n");
    for (int i = 0; i < fields.length; i++) {
      fields[i].setAccessible(true);
      if (fields[i].isSynthetic()) {
        continue;
      }
      sb.append(indent(indentSize + 1));
      sb.append(formatStringValue(fields[i].getName()));
      sb.append(":");
      if (fields[i].getType().isPrimitive()) {
        sb.append(formatPrimitiveValue(fields[i].get(instance), fields[i].getType()));
      } else if (fields[i].getType().equals(String.class)) {
        sb.append(formatStringValue(fields[i].get(instance).toString()));
      } else if (fields[i].getType().isArray()) {
        sb.append(formatArrayValues(fields[i].get(instance), indentSize + 1));
      } else {
        sb.append(objectToJson(fields[i].get(instance), indentSize + 1));
      }
      if (i != fields.length - 1) {
        sb.append(",");
      }
      sb.append("\n");
    }
    sb.append(indent(indentSize));
    sb.append("}");
    return sb.toString();
  }

  private static String indent(int indentSize) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < indentSize; i++) {
      sb.append("  ");
    }
    return sb.toString();
  }

  private static String formatArrayValues(Object array, int indentSize) throws IllegalArgumentException, IllegalAccessException {
    int arrayLength = Array.getLength(array);
    Class<?> componentType = array.getClass().getComponentType();
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    sb.append("\n");
    for (int i = 0; i < arrayLength; i++) {
      Object value = Array.get(array, i);
      if (componentType.isPrimitive()) {
        sb.append(indent(indentSize + 1));
        sb.append(formatPrimitiveValue(value, componentType));
      } else if (componentType.equals(String.class)) {
        sb.append(indent(indentSize + 1));
        sb.append(formatStringValue(value.toString()));
      } else {
        sb.append(objectToJson(value, indentSize + 1));
      }
      if (i != arrayLength - 1) {
        sb.append(",");
      }
      sb.append("\n");
    }
    sb.append(indent(indentSize));
    sb.append("]");
    return sb.toString();
  }

  private static String formatPrimitiveValue(Object instance, Class<?> type)
      throws IllegalArgumentException, IllegalAccessException {
    if (type.equals(boolean.class)
        || type.equals(int.class)
        || type.equals(long.class)
        || type.equals(short.class)) {
      return instance.toString();
    } else if (type.equals(float.class)
        || type.equals(double.class)) {
      return String.format("%.2f", instance);
    }
    throw new IllegalArgumentException("Unsupported primitive type: " + type.getName());
  }

  private static String formatStringValue(String value) {
    return String.format("\"%s\"", value);
  }
}

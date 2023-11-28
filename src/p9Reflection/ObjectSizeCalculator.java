package p9Reflection;

import java.lang.reflect.Field;

class AccountSummary {
  private final String firstName;
  private final String lastName;
  private final short age;
  private final int salary;

  public AccountSummary(String firstName, String lastName, short age, int salary) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.salary = salary;
  }

  @Override
  public String toString() {
    return "AccountSummary [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", salary=" + salary
        + "]";
  }
}

class Empty {
}

public class ObjectSizeCalculator {
  private static final long HEADER_SIZE = 12;
  private static final long REFERENCE_SIZE = 4;

  public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
    AccountSummary accountSummary = new AccountSummary("John", "Smith", (short) 25, 50000);
    ObjectSizeCalculator objectSizeCalculator = new ObjectSizeCalculator();
    long size1 = objectSizeCalculator.sizeOfObject(accountSummary);
    System.out.println(size1);
    long size2 = objectSizeCalculator.sizeOfObject(new Empty());
    System.out.println(size2);
  }

  public long sizeOfObject(Object input) {
    if (input == null) {
      return 0;
    }
    Class<?> inputClass = input.getClass();
    Field[] fields = inputClass.getDeclaredFields();
    long totalSize = HEADER_SIZE + REFERENCE_SIZE;
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.isSynthetic()) {
        continue;
      }
      Class<?> fieldType = field.getType();
      if (fieldType.isPrimitive()) {
        totalSize += sizeOfPrimitiveType(fieldType);
      } else if (fieldType.equals(String.class)) {
        try {
          totalSize += sizeOfString(field.get(input).toString());
        } catch (IllegalArgumentException | IllegalAccessException e) {
          e.printStackTrace();
        }
      } else {
        try {
          totalSize += sizeOfObject(field.get(input));
        } catch (IllegalArgumentException | IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return totalSize;
  }

  private long sizeOfPrimitiveType(Class<?> primitiveType) {
    if (primitiveType.equals(int.class)) {
      return 4;
    } else if (primitiveType.equals(long.class)) {
      return 8;
    } else if (primitiveType.equals(float.class)) {
      return 4;
    } else if (primitiveType.equals(double.class)) {
      return 8;
    } else if (primitiveType.equals(byte.class)) {
      return 1;
    } else if (primitiveType.equals(short.class)) {
      return 2;
    }
    throw new IllegalArgumentException(String.format("Type: %s is not supported", primitiveType));
  }

  private long sizeOfString(String inputString) {
    int stringBytesSize = inputString.getBytes().length;
    return HEADER_SIZE + REFERENCE_SIZE + stringBytesSize;
  }
}

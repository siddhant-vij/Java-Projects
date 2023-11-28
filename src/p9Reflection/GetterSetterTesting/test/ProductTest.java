package p9Reflection.GetterSetterTesting.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p9Reflection.GetterSetterTesting.data.ClothingProduct;

public class ProductTest {
  public static void main(String[] args) {
    testGetters(ClothingProduct.class);
    testSetters(ClothingProduct.class);
  }

  public static void testSetters(Class<?> dataClass) {
    List<Field> fields = getAllFields(dataClass);
    for (Field field : fields) {
      String setterName = "set" + capitalizeFirstLetter(field.getName());
      Method setter = null;
      try {
        setter = dataClass.getMethod(setterName, field.getType());
      } catch (NoSuchMethodException e) {
        throw new IllegalStateException(String.format(
            "Field %s has no setter",
            field.getName()));
      }
      if (!setter.getReturnType().equals(void.class)) {
        throw new IllegalStateException(String.format(
            "Setter %s does not return void",
            setterName));
      }
    }
  }

  public static void testGetters(Class<?> dataClass) {
    List<Field> fields = getAllFields(dataClass);
    Map<String, Method> methodNameToMethod = getMapOfMethodNameToMethod(dataClass);
    for (Field field : fields) {
      String getterName = "get" + capitalizeFirstLetter(field.getName());
      if (!methodNameToMethod.containsKey(getterName)) {
        throw new IllegalStateException(String.format("Field %s has no getter", field.getName()));
      }
      Method getter = methodNameToMethod.get(getterName);
      if (!getter.getReturnType().equals(field.getType())) {
        throw new IllegalStateException(String.format(
            "Getter %s does not return the same type as the field %s",
            getterName,
            field.getName()));
      }
      if (getter.getParameterCount() > 0) {
        throw new IllegalStateException(String.format(
            "Getter %s has %d parameters",
            getterName,
            getter.getParameterCount()));
      }
    }
  }

  private static List<Field> getAllFields(Class<?> dataClass) {
    if (dataClass == null || dataClass.equals(Object.class)) {
      return new ArrayList<>();
    }
    Field[] fields = dataClass.getDeclaredFields();
    List<Field> inheritedFields = getAllFields(dataClass.getSuperclass());
    List<Field> allFields = new ArrayList<>(Arrays.asList(fields));
    allFields.addAll(inheritedFields);
    return allFields;
  }

  private static String capitalizeFirstLetter(String fieldName) {
    return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }

  private static Map<String, Method> getMapOfMethodNameToMethod(Class<?> dataClass) {
    Map<String, Method> methodNameToMethod = new HashMap<>();
    for (Method method : dataClass.getMethods()) {
      methodNameToMethod.put(method.getName(), method);
    }
    return methodNameToMethod;
  }
}

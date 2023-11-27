package p9Reflection.IDEInfoPlugin;

import java.util.Arrays;
import java.util.List;

enum Gender {
  MALE, FEMALE
}

class Product {
}

interface IProduct {
}

public class ClassAnalyzer {
  private static final List<String> JDK_PACKAGE_PREFIXES = Arrays.asList("com.sun.", "java", "javax", "jdk", "org.w3c", "org.xml");

  public static PopupTypeInfo createPopupTypeInfoFromClass(Class<?> inputClass) {
    PopupTypeInfo popupTypeInfo = new PopupTypeInfo();
    popupTypeInfo.setPrimitive(inputClass.isPrimitive())
        .setInterface(inputClass.isInterface())
        .setEnum(inputClass.isEnum())
        .setName(inputClass.getSimpleName())
        .setJdk(isJdkClass(inputClass));
    return popupTypeInfo;
  }

  public static boolean isJdkClass(Class<?> inputClass) {
    String packageName = inputClass.getPackageName();
    return JDK_PACKAGE_PREFIXES.stream()
        .anyMatch(packagePrefix -> packageName.startsWith(packagePrefix));
  }

  public static void main(String[] args) {
    System.out.println(createPopupTypeInfoFromClass(String.class));
    System.out.println(createPopupTypeInfoFromClass(List.class));
    System.out.println(createPopupTypeInfoFromClass(int.class));
    System.out.println(createPopupTypeInfoFromClass(Gender.class));
    System.out.println(createPopupTypeInfoFromClass(Product.class));
    System.out.println(createPopupTypeInfoFromClass(IProduct.class));
  }
}


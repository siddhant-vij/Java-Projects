package p9Reflection;

import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno {
  String name();
  int value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface What {
  String description();  
}

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnoWithDefault {
  String name() default "test";
  int value() default 3000;  
}

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MyRepeatedAnno.class)
@interface MyAnnoWithRepeatable {
  String name() default "test2";
  int value() default 4000;
}

@Retention(RetentionPolicy.RUNTIME)
@interface MyRepeatedAnno {
  MyAnnoWithRepeatable[] value();
}

class MyClassBasic {
  @MyAnno(name = "name1", value = 1)
  public static void myMethodWithoutArgs() {
  }

  @MyAnno(name = "name2", value = 2)
  public static void myMethodWithArgs(String s, int i) {
  }
}

@MyAnno(name = "name3", value = 3)
@What(description = "description3")
class MyClassMultipleAnno {
  @MyAnno(name = "name4", value = 4)
  @What(description = "description4")
  public static void myMethodMultipleAnno() {
  }  
}

class MyClassWithDefault1 {
  @MyAnnoWithDefault()
  public static void myMethodWithDefault() {
  }
}
class MyClassWithDefault2 {
  @MyAnnoWithDefault(value = 5)
  public static void myMethodWithDefault() {
  }
}
class MyClassWithDefault3 {
  @MyAnnoWithDefault(name = "name6")
  public static void myMethodWithDefault() {
  }
}
class MyClassWithDefault4 {
  @MyAnnoWithDefault(name = "name7", value = 7)
  public static void myMethodWithDefault() {
  }
}

class MyClassWithRepeatable {
  @MyAnnoWithRepeatable(name = "name8", value = 8)
  @MyAnnoWithRepeatable(name = "name9", value = 9)
  @MyAnnoWithRepeatable(name = "name10", value = 10)
  public static void myMethodWithRepeatable() {
  }
}

public class AnnotationsDemo {
  public static void tempTestBasic() {
    MyClassBasic myClass = new MyClassBasic();
    try {
      Class<?> myClassObj = myClass.getClass();

      // Without Arguments
      Method m1 = myClassObj.getMethod("myMethodWithoutArgs");
      MyAnno anno = m1.getAnnotation(MyAnno.class);
      System.out.println(anno.name() + " -> " + anno.value());
      System.out.println("-----------------");

      // With Arguments - Parameter Types
      Method m2 = myClassObj.getMethod("myMethodWithArgs", String.class, int.class);
      anno = m2.getAnnotation(MyAnno.class);
      System.out.println(anno.name() + " -> " + anno.value());
    } catch (NoSuchMethodException e) {
      System.out.println("Method not found");
    }
  }

  public static void tempTestMultipleAnno() {
    MyClassMultipleAnno myClass = new MyClassMultipleAnno();
    try {
      Class<?> myClassObj = myClass.getClass();
      Annotation[] annotations = myClassObj.getAnnotations();
      for (Annotation annotation : annotations) {
        System.out.println(annotation);
      }
      System.out.println("-----------------");

      annotations = myClassObj.getMethod("myMethodMultipleAnno").getAnnotations();
      for (Annotation annotation : annotations) {
        System.out.println(annotation);
      }
    } catch (NoSuchMethodException e) {
      System.out.println("Method not found");
    }
  }

  public static void tempTestWithDefault() {
    MyClassWithDefault1 myClass1 = new MyClassWithDefault1();
    MyClassWithDefault2 myClass2 = new MyClassWithDefault2();
    MyClassWithDefault3 myClass3 = new MyClassWithDefault3();
    MyClassWithDefault4 myClass4 = new MyClassWithDefault4();
    try {
      Class<?> myClassObj = myClass1.getClass();
      Method myMethod = myClassObj.getMethod("myMethodWithDefault");
      MyAnnoWithDefault annotation = myMethod.getAnnotation(MyAnnoWithDefault.class);
      System.out.println(annotation);
      System.out.println("-----------------");

      myClassObj = myClass2.getClass();
      myMethod = myClassObj.getMethod("myMethodWithDefault");
      annotation = myMethod.getAnnotation(MyAnnoWithDefault.class);
      System.out.println(annotation);
      System.out.println("-----------------");

      myClassObj = myClass3.getClass();
      myMethod = myClassObj.getMethod("myMethodWithDefault");
      annotation = myMethod.getAnnotation(MyAnnoWithDefault.class);
      System.out.println(annotation);
      System.out.println("-----------------");

      myClassObj = myClass4.getClass();
      myMethod = myClassObj.getMethod("myMethodWithDefault");
      annotation = myMethod.getAnnotation(MyAnnoWithDefault.class);
      System.out.println(annotation);
    } catch (NoSuchMethodException e) {
      System.out.println("Method not found");
    }
  }

  public static void tempTestWithRepeatable() {
    MyClassWithRepeatable myClass = new MyClassWithRepeatable();
    try {
      Class<?> myClassObj = myClass.getClass();
      Method myMethod = myClassObj.getMethod("myMethodWithRepeatable");
      Annotation annotation = myMethod.getAnnotation(MyRepeatedAnno.class);
      System.out.println(annotation);
      System.out.println("-----------------");

      Annotation[] annotations = myMethod.getAnnotationsByType(MyAnnoWithRepeatable.class);
      for (Annotation anno : annotations) {
        System.out.println(anno);
      }
    } catch (NoSuchMethodException e) {
      System.out.println("Method not found");
    }
  }

  public static void main(String[] args) {
    tempTestBasic();
    System.out.println("-----------------");
    tempTestMultipleAnno();
    System.out.println("-----------------");
    tempTestWithDefault();
    System.out.println("-----------------");
    tempTestWithRepeatable();
  }
}

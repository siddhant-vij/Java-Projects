package p9Reflection.AnnotationDiscovery.app.databases;

import p9Reflection.AnnotationDiscovery.annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.InitializerMethod;

@InitializerClass
public class DatabaseConnection {
  @InitializerMethod
  public void connectToDatabase1() {
    System.out.println("Connecting to database 1");
  }

  @InitializerMethod
  public void connectToDatabase2() {
    System.out.println("Connecting to database 2");
  }
}

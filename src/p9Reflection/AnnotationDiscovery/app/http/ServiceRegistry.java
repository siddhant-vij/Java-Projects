package p9Reflection.AnnotationDiscovery.app.http;

import p9Reflection.AnnotationDiscovery.annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.InitializerMethod;

@InitializerClass
public class ServiceRegistry {
  @InitializerMethod
  public void registerService() {
    System.out.println("Service successfully registered");
  }
}

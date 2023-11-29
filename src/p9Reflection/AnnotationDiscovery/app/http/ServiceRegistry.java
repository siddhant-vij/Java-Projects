package p9Reflection.AnnotationDiscovery.app.http;

import p9Reflection.AnnotationDiscovery.annotations.Annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.Annotations.InitializerMethod;

@InitializerClass
public class ServiceRegistry {
  @InitializerMethod
  public void registerService() {
    System.out.println("Service successfully registered");
  }
}

package p9Reflection.AnnotationDiscovery.app.databases;

import p9Reflection.AnnotationDiscovery.annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.InitializerMethod;

@InitializerClass
public class CacheLoader {
  @InitializerMethod
  public void loadCache() {
    System.out.println("Loading data from cache");
  }

  public void reloadCache() {
    System.out.println("Reloading cache");
  }
}

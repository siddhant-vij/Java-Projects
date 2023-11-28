package p9Reflection.AnnotationDiscovery.app;

import p9Reflection.AnnotationDiscovery.annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.InitializerMethod;

@InitializerClass
public class AutoSaver {
  @InitializerMethod
  public void startAutoSavingThreads() {
    System.out.println("Start auto data saving to disk");
  }
}

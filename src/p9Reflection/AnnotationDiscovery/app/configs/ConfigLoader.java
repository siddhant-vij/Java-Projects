package p9Reflection.AnnotationDiscovery.app.configs;

import p9Reflection.AnnotationDiscovery.annotations.Annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.Annotations.InitializerMethod;

@InitializerClass
public class ConfigLoader {
  @InitializerMethod
  public void loadAllConfigs() {
    System.out.println("Loading all configuration files");
  }
}

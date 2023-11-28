package p9Reflection.AnnotationDiscovery.app.configs;

import p9Reflection.AnnotationDiscovery.annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.InitializerMethod;

@InitializerClass
public class ConfigLoader {
  @InitializerMethod
  public void loadAllConfigs() {
    System.out.println("Loading all configuration files");
  }
}

package p9Reflection.AnnotationDiscovery.app.databases;

import p9Reflection.AnnotationDiscovery.annotations.Annotations.ExecuteOnSchedule;
import p9Reflection.AnnotationDiscovery.annotations.Annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.Annotations.InitializerMethod;
import p9Reflection.AnnotationDiscovery.annotations.Annotations.ScheduledExecutorClass;

@InitializerClass
@ScheduledExecutorClass
public class CacheLoader {
  @InitializerMethod
  public void loadCache() {
    System.out.println("Loading data from cache");
  }

  @ExecuteOnSchedule(periodSeconds = 5)
  @ExecuteOnSchedule(periodSeconds = 1, delaySeconds = 10)
  public static void reloadCache() {
  }
}

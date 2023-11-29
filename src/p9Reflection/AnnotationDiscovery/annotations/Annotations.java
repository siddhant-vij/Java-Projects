package p9Reflection.AnnotationDiscovery.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotations {
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface InitializerClass {
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface InitializerMethod {
  }

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface RetryOperation {
    Class<? extends Throwable>[] retryExceptions() default { Exception.class };
    long durationBetweenRetries() default 0;
    String failureMessage() default "Operation failed after retrying";
    int maxNumberOfRetries();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface ScanPackages {
    String[] value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface ScheduledExecutorClass {
  }

  @Repeatable(ExecuteOnSchedules.class)
  @Target(ElementType.METHOD)
  public @interface ExecuteOnSchedule {
    int delaySeconds() default 0;
    int periodSeconds();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface ExecuteOnSchedules {
    ExecuteOnSchedule[] value();
  }
}

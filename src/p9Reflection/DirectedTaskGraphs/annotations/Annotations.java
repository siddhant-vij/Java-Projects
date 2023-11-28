package p9Reflection.DirectedTaskGraphs.annotations;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Annotations {

  @Target({ ElementType.FIELD, ElementType.PARAMETER })
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Input {
    String value();
  }

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Operation {
    String value();
  }

  @Target(ElementType.PARAMETER)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface DependsOn {
    String value();
  }

  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface FinalResult {
  }
}

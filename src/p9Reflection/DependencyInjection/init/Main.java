package p9Reflection.DependencyInjection.init;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import p9Reflection.DependencyInjection.game.Game;

public class Main {
  public static Constructor<?> getFirstConstructor(Class<?> clazz) {
    /*
     * Assumption: Class has at most one constructor
     * - Either explicitly declared or default constructor
     * - This method can be expanded to support multiple constructors
     */
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    if (constructors.length == 0) {
      throw new IllegalArgumentException("Class has no constructors");
    }
    return constructors[0];
  }

  public static <T> T createInstanceRecursively(Class<T> clazz)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Constructor<?> constructor = getFirstConstructor(clazz);
    List<Object> constructorArgs = new ArrayList<>();
    for (Class<?> parameterType : constructor.getParameterTypes()) {
      constructorArgs.add(createInstanceRecursively(parameterType));
    }
    constructor.setAccessible(true);
    return clazz.cast(constructor.newInstance(constructorArgs.toArray()));
  }

  public static void main(String[] args)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Game game = createInstanceRecursively(Game.class);
    System.out.println(game);
  }
}

package p9Reflection.DirectedTaskGraphs;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import p9Reflection.DirectedTaskGraphs.annotations.Annotations.DependsOn;
import p9Reflection.DirectedTaskGraphs.annotations.Annotations.FinalResult;
import p9Reflection.DirectedTaskGraphs.annotations.Annotations.Input;
import p9Reflection.DirectedTaskGraphs.annotations.Annotations.Operation;

public class Main {
  public static void main(String[] args)
      throws IllegalAccessException, InvocationTargetException {
    topGamesInOrder();
    System.out.println("-------------------------");
    BestGamesFinder finder = new BestGamesFinder();
    List<String> topGames = execute(finder);
    System.out.println(topGames);
    System.out.println("-------------------------");
    SqlQueryBuilder builder = new SqlQueryBuilder(
        Arrays.asList("1", "2", "3"),
        10,
        "Movies",
        Arrays.asList("Id", "Name"));
    String sqlQuery = execute(builder);
    System.out.println(sqlQuery);
  }

  public static void topGamesInOrder() {
    BestGamesFinder finder = new BestGamesFinder();
    Set<String> allGames = finder.getAllGames();
    Map<String, Float> gameToRating = finder.getGameToRating(allGames);
    Map<String, Float> gameToPrice = finder.getGameToPrice(allGames);
    SortedMap<Double, String> scoreToGame = finder.scoreGames(gameToPrice, gameToRating);
    List<String> topGames = finder.getTopGames(scoreToGame);
    System.out.println(topGames);
  }

  public static <T> T execute(Object instance) throws IllegalAccessException, InvocationTargetException {
    Class<?> clazz = instance.getClass();
    Map<String, Method> operationNameToMethod = getOperationToMethod(clazz);
    Map<String, Field> inputNameToField = getInputToField(clazz);
    Method finalResultMethod = findFinalResultMethod(clazz);
    @SuppressWarnings("unchecked")
    T t = (T) executeWithDependencies(instance, finalResultMethod, operationNameToMethod, inputNameToField);
    return t;
  }

  private static Object executeWithDependencies(Object instance, Method currentMethod,
      Map<String, Method> operationToMethod, Map<String, Field> inputToField)
      throws IllegalAccessException, InvocationTargetException {
    List<Object> parameterValues = new ArrayList<>(currentMethod.getParameterCount());
    for (Parameter parameter : currentMethod.getParameters()) {
      Object value = null;
      if (parameter.isAnnotationPresent(DependsOn.class)) {
        String operationName = parameter.getAnnotation(DependsOn.class).value();
        Method method = operationToMethod.get(operationName);
        value = executeWithDependencies(instance, method, operationToMethod, inputToField);
      } else if (parameter.isAnnotationPresent(Input.class)) {
        String inputName = parameter.getAnnotation(Input.class).value();
        Field field = inputToField.get(inputName);
        field.setAccessible(true);
        value = field.get(instance);
      }
      parameterValues.add(value);
    }
    return currentMethod.invoke(instance, parameterValues.toArray());
  }

  private static Map<String, Method> getOperationToMethod(Class<?> clazz) {
    Map<String, Method> operationNameToMethod = new HashMap<>();
    for (Method method : clazz.getDeclaredMethods()) {
      if (!method.isAnnotationPresent(Operation.class)) {
        continue;
      }
      Operation operation = method.getAnnotation(Operation.class);
      operationNameToMethod.put(operation.value(), method);
    }
    return operationNameToMethod;
  }

  private static Map<String, Field> getInputToField(Class<?> clazz) {
    Map<String, Field> inputNameToField = new HashMap<>();
    for (Field field : clazz.getDeclaredFields()) {
      if (!field.isAnnotationPresent(Input.class)) {
        continue;
      }
      Input input = field.getAnnotation(Input.class);
      inputNameToField.put(input.value(), field);
    }
    return inputNameToField;
  }

  private static Method findFinalResultMethod(Class<?> clazz) {
    for (Method method : clazz.getDeclaredMethods()) {
      if (method.isAnnotationPresent(FinalResult.class)) {
        return method;
      }
    }
    throw new RuntimeException("No @FinalResult method found");
  }
}

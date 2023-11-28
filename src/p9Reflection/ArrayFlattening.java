package p9Reflection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayFlattening {
  public static void main(String[] args) {
    Integer[] result1 = concat(Integer.class, 1, 2, 3, 4, 5);
    System.out.println(Arrays.toString(result1));

    int[] result2 = concat(int.class, 1, 2, 3, new int[] { 4, 5, 6 }, 7);
    System.out.println(Arrays.toString(result2));

    String[] result = concat(String.class, new String[] { "a", "b" }, "c", new String[] { "d", "e" });
    System.out.println(Arrays.toString(result));
  }

  public static <T> T concat(Class<?> type, Object... arguments) {
    if (arguments.length == 0) {
      return null;
    }
    List<Object> result = new ArrayList<>();
    for (Object arg : arguments) {
      if (arg.getClass().isArray()) {
        int length = Array.getLength(arg);
        for (int i = 0; i < length; i++) {
          result.add(Array.get(arg, i));
        }
      } else {
        result.add(arg);
      }
    }
    Object flattenedArray = Array.newInstance(type, result.size());
    for (int i = 0; i < result.size(); i++) {
      Array.set(flattenedArray, i, result.get(i));
    }
    @SuppressWarnings("unchecked")
    T castArray = (T) flattenedArray;
    return castArray;
  }
}

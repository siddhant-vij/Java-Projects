package p9Reflection.ConfigFileParser.configloader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import p9Reflection.ConfigFileParser.data.GameConfig;
import p9Reflection.ConfigFileParser.data.UserInterfaceConfig;

public class MainApp {
  private static final Path GAME_CONFIG_FILE = Path
      .of("src/p9Reflection/ConfigFileParser/resources/game-properties.cfg");
  private static final Path UI_CONFIG_FILE = Path
      .of("src/p9Reflection/ConfigFileParser/resources/user-interface.cfg");

  public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, FileNotFoundException,
      NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException, IOException {
    GameConfig gameConfig = createConfigObject(GameConfig.class, GAME_CONFIG_FILE);
    UserInterfaceConfig userInterfaceConfig = createConfigObject(UserInterfaceConfig.class, UI_CONFIG_FILE);
    System.out.println(gameConfig);
    System.out.println("-------------------------");
    System.out.println(userInterfaceConfig);
  }

  public static <T> T createConfigObject(Class<T> configClass, Path filePath)
      throws IllegalArgumentException, IllegalAccessException, FileNotFoundException, IOException,
      NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
      Constructor<?> constructor = configClass.getDeclaredConstructor();
      constructor.setAccessible(true);
      T configInstance = configClass.cast(constructor.newInstance());
      while (reader.ready()) {
        String line = reader.readLine();
        String[] nameValuePair = line.split("=");
        String propertyName = nameValuePair[0];
        String propertyValue = nameValuePair[1];

        Field field = null;
        try {
          field = configClass.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
          System.out.println("Field not found: " + propertyName);
          e.printStackTrace();
          continue;
        }

        field.setAccessible(true);

        Object parsedValue;
        if (field.getType().isArray()) {
          parsedValue = parseArray(field.getType().getComponentType(), propertyValue);
        } else {
          parsedValue = parseValue(field.getType(), propertyValue);
        }
        field.set(configInstance, parsedValue);
      }
      return configInstance;
    }
  }

  private static Object parseArray(Class<?> arrayElementType, Object value) {
    String[] values = value.toString().split(",");
    Object array = Array.newInstance(arrayElementType, values.length);
    for (int i = 0; i < values.length; i++) {
      Object parsedValue = parseValue(arrayElementType, values[i]);
      Array.set(array, i, parsedValue);
    }
    return array;
  }

  private static Object parseValue(Class<?> type, String propertyValue) {
    if (type.equals(byte.class)) {
      return Byte.parseByte(propertyValue);
    } else if (type.equals(short.class)) {
      return Short.parseShort(propertyValue);
    } else if (type.equals(int.class)) {
      return Integer.parseInt(propertyValue);
    } else if (type.equals(long.class)) {
      return Long.parseLong(propertyValue);
    } else if (type.equals(float.class)) {
      return Float.parseFloat(propertyValue);
    } else if (type.equals(double.class)) {
      return Double.parseDouble(propertyValue);
    } else if (type.equals(boolean.class)) {
      return Boolean.parseBoolean(propertyValue);
    } else if (type.equals(String.class)) {
      return propertyValue;
    }
    throw new IllegalArgumentException("Unsupported type: " + type.getName());
  }
}

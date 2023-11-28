package p9Reflection.AnnotationDiscovery;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import p9Reflection.AnnotationDiscovery.annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.InitializerMethod;
import p9Reflection.AnnotationDiscovery.annotations.RetryOperation;
import p9Reflection.AnnotationDiscovery.annotations.ScanPackages;

@ScanPackages({ "p9Reflection.AnnotationDiscovery.app",
    "p9Reflection.AnnotationDiscovery.app.configs",
    "p9Reflection.AnnotationDiscovery.app.databases",
    "p9Reflection.AnnotationDiscovery.app.http" })
public class Main {
  public static void main(String[] args) throws Throwable {
    initialize();
  }

  public static void initialize() throws Throwable {
    ScanPackages scanPackages = Main.class.getAnnotation(ScanPackages.class);
    if (scanPackages == null || scanPackages.value().length == 0) {
      return;
    }
    List<Class<?>> classes = getAllClasses(scanPackages.value());
    for (Class<?> clazz : classes) {
      if (clazz.isAnnotationPresent(InitializerClass.class)) {
        List<Method> initializerMethods = getAllInitializerMethods(clazz);
        Object instance = clazz.getConstructor().newInstance();
        for (Method initializerMethod : initializerMethods) {
          callInitializingMethod(instance, initializerMethod);
        }
      }
    }
  }

  private static void callInitializingMethod(Object instance, Method initializerMethod) throws Throwable {
    RetryOperation retryOperation = initializerMethod.getAnnotation(RetryOperation.class);
    int numberOfRetries = retryOperation == null ? 0 : retryOperation.maxNumberOfRetries();
    while (true) {
      try {
        initializerMethod.invoke(instance);
        break;
      } catch (InvocationTargetException e) {
        Throwable targetException = e.getTargetException();
        if (numberOfRetries > 0 && Set.of(retryOperation.retryExceptions()).contains(targetException.getClass())) {
          numberOfRetries--;
          System.out.println("Failed to initialize. Retrying...");
          Thread.sleep(retryOperation.durationBetweenRetries());
        } else if (retryOperation != null) {
          throw new Exception(retryOperation.failureMessage(), targetException);
        } else {
          throw targetException;
        }
      }
    }
  }

  private static List<Class<?>> getAllClasses(String... packageNames)
      throws URISyntaxException, ClassNotFoundException, IOException {
    List<Class<?>> classes = new ArrayList<>();
    for (String packageName : packageNames) {
      String packageRelativePath = packageName.replace(".", "/");
      URI packageUri = Main.class.getClassLoader().getResource(packageRelativePath).toURI();
      if (packageUri.getScheme().equals("jar")) {
        FileSystem fs = FileSystems.newFileSystem(packageUri, Collections.emptyMap());
        classes.addAll(getAllPackageClasses(fs.getPath(packageRelativePath), packageName));
        fs.close();
      } else if (packageUri.getScheme().equals("file")) {
        classes.addAll(getAllPackageClasses(Path.of(packageUri), packageName));
      }
    }
    return classes;
  }

  private static List<Class<?>> getAllPackageClasses(Path packagePath, String packageName)
      throws IOException, ClassNotFoundException {
    if (!Files.exists(packagePath)) {
      return new ArrayList<>();
    }
    List<Path> paths = Files.list(packagePath)
        .filter(Files::isRegularFile)
        .toList();
    List<Class<?>> classes = new ArrayList<>();
    for (Path path : paths) {
      String fileName = path.getFileName().toString();
      if (!fileName.endsWith(".class")) {
        continue;
      }
      String className = packageName + "." + fileName.replace(".class", "");
      classes.add(Class.forName(className));
    }
    return classes;
  }

  private static List<Method> getAllInitializerMethods(Class<?> clazz) {
    List<Method> initializerMethods = new ArrayList<>();
    for (Method method : clazz.getMethods()) {
      if (method.isAnnotationPresent(InitializerMethod.class)) {
        initializerMethods.add(method);
      }
    }
    return initializerMethods;
  }
}

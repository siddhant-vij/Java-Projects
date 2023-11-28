package p9Reflection.AnnotationDiscovery.app.databases;

import java.io.IOException;

import p9Reflection.AnnotationDiscovery.annotations.InitializerClass;
import p9Reflection.AnnotationDiscovery.annotations.InitializerMethod;
import p9Reflection.AnnotationDiscovery.annotations.RetryOperation;

@InitializerClass
public class DatabaseConnection {
  private int failCounter = 5;

  @InitializerMethod
  @RetryOperation(
      retryExceptions = {IOException.class},
      maxNumberOfRetries = 5, // >= failCounter
      durationBetweenRetries = 1000,
      failureMessage = "Failed to connect to Database 1"
  )
  public void connectToDatabase1() throws IOException {
    System.out.println("Connecting to database 1");
    if (failCounter > 0) {
      failCounter--;
      throw new IOException("Failed to connect to database");
    }
    System.out.println("Successfully connected to database 1");
  }

  @InitializerMethod
  public void connectToDatabase2() {
    System.out.println("Connecting to database 2");
  }
}

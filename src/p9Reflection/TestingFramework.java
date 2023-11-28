package p9Reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestingFramework {
  public void runTestSuite(Class<?> testClass) throws Throwable {
    Method[] methods = testClass.getMethods();
    Method beforeClass = findMethodByName(methods, "beforeClass");
    Method afterClass = findMethodByName(methods, "afterClass");
    Method setupTest = findMethodByName(methods, "setupTest");
    List<Method> testMethods = findMethodsByPrefix(methods, "test");

    if (beforeClass != null) {
      beforeClass.invoke(null);
    }

    for (Method test : testMethods) {
      Object testInstance = testClass.getDeclaredConstructor().newInstance();
      if (setupTest != null) {
        setupTest.invoke(testInstance);
      }
      test.invoke(testInstance);
    }

    if (afterClass != null) {
      afterClass.invoke(null);
    }
  }

  private Method findMethodByName(Method[] methods, String name) {
    for (Method method : methods) {
      if (method.getName().equals(name)
          && method.getParameterCount() == 0
          && method.getReturnType().equals(void.class)) {
        return method;
      }
    }
    return null;
  }

  private List<Method> findMethodsByPrefix(Method[] methods, String prefix) {
    List<Method> matchingMethods = new ArrayList<>();
    for (Method method : methods) {
      if (method.getName().startsWith(prefix)
          && method.getParameterCount() == 0
          && method.getReturnType().equals(void.class)) {
        matchingMethods.add(method);
      }
    }
    return matchingMethods;
  }

  public static void main(String[] args) {
    TestingFramework framework = new TestingFramework();
    try {
      framework.runTestSuite(PaymentTest.class);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
}

class PaymentTest {
  public static void beforeClass() {
    // Called in the beginning of the test suite only once
    // Used for all tests need to share computationally expensive setup
    System.out.println("Before Class...");
  }

  public void setupTest() {
    // Called before every test
    // Used for setting up resource before every test
    System.out.println("Setup Test...");
  }

  public void testCreditCardPayment() {
    // Test case 1
    System.out.println("Test Credit Card Payment...");
  }

  public void testWireTransfer() {
    // Test case 2
    System.out.println("Test Wire Transfer...");
  }

  public void testInsufficientFunds() {
    // Test case 3
    System.out.println("Test Insufficient Funds...");
  }

  public static void afterClass() {
    // Called once in the end of the entire test suite
    // Used for closing and cleaning up common resources
    System.out.println("After Class...");
  }
}

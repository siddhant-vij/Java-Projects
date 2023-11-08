package p8Functional;

@FunctionalInterface
interface PerformOperation {
  boolean check(int a);
}

class MathTest {
  public static boolean checker(PerformOperation p, int num) {
    return p.check(num);
  }
}

class MathOperations {
  public boolean isOdd(int a) {
    return a % 2 != 0;
  }

  public boolean isPrime(int a) {
    if (a < 2) {
      return false;
    }
    for (int i = 2; i <= Math.sqrt(a); i++) {
      if (a % i == 0) {
        return false;
      }
    }
    return true;
  }

  public boolean isPalindrome(int a) {
    String str = Integer.toString(a);
    for (int i = 0; i < str.length() / 2; i++) {
      if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
        return false;
      }
    }
    return true;
  }
}

public class MethodReferenceBasics {
  public static void main(String[] args) {
    // Using Method References - instance methods
    MathOperations mathOperations = new MathOperations();
    PerformOperation isOdd = mathOperations::isOdd;
    PerformOperation isPrime = mathOperations::isPrime;
    PerformOperation isPalindrome = mathOperations::isPalindrome;
    int number = 5987895;
    System.out.println(MathTest.checker(isOdd, number));
    System.out.println(MathTest.checker(isPrime, number));
    System.out.println(MathTest.checker(isPalindrome, number));
  }
}

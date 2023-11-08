package p8Functional;

@FunctionalInterface
interface MyDoubleArrayInter {
  double func(double[] arr) throws EmptyArrayException;
}

class EmptyArrayException extends Exception {
  EmptyArrayException() {
    super("Empty Array!");
  }
}

public class LambdaException {
  public static void main(String[] args) {
    MyDoubleArrayInter myDoubleArrayInter1 = (arr) -> {
      if (arr.length == 0) {
        throw new EmptyArrayException();
      }
      double sum = 0.0;
      for (int i = 0; i < arr.length; i++) {
        sum += arr[i];
      }
      return sum / arr.length;
    };
    // This causes an exception to be thrown - handled using try-catch
    try {
      System.out.println(myDoubleArrayInter1.func(new double[0]));
    } catch (EmptyArrayException e) {
      System.out.println(e.getMessage());
    }

    // This does not cause an exception to be thrown.
    try {
      System.out.println(myDoubleArrayInter1.func(new double[] { 1.0, 2.0, 3.0 }));
    } catch (EmptyArrayException e) {
      e.printStackTrace();
    }

  }
}

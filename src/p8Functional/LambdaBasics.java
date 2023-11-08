package p8Functional;

@FunctionalInterface
interface MyDouble {
  double getDouble();
}

@FunctionalInterface
interface MyDoubleWithParam {
  double getDouble(double param);
}

class MyDoubleImpl implements MyDouble {
  @Override
  public double getDouble() {
    return 0.0;
  }
}

class MyDoubleImplWithParam implements MyDoubleWithParam {
  @Override
  public double getDouble(double param) {
    return param;
  }
}

public class LambdaBasics {
  public static void main(String[] args) {
    /*
     * Ways to implement the interface
     * 1. Creating class that implements the interface (above for both)
     * 2. Using Anonymous class (below for no-arg interface MyDouble)
     * 3. Using Lambda expression (below for one-arg interface MyDoubleWithParam)
     */

    // 1. Creating class that implements the interface
    MyDouble myDouble = new MyDoubleImpl();
    System.out.println(myDouble.getDouble());
    MyDoubleWithParam myDoubleWithParam = new MyDoubleImplWithParam();
    System.out.println(myDoubleWithParam.getDouble(10.0));

    // 2. Using Anonymous class for no-arg interface MyDouble
    MyDouble myDoubleA = new MyDouble() {
      @Override
      public double getDouble() {
        return 20.0;
      }
    };
    System.out.println(myDoubleA.getDouble());

    // 3. Using Lambda expression for one-arg interface MyDoubleWithParam
    MyDoubleWithParam myDoubleWithParamA = (param) -> param;
    // lambda which simply returns the parameter - could be complex expression too
    System.out.println(myDoubleWithParamA.getDouble(30.0));
  }
}

package p8Functional;

@FunctionalInterface
interface StringInter {
  String myMethod(String s);
}

public class LambdaArguments {
  private static String myOperation(StringInter myInter, String s) {
    return myInter.myMethod(s);
  }

  public static void main(String[] args) {
    // Inline lambda (input String and returns its reverse) to a method myOperation
    String result = myOperation(s -> new StringBuilder(s).reverse().toString(), "Hello");
    System.out.println(result);

    // Lambda created first (remove space characters) & then passed to myOperation
    StringInter myInter = s -> {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) != ' ') {
          sb.append(s.charAt(i));
        }
      }
      return sb.toString();
    };
    System.out.println(myOperation(myInter, "Hello World"));
  }
}

package p8Functional;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaMiniChallenges {
  public static void main(String[] args) {
    /*
     * Mini Challenge 1: Create a lambda expression for the following anonymous
     * class:
     * Consumer<String> printTheWords = new Consumer<String>() {
     *  @Override
     *  public void accept(String sentence) {
     *    String[] words = words.split(" ");
     *    for (String word : words) {
     *      System.out.println(word);
     *    }
     *  }
     * };
     */
    Consumer<String> printTheWords = (sentence) -> Arrays
        .asList(sentence.split(" "))
        .forEach(System.out::println);
    String sentence = "The quick brown fox jumps over the lazy dog";
    printTheWords.accept(sentence);

    System.out.println("-----------------------");

    /*
     * Mini Challenge 2: Create a lambda expression for the following method:
     * public static String everySecondChar(String source) {
     *  StringBuilder sb = new StringBuilder();
     *  for (int i = 0; i < source.length(); i++) {
     *    if (i % 2 == 0) {
     *      sb.append(source.charAt(i));
     *    }
     *  }
     *  return sb.toString();
     * }
     */
    Function<String, String> everySecondChar = (source) -> {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < source.length(); i+=2) {
        sb.append(source.charAt(i));
      }
      return sb.toString();
    };
    String source = "abcdefghijklmnopqrstuvwxyz";
    System.out.println(everySecondChar.apply(source));

    System.out.println("-----------------------");

    /*
     * Mini Challenge 3: Pass the previous lambda expression as an argument to a method
     * to return the result of lambda everySecondChar on the source string.
     */
    System.out.println(everySecondCharacter(source, everySecondChar));

    System.out.println("-----------------------");

    /*
     * Mini Challenge 4: Create a lambda expression with Supplier interface
     * and return the String "I love Java!" & assign it to a variable iLoveJava
     */
    Supplier<String> iLoveJava = () -> "I love Java!";
    System.out.println(iLoveJava.get());

    System.out.println("-----------------------");
  }

  private static <T> T everySecondCharacter(T source, Function<T, T> func) {
    return func.apply(source);
  }
}

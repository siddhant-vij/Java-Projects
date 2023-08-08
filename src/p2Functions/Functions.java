package p2Functions;

import java.util.Scanner;

public class Functions {

  public static int reverse(int n) {
    int num = Math.abs(n);
    long rev = 0L;
    while (num != 0) {
      rev = rev * 10 + num % 10;
      num = num / 10;
    }
    if (rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE) {
      return 0;
    }
    return n < 0 ? -1 * (int) rev : (int) rev;

  }

  public static String reverseWords(String str) {
    String[] words = str.split(" ");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(reverseWord(word)).append(" ");
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }

  private static String reverseWord(String string) {
    return new StringBuilder(string).reverse().toString();
  }

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      // String input = sc.nextLine();
      // System.out.println(reverseWords(input));

      int n = sc.nextInt();
      System.out.println(reverse(n));
    }
  }
}
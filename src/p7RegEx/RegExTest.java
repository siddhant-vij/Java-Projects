package p7RegEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExTest {

  public static void emailValidation() throws IOException {
    String file = "src/p7RegEx/emailFile.txt";
    String patternStr = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    Pattern pattern = Pattern.compile(patternStr);
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      while ((line = br.readLine()) != null) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
          System.out.println(line);
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    // Input text
    // (914).582.3013
    // (873).334.2589
    // (521).589.3147
    // (625).235.3698
    // (895).568.2145
    // (745).256.3369

    // Output text
    // 914.582.3013
    // 873.334.2589
    // 521.589.3147
    // 625.235.3698
    // 895.568.2145
    // 745.256.3369
    String file = "src/p7RegEx/inputFile.txt";
    String patternStr = "\\(([0-9]{3})\\)(\\.[0-9]{3}\\.[0-9]{4})";
    String replaceStr = "$1$2";
    Pattern pattern = Pattern.compile(patternStr);
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      while ((line = br.readLine()) != null) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
          System.out.println(line.replaceAll(patternStr, replaceStr));
        }
      }
    }

    System.out.println();
    emailValidation();
  }
}

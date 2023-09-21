// Each opening tag has a separate closing tag, and there are no self-closing tags.
package p5Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HtmlTags {
  public static void main(String[] args) throws IOException {
    String tag;
    String file;
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      tag = reader.readLine();
      file = reader.readLine();
    }
    String html = readFile(file);
    printMatchingTags(html, tag);
  }

  private static String readFile(String fileName) throws IOException {
    StringBuilder content = new StringBuilder();
    try (BufferedReader get = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = get.readLine()) != null) {
        content.append(line).append("\n");
      }
    }
    return content.toString();
  }

  private static void printMatchingTags(String html, String tag) {
    extractAndPrintTags(html, tag);
  }

  private static void extractAndPrintTags(String htmlContent, String tag) {
    String openTag = "<" + tag;
    String closeTag = "</" + tag + ">";
    int start, end, count;

    while (true) {
      start = htmlContent.indexOf(openTag);
      if (start == -1) {
        break; // Exit loop if no more opening tags found
      }

      end = start;
      count = 0; // Counter for unmatched opening tags
      while (true) {
        if (htmlContent.startsWith(openTag, end)) {
          count++;
          end += openTag.length();
        } else if (htmlContent.startsWith(closeTag, end)) {
          count--;
          end += closeTag.length();
        } else {
          end++;
        }

        if (count == 0) { // All opening tags have been matched
          break;
        }

        if (end >= htmlContent.length()) { // No closing tag found, should not happen in well-formed HTML
          break;
        }
      }

      // Print the found tag
      String matchedTagContent = htmlContent.substring(start, end);
      System.out.println(matchedTagContent);

      // Remove the outer tags and recursively search for nested tags
      String innerContent = matchedTagContent.substring(openTag.length(),
          matchedTagContent.length() - closeTag.length());
      extractAndPrintTags(innerContent, tag);

      // Remove already processed tag from content to continue search for more
      // occurrences
      htmlContent = htmlContent.substring(0, start) + htmlContent.substring(end);
    }
  }

}

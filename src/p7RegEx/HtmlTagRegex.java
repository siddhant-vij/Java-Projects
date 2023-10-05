package p7RegEx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTagRegex {
  public static void main(String[] args) {
    String htmlSnippet = """
        <H1>Main Heading</H1>
        <h2>Sub-Heading</h2>
        <p>Paragraph 1</p>
        <p style="abc">Style Paragraph</p>
        <h3 id="abc">Summary</h3>
        <br/>
        <img src="abc.png" alt="Logo">
        <p>Testing</p>
        """;

    Pattern pattern = Pattern.compile("<(\\w+)[^>]*>([^\\v</>]*)(</\\1>)*");
    Matcher matcher = pattern.matcher(htmlSnippet);
    while (matcher.find()) {
      System.out.println("Type: " + matcher.group(1) + " - " + "Text: " + matcher.group(2));
    }
  }
}

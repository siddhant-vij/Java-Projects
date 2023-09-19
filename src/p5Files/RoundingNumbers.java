package p5Files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RoundingNumbers {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String file1 = br.readLine();
    String file2 = br.readLine();
    try (FileInputStream fis = new FileInputStream(file1);
        FileOutputStream fos = new FileOutputStream(file2)) {
      StringBuilder strB = new StringBuilder();
      while (fis.available() > 0) {
        char ch = (char) fis.read();
        String str = null;
        if (ch != ' ') {
          strB.append(ch);
        } else {
          str = strB.toString();
          strB.setLength(0);
        }

        if (str != null || (fis.available() == 0 && strB.length() > 0)) {
          if (strB.length() > 0) {
            str = strB.toString();
          }
          int num = (int) Math.round(Double.parseDouble(str));
          byte[] buffer = Integer.toString(num).getBytes();
          fos.write(buffer);
          buffer = " ".getBytes();
          fos.write(buffer);
        }
      }
    }
  }
}

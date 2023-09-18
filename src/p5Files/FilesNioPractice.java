package p5Files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FilesNioPractice {

  public static void main(String[] args) throws IOException {
    // Reads from the console the path to a file, reads lines from the file, and
    // displays them on the screen.
    Scanner sc = null;
    BufferedReader reader = null;
    try {
      sc = new Scanner(System.in);
      reader = Files.newBufferedReader(Path.of(sc.nextLine()));
      String lineX;
      while ((lineX = reader.readLine()) != null) {
        System.out.println(lineX);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (sc != null)
        sc.close();
      if (reader != null)
        reader.close();
    }

    // Read the path to file1 and the path to file2 from the console.
    // Write all the bytes from file1 to file2,
    // but in doing so swap them according to this rule: swap the first with the
    // second, the third with the fourth, and so on.
    // If the last byte in file1 is odd, then write it to file2 as is.
    InputStream fileInputStream = null;
    OutputStream fileOutputStream = null;
    try {
      sc = new Scanner(System.in);
      fileInputStream = Files.newInputStream(Paths.get(sc.nextLine()));
      fileOutputStream = Files.newOutputStream(Paths.get(sc.nextLine()));
      int fileSize = fileInputStream.available();
      byte[] buffer = new byte[fileSize];
      fileInputStream.read(buffer);
      int bufferCounter = 0;
      while (bufferCounter < fileSize - 1) {
        byte byte1 = buffer[bufferCounter];
        byte byte2 = buffer[bufferCounter + 1];
        buffer[bufferCounter] = byte2;
        buffer[bufferCounter + 1] = byte1;
        fileOutputStream.write(buffer, bufferCounter, 2);
        bufferCounter += 2;
      }
      fileOutputStream.write(buffer, bufferCounter, fileSize - bufferCounter);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (sc != null)
        sc.close();
      if (fileInputStream != null)
        fileInputStream.close();
      if (fileOutputStream != null)
        fileOutputStream.close();
    }

    // Read the name of a text file from the console
    // Then read characters from this file (use the readAllLines(Path) method of
    // the Files class) and displays everything except periods, commas, and spaces.
    try {
      sc = new Scanner(System.in);
      String fileName = sc.nextLine();
      List<String> linesX = Files.readAllLines(Paths.get(fileName));
      for (String lineX : linesX) {
        System.out.println(lineX.replaceAll("[,. ]", ""));
      }
    } finally {
      if (sc != null)
        sc.close();
    }

    // Read a file name from the console. Read a set of numbers from the file.
    // Display only the even numbers, sorted in ascending order.
    BufferedReader br = null;
    List<Integer> list = new ArrayList<>();
    try {
      sc = new Scanner(System.in);
      br = new BufferedReader(new InputStreamReader(new FileInputStream(sc.nextLine())));
      String line;
      while ((line = br.readLine()) != null) {
        int num = Integer.parseInt(line);
        if (num % 2 == 0)
          list.add(num);
      }
    } finally {
      if (sc != null)
        sc.close();
      if (br != null)
        br.close();
    }
    Collections.sort(list);
    for (int i = 0; i < list.size(); i++)
      System.out.println(list.get(i));
  }
}
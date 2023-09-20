package p5Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FilesMerge {

  public static List<LineItem> lines = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    String oFile = null;
    String uFile = null;
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      oFile = reader.readLine();
      uFile = reader.readLine();
    }
    merge(oFile, uFile);
    for (LineItem line : lines) {
      System.out.println(line);
    }
  }

  private static List<String> readFileLines(String fileName) throws IOException {
    List<String> fileLines;
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      fileLines = new ArrayList<String>();
      String line;
      while ((line = br.readLine()) != null) {
        fileLines.add(line);
      }
    }
    return fileLines;
  }

  private static void merge(String old, String updated) throws IOException {
    List<String> oFile = readFileLines(old);
    List<String> uFile = readFileLines(updated);
    int oFileLine = 0;
    int uFileLine = 0;

    while ((oFileLine < oFile.size()) && (uFileLine < uFile.size())) {
      if (oFile.get(oFileLine).equals(uFile.get(uFileLine))) {
        lines.add(new LineItem(Type.SAME, oFile.get(oFileLine)));
        oFileLine++;
        uFileLine++;
      } else if ((uFileLine + 1 < uFile.size()) && oFile.get(oFileLine).equals(uFile.get(uFileLine + 1))) {
        lines.add(new LineItem(Type.ADDED, uFile.get(uFileLine)));
        uFileLine++;
      } else if ((oFileLine + 1 < oFile.size()) && oFile.get(oFileLine + 1).equals(uFile.get(uFileLine))) {
        lines.add(new LineItem(Type.REMOVED, oFile.get(oFileLine)));
        oFileLine++;
      }
    }
    while (oFileLine < (oFile.size())) {
      lines.add(new LineItem(Type.REMOVED, oFile.get(oFileLine)));
      oFileLine++;
    }
    while (uFileLine < (uFile.size())) {
      lines.add(new LineItem(Type.ADDED, uFile.get(uFileLine)));
      uFileLine++;
    }
  }

  public static enum Type {
    ADDED, // New line added
    REMOVED, // Line deleted
    SAME // No change
  }

  public static class LineItem {
    public Type type;
    public String line;

    public LineItem(Type type, String line) {
      this.type = type;
      this.line = line;
    }

    @Override
    public String toString() {
      return type + " " + line;
    }
  }
}

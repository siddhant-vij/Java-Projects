package p5Files;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CRUD_Operations {

  private static void displayMenu() {
    System.out.println("1. Create");
    System.out.println("2. Read");
    System.out.println("3. Update");
    System.out.println("4. Delete");
  }

  private static String validate(String item, int length) {
    if (item.length() > length)
      item = item.substring(0, length);
    else
      item = item + " ".repeat(length - item.length());
    return item;
  }

  private static void create(String fileName) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String productName = validate(reader.readLine(), 30);
      String price = validate(reader.readLine(), 8);
      String quantity = validate(reader.readLine(), 4);

      int maxId = Integer.MIN_VALUE;
      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
          maxId = Math.max(maxId, Integer.parseInt(line.substring(0, 8).trim()));
        }
      }
      int id = maxId + 1;
      String idStr = validate(Integer.toString(id), 8);

      String newLine = idStr + productName + price + quantity;
      try (PrintWriter pw = new PrintWriter(new FileOutputStream(fileName, true))) {
        pw.append("\n");
        pw.append(newLine);
      }
    }
  }

  private static void update(String fileName) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String idToBeUpdated = validate(reader.readLine(), 8);
      String productName = validate(reader.readLine(), 30);
      String price = validate(reader.readLine(), 8);
      String quantity = validate(reader.readLine(), 4);

      List<String> items = new ArrayList<>();
      int idx = 0;
      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
          count++;
          if (!line.startsWith(idToBeUpdated)) {
            items.add(line);
          } else {
            idx = count;
          }
        }
      }
      String newLine = idToBeUpdated + productName + price + quantity;
      int counter = 0;
      int index = 0;
      for (int i = 0; i < items.size(); i++) {
        String item = items.get(i);
        index++;
        if (counter == 0) {
          counter++;
          try (PrintWriter pw = new PrintWriter(new FileOutputStream(fileName))) {
            if (index != idx)
              pw.write(item);
            else {
              pw.write(newLine);
              i--;
            }
          }
        } else {
          try (PrintWriter pw = new PrintWriter(new FileOutputStream(fileName, true))) {
            pw.append("\n");
            if (index != idx)
              pw.append(item);
            else {
              pw.append(newLine);
              i--;
            }
          }
        }
      }
    }
  }

  private static void delete(String fileName) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String idToBeDeleted = validate(reader.readLine(), 8);
      List<String> items = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
          if (!line.startsWith(idToBeDeleted)) {
            items.add(line);
          }
        }
      }
      int count = 0;
      for (String item : items) {
        if (count == 0) {
          count++;
          try (PrintWriter pw = new PrintWriter(new FileOutputStream(fileName))) {
            pw.write(item);
          }
        } else {
          try (PrintWriter pw = new PrintWriter(new FileOutputStream(fileName, true))) {
            pw.append("\n");
            pw.append(item);
          }
        }
      }
    }
  }

  private static void read(String fileName) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      String idToBeRead = validate(reader.readLine(), 8);
      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = br.readLine()) != null) {
          if (line.startsWith(idToBeRead)) {
            System.out.println(line);
          }
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    displayMenu();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      int choice = Integer.parseInt(br.readLine());
      String fileName = br.readLine();
      switch (choice) {
        case 1:
          create(fileName);
          break;
        case 2:
          read(fileName);
          break;
        case 3:
          update(fileName);
          break;
        case 4:
          delete(fileName);
          break;
      }
    }
  }
}

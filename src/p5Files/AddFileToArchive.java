package p5Files;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class AddFileToArchive {
  public static void main(String[] args) throws IOException {
    String fileName;
    String zipFileName;
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      System.out.print("Enter file name: ");
      fileName = reader.readLine();
      System.out.print("Enter zip file name: ");
      zipFileName = reader.readLine();
    }
    File file = new File(fileName);
    Map<String, ByteArrayOutputStream> zipContents = new HashMap<>();

    // Read the ZIP file and store entries in memory
    try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName))) {
      ZipEntry entry;
      while ((entry = zipInputStream.getNextEntry()) != null) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int count;
        while ((count = zipInputStream.read(buffer)) != -1) {
          byteArrayOutputStream.write(buffer, 0, count);
        }
        zipContents.put(entry.getName(), byteArrayOutputStream);
      }
    }

    // Write the new ZIP file
    try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName))) {
      // Add the new file to the "new" directory
      zipOutputStream.putNextEntry(new ZipEntry("new/" + file.getName()));
      Files.copy(file.toPath(), zipOutputStream);

      // Write the rest of the original contents, skipping the same-named file in the
      // "new" directory
      for (Map.Entry<String, ByteArrayOutputStream> pair : zipContents.entrySet()) {
        if (!pair.getKey().equals("new/" + file.getName())) {
          zipOutputStream.putNextEntry(new ZipEntry(pair.getKey()));
          zipOutputStream.write(pair.getValue().toByteArray());
        }
      }
    }
  }
}

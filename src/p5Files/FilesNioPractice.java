package p5Files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FilesNioPractice {

  public static void main(String[] args) throws IOException {
    // Get file name from the user - via console
    String fileName;
    System.out.print("Enter file name: ");
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      fileName = reader.readLine();
    }

    // Reading a file via a Channel - Manual Buffer Creation
    try (SeekableByteChannel fchan = Files.newByteChannel(Path.of(fileName))) {
      ByteBuffer buffer = ByteBuffer.allocate(128);
      int count = 0;
      do {
        count = fchan.read(buffer);
        if (count != -1) {
          buffer.rewind();
          for (int i = 0; i < count; i++) {
            System.out.print((char) buffer.get());
          }
        }
      } while (count != -1);
      System.out.println();
    }

    // Reading a file via a Channel - Buffer Map to Channel
    try (FileChannel fchan = (FileChannel) Files.newByteChannel(Path.of(fileName))) {
      long fSize = fchan.size();
      MappedByteBuffer buffer = fchan.map(FileChannel.MapMode.READ_ONLY, 0, fSize);
      for (int i = 0; i < fSize; i++) {
        System.out.print((char) buffer.get());
      }
      System.out.println();
    }

    String testText = "This is a test text. Could also be text taken from a file: line-by-line. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent porta erat ut diam condimentum vehicula. Ut rutrum risus dignissim metus vulputate consectetur. Sed urna purus, suscipit in nibh vitae, ultricies mollis nisl. Aliquam erat volutpat. Aliquam a gravida turpis. Ut vel eros pellentesque, pulvinar lorem at, hendrerit nunc. Morbi interdum vestibulum aliquet. Etiam sed maximus eros. In dolor nisi, lobortis at rutrum sed, rutrum a lectus.";

    // Writing to a file via a Channel - Manual Buffer Creation
    try (FileChannel fchan = (FileChannel) Files.newByteChannel(Path.of(fileName), StandardOpenOption.WRITE,
        StandardOpenOption.CREATE)) {
      ByteBuffer buffer = ByteBuffer.allocate(20);
      int counter = 0;
      do {
        buffer.clear();
        int bytesToRead = Math.min(buffer.capacity(), testText.length() - counter);
        if (bytesToRead <= 0)
          break; // Exit if there's nothing left to read

        buffer.put(testText.getBytes(), counter, bytesToRead);

        buffer.flip();
        int writtenBytes = fchan.write(buffer);
        counter += writtenBytes;
      } while (counter < testText.length());
    }

    // Writing to a file via a Channel - Buffer Map to Channel
    try (FileChannel fchan = (FileChannel) Files.newByteChannel(Path.of(fileName), StandardOpenOption.WRITE,
        StandardOpenOption.READ,
        StandardOpenOption.CREATE)) {
      long fSize = testText.length();
      MappedByteBuffer buffer = fchan.map(FileChannel.MapMode.READ_WRITE, 0, fSize);
      byte[] bytes = testText.getBytes();
      for (int i = 0; i < fSize; i++) {
        buffer.put(bytes[i]);
      }
    }
  }
}
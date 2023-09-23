package p5Files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ListDirectoryTree {
  private List<String> getAllFilesRecursive(Path root) throws IOException {
    List<String> files = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(root)) {
      for (Path path : stream) {
        if (Files.isDirectory(path)) {
          files.addAll(getAllFilesRecursive(path));
        } else {
          files.add(path.getFileName().toString());
        }
      }
    }
    return files;
  }

  private List<String> getAllFilesIterative(Path root) throws IOException {
    List<String> files = new ArrayList<>();
    Stack<String> stack = new Stack<>();
    stack.push(root.toString());
    while (!stack.isEmpty()) {
      String dir = stack.pop();
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(dir))) {
        for (Path path : stream) {
          if (Files.isDirectory(path)) {
            stack.push(path.toString());
          } else {
            files.add(path.getFileName().toString());
          }
        }
      }
    }
    return files;
  }

  private static class MyFileVisiter extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      System.out.println(file.getFileName().toString());
      return FileVisitResult.CONTINUE;
    }
  }

  public static void main(String[] args) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      Path root = Path.of(br.readLine());

      List<String> filesRec = new ListDirectoryTree().getAllFilesRecursive(root);
      for (String file : filesRec) {
        System.out.println(file);
      }

      System.out.println("--------------------------");

      List<String> filesItr = new ArrayList<>();
      filesItr = new ListDirectoryTree().getAllFilesIterative(root);
      for (String file : filesItr) {
        System.out.println(file);
      }

      System.out.println("--------------------------");

      Files.walkFileTree(root, new MyFileVisiter());
    }
  }
}

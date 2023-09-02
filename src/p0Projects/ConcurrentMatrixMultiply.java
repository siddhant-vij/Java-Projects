package p0Projects;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MatrixUtility {
  static int[][] createMatrices(int rows, int cols, int count) {
    int[][] res = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        res[i][j] = ++count;
      }
    }
    return res;
  }

  static void displayMatrix(int[][] result) {
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        System.out.print(result[i][j] + " ");
      }
      System.out.println();
    }
  }
}

interface MatrixMultiplier {
  int[][] multiply(int[][] a, int[][] b);
}

class SequentialMultiplier implements MatrixMultiplier {
  public int[][] multiply(int[][] a, int[][] b) {
    int[][] c = new int[a.length][b[0].length];
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < b[0].length; j++) {
        c[i][j] = 0;
        for (int k = 0; k < a[0].length; k++) {
          c[i][j] += a[i][k] * b[k][j];
        }
      }
    }
    return c;
  }
}

class ConcurrentMultiplierVirtualThreads implements MatrixMultiplier {

  public int[][] multiply(int[][] matrix1, int[][] matrix2) {
    int rows = matrix1.length;
    int cols = matrix2[0].length;
    int[][] result = new int[rows][cols];
    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int row = i;
        int col = j;
        executor.execute(() -> {
          for (int k = 0; k < matrix1[0].length; k++) {
            result[row][col] += matrix1[row][k] * matrix2[k][col];
          }
        });
      }
    }

    executor.shutdown();
    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return result;
  }
}

class ConcurrentMultiplierPlatformThreads implements MatrixMultiplier {
  private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

  public int[][] multiply(int[][] matrix1, int[][] matrix2) {
    int rows = matrix1.length;
    int cols = matrix2[0].length;
    int[][] result = new int[rows][cols];
    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int row = i;
        int col = j;
        executor.execute(() -> {
          for (int k = 0; k < matrix1[0].length; k++) {
            result[row][col] += matrix1[row][k] * matrix2[k][col];
          }
        });
      }
    }

    executor.shutdown();
    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return result;
  }
}

public class ConcurrentMatrixMultiply {
  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("M1 - Rows: ");
      int m1Rows = sc.nextInt();
      System.out.print("M1 - Columns & M2 Rows: ");
      int m1ColsM2Rows = sc.nextInt();
      System.out.print("M2 - Columns: ");
      int m2Cols = sc.nextInt();

      int[][] a = MatrixUtility.createMatrices(m1Rows, m1ColsM2Rows, 0);
      int[][] b = MatrixUtility.createMatrices(m1ColsM2Rows, m2Cols, m1Rows * m1ColsM2Rows);

      MatrixMultiplier sequentialMultiplier = new SequentialMultiplier();
      MatrixMultiplier concurrentMultiplierVT = new ConcurrentMultiplierVirtualThreads();
      MatrixMultiplier concurrentMultiplierPT = new ConcurrentMultiplierPlatformThreads();

      long startTime, endTime;

      // Sequential Matrix Multiplication
      startTime = System.currentTimeMillis();
      int[][] sequentialResult = sequentialMultiplier.multiply(a, b);
      endTime = System.currentTimeMillis();
      System.out.println("Sequential Time taken: " + (endTime - startTime) / 1000 + " s");
      MatrixUtility.displayMatrix(sequentialResult);

      System.out.println("------------------------");

      // Concurrent Matrix Multiplication - Virtual Threads
      startTime = System.currentTimeMillis();
      int[][] concurrentResultVT = concurrentMultiplierVT.multiply(a, b);
      endTime = System.currentTimeMillis();
      System.out.println("Concurrent Time taken: " + (endTime - startTime) / 1000 + " s");
      MatrixUtility.displayMatrix(concurrentResultVT);

      System.out.println("------------------------");

      // Concurrent Matrix Multiplication - Platform Threads
      startTime = System.currentTimeMillis();
      int[][] concurrentResultPT = concurrentMultiplierPT.multiply(a, b);
      endTime = System.currentTimeMillis();
      System.out.println("Concurrent Time taken: " + (endTime - startTime) / 1000 + " s");
      MatrixUtility.displayMatrix(concurrentResultPT);
    }
  }
}

// M1 - Rows: 2500
// M1 - Columns & M2 Rows: 2500
// M2 - Columns: 2500
// Sequential Time taken: 172 s
// ------------------------
// Concurrent Time taken: 40 s
// ------------------------
// Concurrent Time taken: 54 s

package p0Projects.MatrixSearch;

import java.util.Arrays;

public class SortedMatrix {

  public static void main(String[] args) {
    int[][] matrix = {
        { 10, 20, 30, 40 },
        { 50, 60, 70, 80 },
        { 90, 100, 110, 120 },
        { 130, 140, 150, 160 }
    };

    int element = 160;
    System.out.println(Arrays.toString(search(matrix, element)));
  }

  private static int[] binarySearch(int[][] matrix, int row, int cStart, int cEnd, int element) {
    while (cStart <= cEnd) {
      int cMid = (cStart + cEnd) / 2;

      if (matrix[row][cMid] == element) {
        return new int[] { row, cMid };
      }

      if (matrix[row][cMid] > element) {
        cEnd = cMid - 1;
      } else {
        cStart = cMid + 1;
      }
    }
    return new int[] { -1, -1 };
  }

  private static int[] search(int[][] matrix, int element) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    if (cols == 0) {
      return new int[] { -1, -1 };
    }
    if (rows == 1) {
      return binarySearch(matrix, 0, 0, cols - 1, element);
    }

    int rStart = 0;
    int rEnd = rows - 1;
    int cMid = cols / 2;

    while (rStart < (rEnd - 1)) {
      int rMid = (rStart + rEnd) / 2;

      if (matrix[rMid][cMid] == element) {
        return new int[] { rMid, cMid };
      }

      if (matrix[rMid][cMid] > element) {
        rEnd = rMid;
      } else {
        rStart = rMid;
      }
    }

    if (matrix[rStart][cMid] == element) {
      return new int[] { rStart, cMid };
    }

    if (matrix[rStart + 1][cMid] == element) {
      return new int[] { rStart + 1, cMid };
    }

    if (element <= matrix[rStart][cMid - 1]) {
      return binarySearch(matrix, rStart, 0, cMid - 1, element);
    } else if (element >= matrix[rStart][cMid + 1]
        && element <= matrix[rStart][cols - 1]) {
      return binarySearch(matrix, rStart, cMid + 1, cols - 1, element);
    } else if (element <= matrix[rStart + 1][cMid - 1]) {
      return binarySearch(matrix, rStart + 1, 0, cMid - 1, element);
    } else {
      return binarySearch(matrix, rStart + 1, cMid + 1, cols - 1, element);
    }
  }
}

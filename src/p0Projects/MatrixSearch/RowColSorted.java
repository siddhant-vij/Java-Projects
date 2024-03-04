package p0Projects.MatrixSearch;

import java.util.Arrays;

public class RowColSorted {

  public static void main(String[] args) {
    int[][] matrix = {
        { 10, 20, 30, 40 },
        { 15, 25, 35, 45 },
        { 27, 29, 37, 48 },
        { 32, 33, 39, 50 } };

    int element = 89;
    System.out.println(Arrays.toString(search(matrix, element)));
  }

  private static int[] search(int[][] matrix, int element) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    int i = 0;
    int j = cols - 1;

    while (i < rows && j >= 0) {
      if (matrix[i][j] == element) {
        return new int[] { i, j };
      }

      if (matrix[i][j] > element) {
        j--;
      } else {
        i++;
      }
    }

    return new int[] { -1, -1 };
  }
}

package p0Projects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class MergeSortUtils {

  public static void merge(Integer[] arr, int i, int mid, int j) {
    int n1 = mid - i + 1;
    int n2 = j - mid;
    int[] L = new int[n1];
    int[] R = new int[n2];
    for (int k = 0; k < n1; k++) {
      L[k] = arr[i + k];
    }
    for (int k = 0; k < n2; k++) {
      R[k] = arr[mid + 1 + k];
    }
    int i1 = 0;
    int i2 = 0;
    int k = i;
    while (i1 < n1 && i2 < n2) {
      if (L[i1] < R[i2]) {
        arr[k] = L[i1];
        i1++;
      } else {
        arr[k] = R[i2];
        i2++;
      }
      k++;
    }
    while (i1 < n1) {
      arr[k] = L[i1];
      i1++;
      k++;
    }
    while (i2 < n2) {
      arr[k] = R[i2];
      i2++;
      k++;
    }
  }

}

class MergeSortSingleThread extends Thread {
  Integer[] arr;

  public MergeSortSingleThread(Integer[] arr) {
    this.arr = arr;
  }

  private void mergeSort(Integer[] arr, int i, int j) {
    if (i < j) {
      int mid = (i + j) / 2;
      mergeSort(arr, i, mid);
      mergeSort(arr, mid + 1, j);
      MergeSortUtils.merge(arr, i, mid, j);
    }
  }

  @Override
  public void run() {
    this.mergeSort(arr, 0, arr.length - 1);
  }
}

class MergeSortMultipleThread extends RecursiveAction {
  Integer[] arr;
  private int low;
  private int high;

  public MergeSortMultipleThread(Integer[] arr, int low, int high) {
    this.arr = arr;
    this.low = low;
    this.high = high;
  }

  @Override
  protected void compute() {
    if (low < high) {
      int mid = (low + high) / 2;
      invokeAll(
          new MergeSortMultipleThread(arr, low, mid),
          new MergeSortMultipleThread(arr, mid + 1, high));
      MergeSortUtils.merge(arr, low, mid, high);
    }
  }
}

public class MultiThreadedMergeSort {

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Enter number of elements: ");
      int n = sc.nextInt();
      Random rd = new Random();
      Integer[] arr = new Integer[n];
      for (int i = 0; i < arr.length; i++) {
        arr[i] = rd.nextInt();
      }

      MergeSortSingleThread thread1 = new MergeSortSingleThread(arr);
      long startTimeSingle = System.nanoTime();
      thread1.start();
      try {
        thread1.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      long endTimeSingle = System.nanoTime();
      System.out.println("Time taken - Single Thread: " + (endTimeSingle - startTimeSingle) / 1000000000.0 + " s");

      System.out.println("--------------------");

      List<Integer> intList = Arrays.asList(arr);
      Collections.shuffle(intList);
      intList.toArray(arr);

      // Measuring time and memory for Multi Threaded Merge Sort
      long startTimeMulti = System.nanoTime();
      try (ForkJoinPool pool = new ForkJoinPool(250)) {
        pool.invoke(new MergeSortMultipleThread(arr, 0, arr.length - 1));
      }
      long endTimeMulti = System.nanoTime();
      System.out.println("Time taken - Multi Thread: " + (endTimeMulti - startTimeMulti) / 1000000000.0 + " s");

    }
  }
}

// Enter number of elements: 99999999
// Time taken - Single Thread: 45.6335461 s
// --------------------
// Time taken - Multi Thread: 43.4562248 s
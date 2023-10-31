package p6Threads;

/*
 * Accept an array of numbers
 * Calculate the nth prime number (n = arr[i])
 * Add & print the sum of all prime numbers
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class NthPrimeNumber {
  public boolean isPrime(int n) {
    if (n == 1)
      return false;
    if (n != 2 && n % 2 == 0 || n != 3 && n % 3 == 0)
      return false;
    for (int i = 5; i * i <= n; i += 6) {
      if (n % i == 0 || n % (i + 2) == 0)
        return false;
    }
    return true;
  }

  public int nthPrimeNumber(int n) {
    int count = 0;
    int i = 2;
    while (count < n) {
      if (isPrime(i))
        count++;
      i++;
    }
    return i - 1;
  }
}

class SumTask extends RecursiveTask<Integer> {
  int[] arr;
  int start, end;

  SumTask(int[] arr, int start, int end) {
    this.arr = arr;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Integer compute() {
    if (start == end) {
      // System.out.println(arr[start] + ":" + new
      // NthPrimeNumber().nthPrimeNumber(arr[start]));
      return new NthPrimeNumber().nthPrimeNumber(arr[start]);
    } else if (end - start == 1) {
      // System.out.println(arr[start] + ":" + new
      // NthPrimeNumber().nthPrimeNumber(arr[start]));
      // System.out.println(arr[end] + ":" + new
      // NthPrimeNumber().nthPrimeNumber(arr[end]));
      return new NthPrimeNumber().nthPrimeNumber(arr[start]) + new NthPrimeNumber().nthPrimeNumber(arr[end]);
    } else {
      int mid = (start + end) / 2;
      SumTask task1 = new SumTask(arr, start, mid);
      SumTask task2 = new SumTask(arr, mid + 1, end);
      task1.fork();
      task2.fork();
      return task1.join() + task2.join();
    }
  }
}

public class PrimeNumbersFJPDemo {
  public static void main(String[] args) {
    int n = 20;
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = i + 1;
    }
    ForkJoinPool pool = ForkJoinPool.commonPool();
    SumTask task = new SumTask(arr, 0, arr.length - 1);
    int sum = pool.invoke(task);
    System.out.println(sum);
  }
}

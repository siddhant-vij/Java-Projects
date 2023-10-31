package p6Threads;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class NthFibonacci extends RecursiveTask<Integer> {
  private int n;

  public NthFibonacci(int n) {
    this.n = n;
  }

  @Override
  protected Integer compute() {
    if (n == 1 || n == 2) {
      return n - 1;
    }
    NthFibonacci f1 = new NthFibonacci(n - 1);
    NthFibonacci f2 = new NthFibonacci(n - 2);
    f2.fork();
    return f1.compute() + f2.join();
  }
}

public class FibonacciParallel {
  public static void main(String[] args) {
    int n = 25;
    ForkJoinPool pool = ForkJoinPool.commonPool();
    NthFibonacci task = new NthFibonacci(n);
    int nFib = pool.invoke(task);
    System.out.println(nFib);
  }
}

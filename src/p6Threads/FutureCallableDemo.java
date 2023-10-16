package p6Threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MySum implements Callable<Integer> {
  int num;

  public MySum(int num) {
    this.num = num;
  }

  @Override
  public Integer call() {
    int sum = 0;
    for (int i = 1; i <= this.num; i++) {
      sum += i;
    }
    return sum;
  }
}

class MyHypotenuse implements Callable<Double> {
  int side1;
  int side2;

  public MyHypotenuse(int side1, int side2) {
    this.side1 = side1;
    this.side2 = side2;
  }

  @Override
  public Double call() {
    return Math.sqrt(this.side1 * this.side1 + this.side2 * this.side2);
  }
}

class Factorial implements Callable<Integer> {
  int num;

  public Factorial(int num) {
    this.num = num;
  }

  @Override
  public Integer call() {
    int fact = 1;
    for (int i = 2; i <= this.num; i++) {
      fact *= i;
    }
    return fact;
  }
}

public class FutureCallableDemo {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(3);
    Future<Integer> sumFuture = executor.submit(new MySum(10));
    Future<Double> hypotenuseFuture = executor.submit(new MyHypotenuse(3, 4));
    Future<Integer> factorialFuture = executor.submit(new Factorial(5));
    try {
      System.out.println("Sum = " + sumFuture.get());
      System.out.println("Hypotenuse = " + hypotenuseFuture.get());
      System.out.println("Factorial = " + factorialFuture.get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    executor.shutdown();
  }
}

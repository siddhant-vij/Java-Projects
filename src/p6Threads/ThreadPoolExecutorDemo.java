package p6Threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ActualJob implements Runnable {
  String name;

  ActualJob(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    System.out.println(name + " job started by " + Thread.currentThread().getName());
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      System.out.println("Interrupted");
    }
    System.out.println(name + " job finished by " + Thread.currentThread().getName());
  }
}

class ActualJobCall implements Callable<Integer> {
  int num;

  ActualJobCall(int num) {
    this.num = num;
  }

  @Override
  public Integer call() {
    // If a checked exception is thrown in the callable
    // it can be handled with try-catch block or
    // throws ExceptionType.
    // But, in run() method, only try-catch is allowed
    int sum = 0;
    System.out.println("Sum Job Call for num = " + this.num + " handled by " + Thread.currentThread().getName());
    for (int i = 1; i <= this.num; i++) {
      sum += i;
    }
    return sum;
  }
}

public class ThreadPoolExecutorDemo {
  public static void jobCallDemo() throws InterruptedException, ExecutionException {
    ActualJobCall[] jobs = {
        new ActualJobCall(5),
        new ActualJobCall(10),
        new ActualJobCall(15),
        new ActualJobCall(20),
        new ActualJobCall(25),
        new ActualJobCall(100)
    };
    // Executor Framework
    ExecutorService service = Executors.newFixedThreadPool(3);
    // A Thread Pool of 3 Threads is created
    // This thread pool different from Runnable's thread pool

    for (ActualJobCall job : jobs) {
      Future<Integer> f = service.submit(job);
      System.out.println("Sum Job Call for num = " + job.num + " returns " + f.get());
    }
    // Jobs are submitted to the thread pool
    // Future is used to get the result of the job

    service.shutdown();
    // The thread pool is shutdown - all threads are terminated
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ActualJob[] jobs = {
        new ActualJob("Job 1"),
        new ActualJob("Job 2"),
        new ActualJob("Job 3"),
        new ActualJob("Job 4"),
        new ActualJob("Job 5"),
        new ActualJob("Job 6")
    };
    // Executor Framework
    ExecutorService service = Executors.newFixedThreadPool(3);
    // A Thread Pool of 3 Threads is created

    for (ActualJob job : jobs) {
      service.submit(job);
    }
    // Jobs are submitted to the thread pool

    service.shutdown();
    // The thread pool is shutdown - all threads are terminated

    jobCallDemo();
    // Runnable vs Callable - Return
  }
}

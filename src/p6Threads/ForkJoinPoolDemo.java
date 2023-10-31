package p6Threads;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class Activity {
  public double transform(double n) {
    if (n % 2 == 0) {
      return Math.sqrt(n);
    } else {
      return Math.cbrt(n);
    }
  }
}

class MyAction extends RecursiveAction {
  int threshold;
  double[] data;
  int start, end;
  Activity activity;

  MyAction(int threshold, double[] data, int start, int end, Activity activity) {
    this.threshold = threshold;
    this.data = data;
    this.start = start;
    this.end = end;
    this.activity = activity;
  }

  @Override
  protected void compute() {
    if (end - start < threshold) {
      for (int i = start; i < end; i++) {
        data[i] = activity.transform(data[i]);
      }
    } else {
      int mid = (start + end) / 2;
      invokeAll(new MyAction(threshold, data, start, mid, activity), new MyAction(threshold, data, mid, end, activity));
    }
  }
}

public class ForkJoinPoolDemo {
  public static void main(String[] args) {
    /*
     * Sequential Time: 6.0 s
     */
    double[] arr1 = new double[100000000];
    for (int i = 0; i < arr1.length; i++) {
      arr1[i] = i * 1.0;
    }
    Activity activity = new Activity();
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < arr1.length; i++) {
      arr1[i] = activity.transform(arr1[i]);
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Sequential Time: " + (endTime - startTime) / 1000.0 + " s");

    System.out.println("------------------------");

    /*
     * Parallel Time: 1.0 s
     */
    double[] arr2 = new double[100000000];
    for (int i = 0; i < arr2.length; i++) {
      arr2[i] = i * 1.0;
    }
    ForkJoinPool pool = ForkJoinPool.commonPool();
    startTime = System.currentTimeMillis();
    pool.invoke(new MyAction(1000, arr2, 0, arr2.length, activity));
    endTime = System.currentTimeMillis();
    System.out.println("Parallel Time: " + (endTime - startTime) / 1000.0 + " s");
  }
}

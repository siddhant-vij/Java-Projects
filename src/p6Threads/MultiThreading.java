package p6Threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

class MyThread1 extends Thread {
    private final int num;

    MyThread1(int num, String name) {
        super(name);
        this.num = num;
    }

    @Override
    public void run() {
        int i = 0;
        while ((i++) < this.num) {
            System.out.println("MyThread # 1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class MyThread2 extends Thread {
    private final int num;

    MyThread2(int num, String name) {
        super(name);
        this.num = num;
    }

    @Override
    public void run() {
        int i = 0;
        while ((i++) < this.num) {
            System.out.println("MyThread # 2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class MyThreadRunnable1 implements Runnable {
    private final int num;

    MyThreadRunnable1(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        int i = 0;
        while ((i++) < this.num) {
            System.out.println("MyThread # 1");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class MyThreadRunnable2 implements Runnable {
    private final int num;

    MyThreadRunnable2(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        int i = 0;
        while ((i++) < this.num) {
            System.out.println("MyThread # 2");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class MyThreadCallable1 implements Callable<Integer> {
    private final int num;

    MyThreadCallable1(int num) {
        this.num = num;
    }

    @Override
    public Integer call() {
        int sum = 0;
        System.out.println("Sum Job Call for num = " + this.num + " handled by " + Thread.currentThread().getName());
        for (int i = 1; i <= this.num; i++) {
            sum += i;
        }
        return sum;
    }
}

public class MultiThreading {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Multi-Threading: Extending (In Built) Thread Class
        // int n = 100;
        // MyThread1 th1 = new MyThread1(n, "Thread 1");
        // MyThread2 th2 = new MyThread2(n, "Thread 2");
        // th1.start();
        // // To proceed with Thread 2 only after Thread 1 finishes (join() method
        // inside try-catch block)
        // /*try {
        // th1.join();
        // }
        // catch (Exception e){
        // throw new RuntimeException(e);
        // }*/
        // th2.start();

        // Thread Methods: Multiple other methods than below - Research on Oracle Docs
        /*
         * System.out.println(th1.getId());
         * System.out.println(th1.getName());
         * System.out.println(th1.getPriority());
         * System.out.println(th2.getId());
         * System.out.println(th2.getName());
         * System.out.println(th2.getPriority());
         */

        // Multi-Threading: Implementing Runnable Interface
        /*
         * int n = 5;
         * MyThreadRunnable1 thr1 = new MyThreadRunnable1(n);
         * Thread mth1 = new Thread(thr1);
         * MyThreadRunnable2 thr2 = new MyThreadRunnable2(n);
         * Thread mth2 = new Thread(thr2);
         * mth1.start();
         * mth2.start();
         */

        /*
         * mth2.setName("Thread-2");
         * mth1.setName("Thread-1");
         * 
         * System.out.println(mth1.getId());
         * System.out.println(mth1.getName());
         * System.out.println(mth1.getPriority());
         * System.out.println(mth2.getId());
         * System.out.println(mth2.getName());
         * System.out.println(mth2.getPriority());
         */

        // Multi-Threading: Implementing Callable Interface
        ExecutorService es = Executors.newFixedThreadPool(3);
        int[] x = { 10, 20, 30, 40, 50, 60 };
        for (int i = 0; i < x.length; i++) {
            FutureTask<Integer> ft = new FutureTask<>(new MyThreadCallable1(x[i]));
            es.execute(ft);
            System.out.println("Number: " + x[i] + ", Sum: " + ft.get());
        }
        es.shutdown();
    }
}
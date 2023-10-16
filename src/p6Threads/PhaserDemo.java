package p6Threads;

import java.util.concurrent.Phaser;

class MyThreadP implements Runnable {
  String name;
  Phaser phaser;

  MyThreadP(String name, Phaser phaser) {
    this.name = name;
    this.phaser = phaser;
    this.phaser.register();
  }

  @Override
  public void run() {
    System.out.println("Thread: " + name + " started with Phase " + phaser.getPhase());
    phaser.arriveAndAwaitAdvance();

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Thread: " + name + " started with Phase " + phaser.getPhase());
    phaser.arriveAndAwaitAdvance();

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Thread: " + name + " started with Phase " + phaser.getPhase());
    phaser.arriveAndDeregister();
  }
}

public class PhaserDemo {
  public static void main(String[] args) {
    Phaser phaser = new Phaser(1);
    int curPhase;

    System.out.println("Starting...\n");
    new Thread(new MyThreadP("A", phaser)).start();
    new Thread(new MyThreadP("B", phaser)).start();
    new Thread(new MyThreadP("C", phaser)).start();

    curPhase = phaser.getPhase();
    phaser.arriveAndAwaitAdvance();
    System.out.println("Phase: " + curPhase + " completed.\n");

    curPhase = phaser.getPhase();
    phaser.arriveAndAwaitAdvance();
    System.out.println("Phase: " + curPhase + " completed.\n");

    curPhase = phaser.getPhase();
    phaser.arriveAndAwaitAdvance();
    System.out.println("Phase: " + curPhase + " completed.\n");

    phaser.arriveAndDeregister();

    if (phaser.isTerminated())
      System.out.println("Exiting...");
  }
}

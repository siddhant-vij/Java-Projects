package p6Threads;

import java.util.concurrent.Phaser;

class MyPhaser extends Phaser {
  int numPhases;

  MyPhaser(int parties, int numPhases) {
    super(parties);
    this.numPhases = numPhases;
  }

  @Override
  protected boolean onAdvance(int curPhase, int registeredParties) {
    System.out.println("Phase: " + curPhase + " completed.\n");
    if (curPhase == numPhases - 1 || registeredParties == 0) {
      return true;
    }
    return false;
  }
}

class MyThreadPB implements Runnable {
  String name;
  Phaser phaser;

  MyThreadPB(String name, Phaser phaser) {
    this.name = name;
    this.phaser = phaser;
    this.phaser.register();
  }

  @Override
  public void run() {
    while (!phaser.isTerminated()) {
      System.out.println("Thread: " + name + " started with Phase " + phaser.getPhase());
      phaser.arriveAndAwaitAdvance();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

public class PhaserDemoBetter {
  public static void main(String[] args) {
    Phaser phaser = new MyPhaser(1, 3);

    System.out.println("Starting...\n");

    new Thread(new MyThreadPB("A", phaser)).start();
    new Thread(new MyThreadPB("B", phaser)).start();
    new Thread(new MyThreadPB("C", phaser)).start();

    while (!phaser.isTerminated()) {
      phaser.arriveAndAwaitAdvance();
    }

    System.out.println("Exiting...\n");
  }
}

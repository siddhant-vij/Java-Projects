package p6Threads;

import java.util.ArrayList;
import java.util.List;

class VirtualThreadJob {
  List<Thread> vThreads = new ArrayList<>();
  int vThreadCount;

  VirtualThreadJob(int vThreadCount) {
    this.vThreadCount = vThreadCount;
  }

  public Double tryThis() {
    long startTime = System.nanoTime();
    for (int i = 0; i < vThreadCount; i++) {
      Thread vThread = Thread.ofVirtual().start(
          () -> {
            for (int j = 1; j <= 100; j++) {
              try {
                Thread.sleep(0);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          });
      vThreads.add(vThread);
    }
    for (Thread vThread : vThreads) {
      try {
        vThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    long endTime = System.nanoTime();
    return (endTime - startTime) / 1000000000.0;
  }
}

class PlatformThreadJob {
  List<Thread> pThreads = new ArrayList<>();
  int pThreadCount;

  PlatformThreadJob(int pThreadCount) {
    this.pThreadCount = pThreadCount;
  }

  public Double tryThis() {
    long startTime = System.nanoTime();
    for (int i = 0; i < pThreadCount; i++) {
      Thread pThread = Thread.ofPlatform().start(
          () -> {
            for (int j = 1; j <= 100; j++) {
              try {
                Thread.sleep(0);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          });
      pThreads.add(pThread);
    }
    for (Thread vThread : pThreads) {
      try {
        vThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    long endTime = System.nanoTime();
    return (endTime - startTime) / 1000000000.0;
  }
}

public class VirtualThreadDemo {
  public static void main(String[] args) {
    VirtualThreadJob vJob = new VirtualThreadJob(100000);
    System.out.println(vJob.tryThis());
    PlatformThreadJob pJob = new PlatformThreadJob(100000);
    System.out.println(pJob.tryThis());
    // 1.7499644
    // 42.8469847
  }
}

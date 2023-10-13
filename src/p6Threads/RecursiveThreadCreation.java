// Requirements:
// •	The GenerateThread class should inherit the Thread class.
// •	The GenerateThread class must have a public no-argument constructor.
// •	The GenerateThread class constructor should increment createdThreadCount and pass it as a string to the superclass constructor.
// •	The GenerateThread class constructor must start the thread.
// •	The GenerateThread class's toString method must return the thread name and the word " created". Example: "8 created".
// •	If the number of created threads is less than Solution.count, the run method must create a new GenerateThread.
// •	If the number of created threads is less than Solution.count, the run method must display the created thread.
// •	The program's output must match the task conditions and indicate that all 15 threads have been created.

package p6Threads;

public class RecursiveThreadCreation {
  static int count = 15;
  static volatile int createdThreadCount;

  public static void main(String[] args) {
    System.out.println(new GenerateThread());
  }
  
  public static class GenerateThread extends Thread {
    public GenerateThread() {
      super(String.valueOf(++createdThreadCount));
      start();
    }

    public void run() {
      if (createdThreadCount < count) {
        System.out.println(new GenerateThread());
      }
    }

    @Override
    public String toString() {
      return getName() + " created";
    }
  }
}

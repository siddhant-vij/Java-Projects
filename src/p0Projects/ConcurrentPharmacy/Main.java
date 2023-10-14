package p0Projects.ConcurrentPharmacy;

public class Main {
  public static boolean isStopped = false;

  public static void main(String[] args) throws InterruptedException {
    Thread pharmacy = new Thread(new Pharmacy(), "Pharmacy");
    Thread man = new Thread(new Person(), "Man");
    Thread woman = new Thread(new Person(), "Woman");

    pharmacy.start();
    man.start();
    woman.start();

    Thread.sleep(1000);
    isStopped = true;
  }
}

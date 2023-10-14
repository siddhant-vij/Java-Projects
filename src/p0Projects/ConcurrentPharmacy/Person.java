package p0Projects.ConcurrentPharmacy;

public class Person implements Runnable {
  @Override
  public void run() {
    while(!Main.isStopped){
      DrugController.buy(Utils.getRandomDrug(), Utils.getRandomCount());
      Utils.waitAMoment();
    }
  }
}

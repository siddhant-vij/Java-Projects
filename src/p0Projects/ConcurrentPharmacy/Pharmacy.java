package p0Projects.ConcurrentPharmacy;

public class Pharmacy implements Runnable {
  @Override
  public void run() {
    while(!Main.isStopped){
      DrugController.sell(Utils.getRandomDrug(), Utils.getRandomCount());
      Utils.waitAMoment();
      Utils.waitAMoment();
      Utils.waitAMoment();
    }
  }  
}

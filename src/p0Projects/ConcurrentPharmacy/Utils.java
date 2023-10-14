package p0Projects.ConcurrentPharmacy;

import java.util.ArrayList;
import java.util.List;

public class Utils {
  public static int getRandomCount() {
    return (int) (Math.random() * 3) + 1;
  }

  public static Drug getRandomDrug() {
    int index = (int) ((Math.random() * 1000) % (DrugController.allDrugs.size()));
    List<Drug> drugs = new ArrayList<>(DrugController.allDrugs.keySet());
    return drugs.get(index);
  }

  public static void waitAMoment() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException ignored) {
    }
  }
}

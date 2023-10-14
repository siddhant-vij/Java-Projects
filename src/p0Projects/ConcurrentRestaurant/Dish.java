package p0Projects.ConcurrentRestaurant;

public class Dish {
  private byte tableNumber;

  public Dish(byte tableNumber) {
    this.tableNumber = tableNumber;
  }

  public byte getTableNumber() {
    return tableNumber;
  }
}

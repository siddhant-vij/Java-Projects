package p9Reflection.JsonSerializer.data;

public class Address {
  private final String street;
  private final short apartment;

  public Address(String street, short apartment) {
    this.street = street;
    this.apartment = apartment;
  }

  @Override
  public String toString() {
    return "Address [street=" + street + ", apartment=" + apartment + "]";
  }
}

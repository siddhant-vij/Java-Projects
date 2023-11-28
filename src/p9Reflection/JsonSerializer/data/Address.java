package p9Reflection.JsonSerializer.data;

public class Address {
  private static final int ZIP_CODE_MAX_DIGITS = 5;
  private final transient String zipCode;
  private final String street;
  private final short apartment;

  public Address(String street, short apartment, String zipCode) {
    this.street = street;
    this.apartment = apartment;
    if (zipCode.length() != ZIP_CODE_MAX_DIGITS) {
      throw new IllegalArgumentException("Invalid zip code");
    }
    this.zipCode = zipCode;
  }

  @Override
  public String toString() {
    return "Address{" +
        "zipCode='" + zipCode + '\'' +
        ", street='" + street + '\'' +
        ", apartment=" + apartment +
        '}';
  }
}

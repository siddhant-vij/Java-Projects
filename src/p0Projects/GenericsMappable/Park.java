package p0Projects.GenericsMappable;

import java.util.Arrays;

public class Park extends Point {
  String name;

  public Park(String name, String location) {
    this.name = name;
    this.location = new double[2];
    this.location = Mappable.stringToLatLong(location);
  }

  @Override
  public void render() {
    System.out.printf("Render %s National Park as POINT(%s)\n", name, location());
  }

  public String location() {
    return Arrays.toString(location);
  }
}

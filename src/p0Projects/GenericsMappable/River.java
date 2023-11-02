package p0Projects.GenericsMappable;

import java.util.Arrays;

public class River extends Line {
  String name;

  public River(String name, String[] locations) {
    this.name = name;
    this.locations = new double[locations.length][2];
    for (int i = 0; i < locations.length; i++) {
      this.locations[i] = Mappable.stringToLatLong(locations[i]);
    }
  }

  @Override
  public void render() {
    System.out.printf("Render %s River as LINE(%s)\n", name, locations());
  }

  public String locations() {
    return Arrays.deepToString(locations);
  }
}

package p0Projects.GenericsMappable;

public interface Mappable {
  void render();

  static double[] stringToLatLong(String str) {
    String[] strArray = str.split(",");
    double[] latLongArray = new double[strArray.length];
    for (int i = 0; i < strArray.length; i++) {
      latLongArray[i] = Double.parseDouble(strArray[i]);
    }
    return latLongArray;
  }
}
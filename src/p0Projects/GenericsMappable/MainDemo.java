package p0Projects.GenericsMappable;

public class MainDemo {
  public static void main(String[] args) {
    // US National Parks
    Park yellowstone = new Park("Yellowstone", "44.4882, -110.5916");
    Park grandCanyon = new Park("Grand Canyon", "36.0636, -112.1079");
    Park yosemite = new Park("Yosemite", "37.8855, -119.5360");

    // US National Rivers
    River coloradoRiver = new River("Colorado",
        new String[] { "40.4708, -105.8286", "36.1015, -112.0892", "37.8855, -119.5360", "31.7811, -114.9046" });
    River mississippiRiver = new River("Mississippi",
        new String[] { "47.2160, -95.2348", "35.1556, -90.0485", "29.1556, -89.0485" });
    River missouriRiver = new River("Missouri", new String[] { "45.9239, -111.4983", "38.8146, -90.1218" });
    River delawareRiver = new River("Delaware", new String[] { "42.2026, -75.00836", "39.4955, -75.5592" });

    // Creating & Rendering of Park Layer
    Layer<Park> parkLayer = new Layer<>();
    parkLayer.addElement(yellowstone);
    parkLayer.addElement(grandCanyon);
    parkLayer.addElement(yosemite);
    parkLayer.renderLayer();

    // Creating & Rendering of River Layer
    Layer<River> riverLayer = new Layer<>();
    riverLayer.addElement(coloradoRiver);
    riverLayer.addElement(mississippiRiver);
    riverLayer.addElement(missouriRiver);
    riverLayer.addElement(delawareRiver);
    riverLayer.renderLayer();
  }
}

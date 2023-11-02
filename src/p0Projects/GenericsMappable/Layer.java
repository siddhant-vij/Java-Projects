package p0Projects.GenericsMappable;

import java.util.ArrayList;
import java.util.List;

public class Layer<T extends Mappable> {
  private List<T> layerElements = new ArrayList<>();

  public void addElement(T element) {
    layerElements.add(element);
  }

  public void renderLayer() {
    for (T element : layerElements) {
      element.render();
    }
  }
}

package p9Reflection.DependencyInjection.game;

class E {
  private G g;

  E(G g) {
    this.g = g;
  }

  public G getG() {
    return g;
  }

  public void setG(G g) {
    this.g = g;
  }

  @Override
  public String toString() {
    return "E [g=" + g + "]";
  }
}

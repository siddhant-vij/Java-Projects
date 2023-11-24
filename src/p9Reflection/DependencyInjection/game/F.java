package p9Reflection.DependencyInjection.game;

class F {
  private G g;

  F(G g) {
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
    return "F [g=" + g + "]";
  }
}

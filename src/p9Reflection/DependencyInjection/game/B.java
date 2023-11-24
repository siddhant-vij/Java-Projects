package p9Reflection.DependencyInjection.game;

class B {
  private G g;

  B(G g) {
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
    return "B [g=" + g + "]";
  }
}

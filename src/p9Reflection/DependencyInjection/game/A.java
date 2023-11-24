package p9Reflection.DependencyInjection.game;

class A {
  private G g;

  A(G g) {
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
    return "A [g=" + g + "]";
  }
}

package p9Reflection.DependencyInjection.game;

class C {
  private F f;

  C(F f) {
    this.f = f;
  }

  public F getF() {
    return f;
  }

  public void setF(F f) {
    this.f = f;
  }

  @Override
  public String toString() {
    return "C [f=" + f + "]";
  }
}

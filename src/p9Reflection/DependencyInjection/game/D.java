package p9Reflection.DependencyInjection.game;

class D {
  private E e;

  public D(E e) {
    this.e = e;
  }

  public E getE() {
    return e;
  }

  public void setE(E e) {
    this.e = e;
  }

  @Override
  public String toString() {
    return "D [e=" + e + "]";
  }
}

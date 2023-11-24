package p9Reflection.DependencyInjection.game;

/*
 * Dependency Graph for Game (A, B, C, D, E, F, G)
 * G <- F
 * G <- E
 * E <- D
 * F <- C
 * G <- B
 * G <- A
 * A, B, C, D <- Game
 */

public class Game {
  private A a;
  private B b;
  private C c;
  private D d;
  
  public Game(A a, B b, C c, D d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }

  public A getA() {
    return a;
  }

  public void setA(A a) {
    this.a = a;
  }

  public B getB() {
    return b;
  }

  public void setB(B b) {
    this.b = b;
  }

  public C getC() {
    return c;
  }

  public void setC(C c) {
    this.c = c;
  }

  public D getD() {
    return d;
  }

  public void setD(D d) {
    this.d = d;
  }

  @Override
  public String toString() {
    return "Game [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
  }
}

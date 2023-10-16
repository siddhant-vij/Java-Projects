package p6Threads;

import java.util.Random;
import java.util.concurrent.Exchanger;

class MakeString implements Runnable {
  String str;
  Exchanger<String> exchanger;
  int num;

  MakeString(Exchanger<String> exchanger, int num) {
    this.str = new String();
    this.exchanger = exchanger;
    this.num = num;
  }

  @Override
  public void run() {
    StringBuilder sb = new StringBuilder();
    char ch = 'A';
    Random random = new Random();

    for (int i = 0; i < num; i++) {
      for (int j = 0; j < random.nextInt(10); j++) {
        sb.append((char) (ch++));
      }
      str = sb.toString();
      try {
        str = exchanger.exchange(str);
        sb.setLength(0);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class UseString implements Runnable {
  String str;
  Exchanger<String> exchanger;
  int num;

  UseString(Exchanger<String> exchanger, int num) {
    this.exchanger = exchanger;
    this.num = num;
  }

  @Override
  public void run() {
    for (int i = 0; i < num; i++) {
      try {
        str = exchanger.exchange(str);
        System.out.println(str);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

public class ExchangerDemo {
  public static void main(String[] args) {
    Exchanger<String> exchanger = new Exchanger<>();
    int num = 3;
    new Thread(new MakeString(exchanger, num)).start();
    new Thread(new UseString(exchanger, num)).start();
  }
}

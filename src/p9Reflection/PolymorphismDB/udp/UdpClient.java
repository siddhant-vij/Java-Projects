package p9Reflection.PolymorphismDB.udp;

public class UdpClient {
  public void sendAndForget(String data) {
    System.out.println(String.format("Request with body: %s sent via UDP", data));
  }
}

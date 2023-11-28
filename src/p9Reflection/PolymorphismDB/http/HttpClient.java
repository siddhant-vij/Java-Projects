package p9Reflection.PolymorphismDB.http;

public class HttpClient {
  private String serverAddress;

  public HttpClient(String serverAddress) {
    this.serverAddress = serverAddress;
  }

  public boolean sendRequest(String data) {
    System.out
        .println(String.format("Sending request with body: %s to server with address: %s", data, serverAddress));
    return true;
  }
}

package p9Reflection.DynamicProxy.external;

public interface HttpClient {
  void initialize();
  String sendRequest(String request);
}

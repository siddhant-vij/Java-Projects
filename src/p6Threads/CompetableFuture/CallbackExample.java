package p6Threads.CompetableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

class Delay {
  public static void delay(int seconds) {
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class WeatherEmail {
  public void startProcessing() {
    System.out.println("Started processing...");
  }

  public String getUserDetails(int seconds) {
    System.out.println("Getting user details...");
    Delay.delay(seconds);
    return "testUser";
  }

  public double getWeatherData(int seconds) {
    System.out.println("Getting weather data...");
    Delay.delay(seconds);
    return 20.0;
  }

  public void sendEmail(String userEmail, double temperature) {
    System.out.println("Sending email to " + userEmail + "...");
    System.out.println("Temperature is " + temperature);
  }
}

public class CallbackExample {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    /*
     * Example 1: Synchronous method calls
     */
    WeatherEmail weatherEmail = new WeatherEmail();
    weatherEmail.startProcessing();
    String userName = weatherEmail.getUserDetails(2);
    double temperature = weatherEmail.getWeatherData(3);
    weatherEmail.sendEmail(userName, temperature);

    System.out.println("------------------------");

    /*
     * Example 2: Asynchronous method calls
     */
    weatherEmail.startProcessing();
    CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(
        () -> weatherEmail.getUserDetails(2)).thenApply(name -> {
          System.out.println("Got user name: " + name);
          return name;
        });
    CompletableFuture<Double> weatherFuture = CompletableFuture.supplyAsync(
        () -> weatherEmail.getWeatherData(2)).thenApply(temp -> {
          System.out.println("Got temperature: " + temp);
          return temp;
        });
    userName = userFuture.get();
    temperature = weatherFuture.get();
    weatherEmail.sendEmail(userName, temperature);

    System.out.println("------------------------");

    /*
     * Example 3: Asynchronous method calls using thenCombine
     */
    weatherEmail.startProcessing();
    CompletableFuture<String> userFuture2 = CompletableFuture.supplyAsync(
        () -> weatherEmail.getUserDetails(2));
    CompletableFuture<Double> weatherFuture2 = CompletableFuture.supplyAsync(
        () -> weatherEmail.getWeatherData(2));
    userName = userFuture2.thenCombine(weatherFuture2, (name, temp) -> {
      System.out.println("Got user name: " + name);
      System.out.println("Got temperature: " + temp);
      weatherEmail.sendEmail(name, temp);
      return name;
    }).get();
  }
}

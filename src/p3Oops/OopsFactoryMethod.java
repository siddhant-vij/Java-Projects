// The factory method is a creational design pattern, i.e., related to object creation.
// In the Factory pattern, we create objects without exposing the creation logic to the client and the client uses the same common interface to create a new type of object.
// The idea is to use a static member-function (static factory method) that creates & returns instances, hiding the details of class modules from the user.
// A factory pattern is one of the core design principles to create an object, allowing clients to create objects of a library(explained below) in a way such that it doesn’t have a tight coupling with the class hierarchy of the library.
// What is meant when we talk about libraries and clients? 
// A library is something that is provided by some third party that exposes some public APIs and clients make calls to those public APIs to complete their tasks. A very simple example can be different kinds of Views provided by Android OS.
// Example Statement: Consider we want to implement a notification service through email, SMS, and push notifications. We could have an interface called Notification, and three concrete classes are implementing the Notification interface. A factory class NotificationFactory is created to get a Notification object (similar to getMovie() implemented in Movie Class)

package p3Oops;

import java.util.Scanner;

class Movie {

  private String title;

  public Movie(String title) {
    this.title = title;
  }

  public void watchMovie() {

    String instanceType = this.getClass().getSimpleName();
    System.out.println(title + " is a " + instanceType + " film");
  }

  public static Movie getMovie(String type, String title) {

    return switch (type.toUpperCase().charAt(0)) {
      case 'A' -> new Adventure(title);
      case 'C' -> new Comedy(title);
      case 'S' -> new ScienceFiction(title);
      default -> new Movie(title);
    };
  }
}

class Adventure extends Movie {

  public Adventure(String title) {
    super(title);
  }

  @Override
  public void watchMovie() {
    super.watchMovie();
    System.out.printf(".. %s%n".repeat(3),
        "Pleasant Scene",
        "Scary Music",
        "Something Bad Happens");
  }
}

class Comedy extends Movie {

  public Comedy(String title) {
    super(title);
  }

  @Override
  public void watchMovie() {
    super.watchMovie();
    System.out.printf(".. %s%n".repeat(3),
        "Something funny happens",
        "Something even funnier happens",
        "Happy Ending");
  }
}

class ScienceFiction extends Movie {

  public ScienceFiction(String title) {
    super(title);
  }

  @Override
  public void watchMovie() {
    super.watchMovie();
    System.out.printf(".. %s%n".repeat(3),
        "Bad Aliens do Bad Stuff",
        "Space Guys Chase Aliens",
        "Planet Blows Up");
  }
}

public class OopsFactoryMethod {
  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      // Movie theMovie = Movie.getMovie("Science", "Star Wars");
      // theMovie.watchMovie();
      while (true) {
        System.out.print("Enter Type (A for Adventure, C for Comedy, " +
            "S for Science Fiction, or Q to quit): ");
        String type = sc.nextLine();
        if ("Qq".contains(type)) {
          break;
        }
        System.out.print("Enter Movie Title: ");
        String title = sc.nextLine();
        Movie movie = Movie.getMovie(type, title);
        movie.watchMovie();
      }
    }
  }
}

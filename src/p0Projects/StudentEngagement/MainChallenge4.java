package p0Projects.StudentEngagement;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainChallenge4 {
  private static final int MAX_STUDENTS = 10000;

  public static void main(String[] args) {
    Course[] courses = {
        new Course("CS101", "Intro to CS"),
        new Course("CS102", "Data Structures"),
        new Course("CS103", "Algorithms"),
        new Course("CS104", "Operating Systems"),
        new Course("CS105", "Software Engineering"),
        new Course("CS106", "Distributed Systems"),
        new Course("CS107", "Computer Networks"),
        new Course("CS108", "Artificial Intelligence"),
        new Course("CS109", "Cloud Computing"),
        new Course("CS110", "Big Data")
    };

    List<Student> students = Stream.generate(() -> Student
        .getRandomStudent(courses))
        .limit(MAX_STUDENTS)
        .collect(Collectors.toList());

    /*
     * Q1: How many students are enrolled in each course?
     */
    System.out.println("(1 - 1) No. of students in each course:");
    for (Course course : courses) {
      System.out.println(course.courseCode() + ": " + students
          .stream()
          .filter(student -> student.getEngagementMap()
              .containsKey(course.courseCode()))
          .count());
    }
    System.out.println("----------------------------");

    System.out.println("(1 - 2) No. of students in each course:");
    var mappedActivity = students.stream()
        .flatMap(student -> student.getEngagementMap().values().stream())
        .collect(Collectors.groupingBy(CourseEngagement::getCourseCode, Collectors.counting()));

    mappedActivity.forEach((key, value) -> {
      System.out.println(key + ": " + value);
    });
    System.out.println("----------------------------");

    /*
     * Q2: How many students are taking exactly 1 to 10 courses?
     */
    System.out.println("(2 - 1)No. of students taking exactly 1 to 10 courses:");
    for (int i = 1; i <= 10; i++) {
      final int shadow = i;
      System.out.println(i + " -> " + students
          .stream()
          .filter(student -> student.getEngagementMap()
              .size() == shadow)
          .count());
    }
    System.out.println("----------------------------");

    System.out.println("(2 - 2) No. of students taking exactly 1 to 10 courses:");
    var classCounts = students.stream()
        .collect(Collectors.groupingBy(
            student -> student.getEngagementMap().size(),
            Collectors.counting()));
    classCounts.forEach((key, value) -> {
      System.out.println(key + " -> " + value);
    });
    System.out.println("----------------------------");

    /*
     * Q3: Determine the average percentage complete, for all courses
     */
    System.out.println("Average percentage complete for all courses:");
    for (Course course : courses) {
      System.out.printf("%s: %.2f%%%n", course.courseCode(), students
          .stream()
          .mapToDouble(student -> student.getPercentComplete(course.courseCode()))
          .average()
          .getAsDouble());
    }
    System.out.println("----------------------------");
  }
}

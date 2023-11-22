package p0Projects.StudentEngagement;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainChallenge2 {
  private static final int MAX_STUDENTS = 1000;

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
        new Course("CS110", "Big Data"),
        new Course("CS111", "Machine Learning"),
        new Course("CS112", "Cybersecurity"),
        new Course("CS113", "Web Development"),
        new Course("CS114", "Software Testing"),
        new Course("CS115", "Computer Graphics"),
        new Course("CS116", "Mobile Development"),
        new Course("CS117", "Robotics"),
        new Course("CS118", "Data Mining"),
        new Course("CS119", "Natural Language Processing"),
        new Course("CS120", "Computer Vision")
    };

    List<Student> students = Stream.generate(() -> Student
        .getRandomStudent(courses))
        .limit(MAX_STUDENTS)
        .collect(Collectors.toList());

    /*
     * Q1: How many male and female students are in the group?
     */
    Predicate<Student> isMale = student -> student.getGender().equals("M");
    Predicate<Student> isFemale = student -> student.getGender().equals("F");
    Predicate<Student> isUndeclared = student -> student.getGender().equals("U");
    int numberOfMaleStudents = (int) students.stream()
        .filter(isMale)
        .count();
    int numberOfFemaleStudents = (int) students.stream()
        .filter(isFemale)
        .count();
    int numberOfUndeclaredStudents = (int) students.stream()
        .filter(isUndeclared)
        .count();
    System.out.println("Number of male students: " + numberOfMaleStudents);
    System.out.println("Number of female students: " + numberOfFemaleStudents);
    System.out.println("Number of undeclared students: " + numberOfUndeclaredStudents);

    System.out.println("-------------------------------------");

    /*
     * Q2: How many students fall into the three age ranges, less than age 30,
     * between 30 and 60, over 60 years old.
     */
    Predicate<Student> isUnder30 = student -> student.getAgeEnrolled() < 30;
    Predicate<Student> isBetween30And60 = student -> student
        .getAgeEnrolled() >= 30 && student.getAgeEnrolled() <= 60;
    Predicate<Student> isOver60 = student -> student.getAgeEnrolled() > 60;
    int numberOfUnder30 = (int) students.stream()
        .filter(isUnder30)
        .count();
    int numberOfBetween30And60 = (int) students.stream()
        .filter(isBetween30And60)
        .count();
    int numberOfOver60 = (int) students.stream()
        .filter(isOver60)
        .count();
    System.out.println("Number of students under 30: " + numberOfUnder30);
    System.out.println("Number of students between 30 and 60: " + numberOfBetween30And60);
    System.out.println("Number of students over 60: " + numberOfOver60);

    System.out.println("-------------------------------------");

    /*
     * Q3: Use summaryStatistics on student's age, to get a better idea of how old
     * the student population is.
     */

    System.out.print("Stats for Enrollment Age: ");
    System.out.println(students.stream()
        .mapToInt(Student::getAgeEnrolled)
        .summaryStatistics());

    System.out.println("-------------------------------------");

    System.out.print("Stats for Current Age: ");
    System.out.println(students.stream()
        .mapToInt(Student::getAge)
        .summaryStatistics());

    System.out.println("-------------------------------------");

    /*
     * Q4: What countries are the students from? Print a distinct list of the
     * country codes - along with the count of students from each country.
     */
    System.out.println("Country codes with student count: ");
    students.stream()
        .map(Student::getCountryCode)
        .distinct()
        .sorted()
        .forEach(country -> System.out.println(country + ": " + students.stream()
            .filter(student -> student.getCountryCode().equals(country))
            .count()));

    System.out.println("-------------------------------------");

    /*
     * Q5: Are there students that are still active, that have been enrolled for
     * more than 5 years? - Print the number of students & also, the year of the
     * enrollment & last activity for each active student.
     * Active = Months since last activity is less than 6 months
     */
    Predicate<Student> isActive = student -> student.isActive();
    Predicate<Student> hasBeenEnrolledForMoreThan5Years = student -> student
        .getYearEnrolled() < (LocalDate.now().getYear() - 5);
    int numberOfActiveStudents = (int) students.stream()
        .filter(isActive)
        .filter(hasBeenEnrolledForMoreThan5Years)
        .count();
    System.out.println("Number of active students, enrolled for more than 5 years: " + numberOfActiveStudents);

    System.out.println("-------------------------------------");
  }
}

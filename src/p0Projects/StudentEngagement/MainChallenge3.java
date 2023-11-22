package p0Projects.StudentEngagement;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainChallenge3 {
  private static final int MAX_STUDENTS = 5000;

  public static void main(String[] args) {
    Course[] courses = {
        new Course("CS101", "Intro to CS", 50),
        new Course("CS102", "Data Structures", 100)
    };

    List<Student> students = Stream.generate(() -> Student
        .getRandomStudent(courses))
        .limit(MAX_STUDENTS)
        .collect(Collectors.toList());

    /*
     * Q1: Use the getPercentComplete method to calculate the average percentage
     * completed for all students for the Intro to CS course.
     */
    double averagePercentComplete = students.stream()
        .mapToDouble(student -> student.getPercentComplete("CS101"))
        .reduce(0.0, Double::sum) / students.size();
    System.out.printf("Avg. %% completed for Intro to CS: %.2f%%%n", averagePercentComplete);

    System.out.println("----------------------------");

    /*
     * Q2: Use this result, multiplying it by 1.25, to collect a group of students
     * (either as a list, or a set). These would be the students who've completed
     * more than three quarters of that average percentage.
     */
    int topPercent = (int) (averagePercentComplete * 1.25);
    System.out.printf("Top percent: %d%%%n", topPercent);
    System.out.println("----------------------------");

    List<Student> selectedStudents = students.stream()
        .filter(student -> student.getPercentComplete("CS101") >= topPercent)
        .collect(Collectors.toList());
    System.out.println("Number of selected students: " + selectedStudents.size());
    System.out.println("----------------------------");

    /*
     * Q3: Sort by the longest enrolled students who are still active, because
     * you're going to offer your new course to 10 of these students, for a trial
     * run.
     */
    selectedStudents.sort(Comparator.comparingInt(Student::getYearsSinceEnrolled).reversed());
    List<Student> shortList = selectedStudents.stream()
        .filter(student -> student.isActive("CS101"))
        .collect(Collectors.toList());
    System.out.println("Number of shortlisted students: " + shortList.size());
    System.out.println("----------------------------");

    /*
     * Q4: Add the new course to these ten students
     */
    List<Student> trialList = shortList.stream()
        .limit(10)
        .collect(Collectors.toList());
    Course newCourse = new Course("CS103", "Algorithms", 100);
    trialList.forEach(student -> {
      student.addCourse(newCourse);
    });

    System.out.println("StudentID,PercentComplete,YearsSinceEnrolled,MonthsSinceActive,IsNewCourseAdded");

    trialList.forEach(student -> {
      System.out.println(student.getStudentId() + "," + student.getPercentComplete("CS101") + ","
          + student.getYearEnrolled() + "," + student.getMonthsSinceActive("CS101") + ","
          + isNewCoursePresent(student, newCourse));
    });

    System.out.println("----------------------------");
  }

  private static boolean isNewCoursePresent(Student student, Course course) {
    for (CourseEngagement ce : student.getEngagementMap().values()) {
      if (ce.getCourseCode().equals(course.courseCode())) {
        return true;
      }
    }
    return false;
  }
}

package p0Projects.StudentEngagement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Student {
  private static long lastStudentId = 1;
  private final static Random random = new Random();
  private final long studentId;
  private final String countryCode;
  private final int yearEnrolled;
  private final int ageEnrolled;
  private final String gender;
  private final boolean programmingExperience;
  private final Map<String, CourseEngagement> engagementMap = new HashMap<>();
  private static final int ORIGIN_YEAR = 2014;

  public Student(String countryCode, int yearEnrolled, int ageEnrolled, String gender,
      boolean programmingExperience, Course... courses) {
    studentId = lastStudentId++;
    this.countryCode = countryCode;
    this.yearEnrolled = yearEnrolled;
    this.ageEnrolled = ageEnrolled;
    this.gender = gender;
    this.programmingExperience = programmingExperience;
    for (Course course : courses) {
      addCourse(course, LocalDate.of(yearEnrolled, 1, 1));
    }
  }

  public void addCourse(Course newCourse) {
    addCourse(newCourse, LocalDate.now());
  }

  public void addCourse(Course newCourse, LocalDate enrollDate) {
    engagementMap.put(newCourse.courseCode(),
        new CourseEngagement(newCourse, enrollDate, "Enrollment"));
  }

  public long getStudentId() {
    return studentId;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public int getYearEnrolled() {
    return yearEnrolled;
  }

  public int getAgeEnrolled() {
    return ageEnrolled;
  }

  public String getGender() {
    return gender;
  }

  public boolean hasProgrammingExperience() {
    return programmingExperience;
  }

  public Map<String, CourseEngagement> getEngagementMap() {
    return Map.copyOf(engagementMap);
  }

  public int getYearsSinceEnrolled() {
    return LocalDate.now().getYear() - yearEnrolled;
  }

  public int getAge() {
    return ageEnrolled + getYearsSinceEnrolled();
  }

  public boolean isActive() {
    return getMonthsSinceActive() < 6;
  }

  public boolean isActive(String courseCode) {
    return getMonthsSinceActive(courseCode) < 6;
  }

  public int getMonthsSinceActive(String courseCode) {
    CourseEngagement info = engagementMap.get(courseCode);
    return info == null ? 0 : info.getMonthsSinceActive();
  }

  public int getMonthsSinceActive() {
    int inactiveMonths = (LocalDate.now().getYear() - ORIGIN_YEAR) * 12;
    for (String key : engagementMap.keySet()) {
      inactiveMonths = Math.min(inactiveMonths, getMonthsSinceActive(key));
    }
    return inactiveMonths;
  }

  public double getPercentComplete(String courseCode) {
    var info = engagementMap.get(courseCode);
    return (info == null) ? 0 : info.getPercentComplete();
  }

  public void watchLecture(String courseCode, int lectureNumber, int month, int year) {
    var activity = engagementMap.get(courseCode);
    if (activity != null) {
      activity.watchLecture(lectureNumber, LocalDate.of(year, month, 1));
    }
  }

  private static String getRandomVal(String... data) {
    return data[random.nextInt(data.length)];
  }

  public static Student getRandomStudent(Course... courses) {
    int maxYear = LocalDate.now().getYear() + 1;
    int numCourses = random.nextInt(1, Math.min(11, courses.length + 1));
    Set<Integer> selectedIndices = new HashSet<>();
    while (selectedIndices.size() < numCourses) {
      int randomIndex = random.nextInt(courses.length);
      selectedIndices.add(randomIndex);
    }
    Course[] selectedCourses = selectedIndices.stream()
        .map(index -> courses[index])
        .toArray(Course[]::new);
    Student student = new Student(
        getRandomVal("AU", "CA", "CN", "GB", "IN", "UA", "US"),
        random.nextInt(ORIGIN_YEAR + 1, maxYear),
        random.nextInt(18, 90),
        getRandomVal("M", "F", "U"),
        random.nextBoolean(),
        selectedCourses);
    for (Course c : selectedCourses) {
      int lecture = random.nextInt(1, c.lectureCount());
      int year = random.nextInt(student.getYearEnrolled(), maxYear);
      int month = random.nextInt(1, 13);
      if (year == (maxYear - 1)) {
        if (month > LocalDate.now().getMonthValue()) {
          month = LocalDate.now().getMonthValue();
        }
      }
      student.watchLecture(c.courseCode(), lecture, month, year);
    }
    return student;
  }

  @Override
  public String toString() {
    return "Student{" +
        "studentId=" + studentId +
        ", countryCode='" + countryCode + '\'' +
        ", yearEnrolled=" + yearEnrolled +
        ", ageEnrolled=" + ageEnrolled +
        ", gender='" + gender + '\'' +
        ", programmingExperience=" + programmingExperience +
        ", engagementMap=" + engagementMap +
        '}';
  }
}

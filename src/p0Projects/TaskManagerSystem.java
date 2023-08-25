// User Stories for Task Management System:
// 1. As a user, I should be able to create a new task with a title, description, and priority level so that I can add tasks to my list.
// 2. As a user, I should be able to see a list of all my tasks, sorted by priority or date created so that I can easily find and manage them.
// 3. As a user, I should be able to update the title, description, or priority of a task.
// 4. As a user, I should be able to delete a task from my list when I don't need it anymore.
// 5. As a user, I should be able to save my task list to a file so that I can load them the next time I open the application.
// 6. As a user, I should be informed gracefully if something goes wrong, such as saving to a file or reading from a file that doesn't exist.
// 7. As a user, I should be able to filter tasks by their priority or date created.
// 8. As a user, I should be able to see statistics, like the number of tasks per priority.
// 9. As a user, I should be able to perform bulk operations, like marking multiple tasks as complete.
// 10. As a user, if I accidentally delete or update a task, I should be able to undo the last action.

package p0Projects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

enum Priority {
  LOW, MEDIUM, HIGH
}

interface TaskFilter {
  boolean matches(Task task);
}

class InvalidTaskException extends Exception {
  public InvalidTaskException(String message) {
    super(message);
  }
}

class FilePersistenceException extends Exception {
  public FilePersistenceException(String message) {
    super(message);
  }
}

class PriorityFilter implements TaskFilter {
  private Priority priority;

  public PriorityFilter(Priority priority) {
    this.priority = priority;
  }

  @Override
  public boolean matches(Task task) {
    return task.getPriority() == priority;
  }
}

class DateFilter implements TaskFilter {
  private Date date;

  public DateFilter(Date date) {
    this.date = date;
  }

  @Override
  public boolean matches(Task task) {
    // Here we're just checking if the task was created on the given date.
    // You can modify this to be more granular if needed.
    return task.getCreatedDate().equals(date);
  }
}

class Task {
  private String title;
  private String description;
  private Priority priority;
  private Date createdDate;
  private boolean completed = false;

  // Constructor
  public Task(String title, String description, Priority priority) {
    this.title = title;
    this.description = description;
    this.priority = priority;
    this.createdDate = new Date(); // Assume this captures the current date-time
  }

  public Task(Task another) {
    this.title = another.title;
    this.description = another.description;
    this.priority = another.priority;
    this.createdDate = (Date) another.createdDate.clone(); // Clone the Date object
  }

  // Getter and Setter methods (encapsulation)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void markAsComplete() {
    this.completed = true;
  }

  public boolean isCompleted() {
    return completed;
  }

  public class TaskUpdater {

    public void updateTitle(String newTitle) {
      title = newTitle;
    }

    public void updateDescription(String newDescription) {
      description = newDescription;
    }

    public void updatePriority(Priority newPriority) {
      priority = newPriority;
    }
  }

  public TaskUpdater getUpdater() {
    return new TaskUpdater();
  }

  @Override
  public String toString() {
    return "Title: " + title + ", Description: " + description + ", Priority: " + priority + ", Created on: "
        + createdDate;
  }
}

class TaskManager {
  private List<Task> tasks;
  private Stack<State> states = new Stack<>();

  public TaskManager() {
    try {
      this.tasks = TaskIO.loadTasks(); // Load tasks from file on startup
    } catch (FilePersistenceException e) {
      System.out.println(e.getMessage());
    }

    // Ensure tasks is initialized even if there was an exception
    if (this.tasks == null) {
      this.tasks = new ArrayList<>();
    }
  }

  public void createTask(String title, String description, Priority priority) {
    Task task = new Task(title, description, priority);

    // Ensure tasks is not null before adding
    if (this.tasks == null) {
      this.tasks = new ArrayList<>();
    }

    tasks.add(task);
  }

  public boolean deleteTask(Task task) {
    return tasks.remove(task);
  }

  public Task getTaskByIndex(int index) {
    return tasks.get(index);
  }

  public List<Task> getAllTasks() {
    return tasks;
  }

  public List<Task> getTasksSortedByPriority() {
    tasks.sort(Comparator.comparingInt(task -> task.getPriority().ordinal()));
    return tasks;
  }

  public List<Task> getTasksSortedByDate() {
    tasks.sort(Comparator.comparing(Task::getCreatedDate));
    return tasks;
  }

  public List<Task> filterTasks(TaskFilter filter) {
    return tasks.stream()
        .filter(filter::matches)
        .collect(Collectors.toList());
  }

  public void saveState() {
    states.push(new State(tasks));
  }

  public void undoLastAction() {
    if (!states.isEmpty()) {
      State prevState = states.pop();
      tasks = prevState.getTasksSnapshot();
    } else {
      System.out.println("No actions to undo.");
    }
  }

  public void saveTasksToFile() {
    try {
      TaskIO.saveTasks(tasks);
      System.out.println("Tasks saved successfully!");
    } catch (FilePersistenceException e) {
      System.out.println(e.getMessage());
    }
  }

  public class BulkOperations {
    public void markTasksAsComplete(List<Integer> taskIndices) {
      for (Integer index : taskIndices) {
        if (index >= 0 && index < tasks.size()) {
          Task task = tasks.get(index);
          task.markAsComplete();
        }
      }
    }

    // Add more bulk operations methods here in the future as needed
  }
}

class TaskIO {

  private static final String FILENAME = "./src/p0Projects/tasks.txt";

  public static void saveTasks(List<Task> tasks) throws FilePersistenceException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
      for (Task task : tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getTitle()).append("|")
            .append(task.getDescription()).append("|")
            .append(task.getPriority().toString()).append("|")
            .append(task.getCreatedDate().toString());
        writer.println(sb.toString());
      }
    } catch (IOException e) {
      throw new FilePersistenceException("Error saving tasks: " + e.getMessage());
    }
  }

  public static List<Task> loadTasks() throws FilePersistenceException {
    List<Task> tasks = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split("\\|");
        Task task = new Task(parts[0], parts[1], Priority.valueOf(parts[2]));
        tasks.add(task);
      }
    } catch (IOException e) {
      throw new FilePersistenceException("Error loading tasks: " + e.getMessage());
    }

    return tasks;
  }
}

class TaskStatistics {
  private List<Task> tasks;

  public TaskStatistics(List<Task> tasks) {
    this.tasks = tasks;
  }

  public Map<Priority, Integer> countTasksByPriority() {
    Map<Priority, Integer> countByPriority = new HashMap<>();

    for (Task task : tasks) {
      countByPriority.put(task.getPriority(),
          countByPriority.getOrDefault(task.getPriority(), 0) + 1);
    }

    return countByPriority;
  }

  // You can add more statistical methods in the future - ifneeded.
}

class State {
  private List<Task> tasksSnapshot;

  public State(List<Task> tasks) {
    this.tasksSnapshot = new ArrayList<>();
    for (Task task : tasks) {
      // Create a deep copy of each task
      this.tasksSnapshot.add(new Task(task));
    }
  }

  public List<Task> getTasksSnapshot() {
    return tasksSnapshot;
  }
}

public class TaskManagerSystem {

  private static TaskManager taskManager = new TaskManager();
  private static final Scanner SCANNER = new Scanner(System.in);

  private static String getUserInput(String prompt) {
    System.out.print(prompt);
    String input = "";
    while (true) {
      input = SCANNER.nextLine();
      if (input.isEmpty()) {
        System.out.println("Input error. Please try again.");
      } else {
        break;
      }
    }
    return input;
  }

  private static void createTask() {
    taskManager.saveState();

    String title = "";
    String description = "";
    Priority priority = null;

    while (title.isEmpty()) {
      title = getUserInput("Enter task title: ");
      if (title.isEmpty()) {
        System.out.println("Title cannot be empty!");
      }
    }

    while (description.isEmpty()) {
      description = getUserInput("Enter task description: ");
      if (description.isEmpty()) {
        System.out.println("Description cannot be empty!");
      }
    }

    while (priority == null) {
      try {
        String priorityInput = getUserInput("Enter task priority (LOW, MEDIUM, HIGH): ");
        priority = Priority.valueOf(priorityInput.toUpperCase());
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid priority. Please choose from LOW, MEDIUM, or HIGH.");
      }
    }

    taskManager.createTask(title, description, priority);
  }

  private static void viewAllTasks() {
    System.out.println("Select sorting method: ");
    System.out.println("1. By Priority");
    System.out.println("2. By Creation Date");
    System.out.print("Enter choice: ");

    int choice = 0;
    try {
      choice = Integer.parseInt(SCANNER.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter a number.");
    }

    List<Task> tasks;

    switch (choice) {
      case 1:
        tasks = taskManager.getTasksSortedByPriority();
        break;
      case 2:
        tasks = taskManager.getTasksSortedByDate();
        break;
      default:
        System.out.println("Invalid choice. Sorting by creation date.");
        tasks = taskManager.getTasksSortedByDate();
    }

    if (tasks.isEmpty()) {
      System.out.println("No tasks available.");
      return;
    }

    for (Task task : tasks) {
      System.out.println(task);
    }
  }

  private static void updateTask() {
    taskManager.saveState();

    if (taskManager.getAllTasks().isEmpty()) {
      System.out.println("No tasks available to update.");
      return;
    }

    System.out.println("Select a task to update:");

    List<Task> tasks = taskManager.getAllTasks();
    for (int i = 0; i < tasks.size(); i++) {
      System.out.println((i + 1) + ". " + tasks.get(i));
    }

    int taskIndex = 0;
    try {
      taskIndex = Integer.parseInt(SCANNER.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter a number.");
      return; // Return to main menu or you can prompt again
    }

    Task selectedTask = tasks.get(taskIndex - 1);
    Task.TaskUpdater updater = selectedTask.getUpdater();

    boolean continueUpdating = true;

    while (continueUpdating) {
      System.out.println("Choose an attribute to update:");
      System.out.println("1. Title");
      System.out.println("2. Description");
      System.out.println("3. Priority");
      System.out.println("0. Go back");

      int choice = 0;
      try {
        choice = Integer.parseInt(SCANNER.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }

      switch (choice) {
        case 1:
          System.out.println("Enter new title:");
          String newTitle = SCANNER.nextLine();
          updater.updateTitle(newTitle);
          break;
        case 2:
          System.out.println("Enter new description:");
          String newDescription = SCANNER.nextLine();
          updater.updateDescription(newDescription);
          break;
        case 3:
          System.out.println("Select new priority (1. LOW, 2. MEDIUM, 3. HIGH):");
          int priorityChoice = 0;
          try {
            priorityChoice = Integer.parseInt(SCANNER.nextLine());
          } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
          }
          switch (priorityChoice) {
            case 1:
              updater.updatePriority(Priority.LOW);
              break;
            case 2:
              updater.updatePriority(Priority.MEDIUM);
              break;
            case 3:
              updater.updatePriority(Priority.HIGH);
              break;
            default:
              System.out.println("Invalid priority choice.");
          }
          break;
        case 0:
          continueUpdating = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private static void deleteTask() {
    taskManager.saveState();

    if (taskManager.getAllTasks().isEmpty()) {
      System.out.println("No tasks available to delete.");
      return;
    }

    System.out.println("Select a task to delete:");

    List<Task> tasks = taskManager.getAllTasks();
    for (int i = 0; i < tasks.size(); i++) {
      System.out.println((i + 1) + ". " + tasks.get(i));
    }

    int taskIndex = 0;
    try {
      taskIndex = Integer.parseInt(SCANNER.nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter a number.");
      return;
    }

    Task selectedTask = taskManager.getTaskByIndex(taskIndex - 1);
    if (taskManager.deleteTask(selectedTask)) {
      System.out.println("Task deleted successfully!");
    } else {
      System.out.println("Failed to delete the task.");
    }
  }

  private static void filterTasks() {
    System.out.println("Filter by: ");
    System.out.println("1. Priority");
    System.out.println("2. Date created");

    int choice = Integer.parseInt(getUserInput("Enter choice: "));
    List<Task> filteredTasks = new ArrayList<>();

    switch (choice) {
      case 1:
        System.out.println("Select Priority (LOW, MEDIUM, HIGH): ");
        Priority priority = Priority.valueOf(getUserInput("").toUpperCase());
        filteredTasks = taskManager.filterTasks(new PriorityFilter(priority));
        break;
      case 2:
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
          Date date = sdf.parse(getUserInput("Enter date (yyyy-MM-dd): "));
          filteredTasks = taskManager.filterTasks(new DateFilter(date));
        } catch (ParseException e) {
          System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
          return; // Return to the main menu
        }
        break;
      default:
        System.out.println("Invalid choice! Please choose a valid option.");
        return;
    }

    // Display the filtered tasks (this is a simple print, you can enhance the
    // display as needed)
    for (Task task : filteredTasks) {
      System.out.println(task.getTitle() + " - " + task.getDescription());
    }
  }

  private static void displayTaskStatistics() {
    TaskStatistics statistics = new TaskStatistics(taskManager.getAllTasks());
    Map<Priority, Integer> countsByPriority = statistics.countTasksByPriority();

    System.out.println("Task counts by priority:");
    for (Map.Entry<Priority, Integer> entry : countsByPriority.entrySet()) {
      System.out.printf("%s: %d tasks\n", entry.getKey(), entry.getValue());
    }
  }

  private static List<Integer> getMultipleTaskIndices() {
    System.out.println("Enter task indices separated by commas (e.g., 0,2,3): ");
    String[] indexStrings = getUserInput("").split(",");
    List<Integer> indices = new ArrayList<>();
    for (String indexString : indexStrings) {
      indices.add(Integer.parseInt(indexString.trim()));
    }
    return indices;
  }

  private static void performBulkOperations() {
    System.out.println("1. Mark multiple tasks as complete");
    // Add more bulk operation options as needed in the future

    int choice = Integer.parseInt(getUserInput("Select an operation: "));
    switch (choice) {
      case 1:
        taskManager.saveState();
        List<Integer> indices = getMultipleTaskIndices();
        taskManager.new BulkOperations().markTasksAsComplete(indices);
        System.out.println("Selected tasks have been marked as complete.");
        break;

      // Handle other bulk operations in future cases
    }
  }

  private static void clearScreen() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static boolean shouldClearScreen() {
    String response = getUserInput("Would you like to clear the screen? (yes/no): ");
    return response.trim().equalsIgnoreCase("yes");
  }

  private static void displayMenu() {
    System.out.println("------ Task Manager ------");
    System.out.println("1. Create a new task");
    System.out.println("2. View all tasks");
    System.out.println("3. Update a task");
    System.out.println("4. Delete a task");
    System.out.println("5. Save tasks to file");
    System.out.println("6. Filter tasks");
    System.out.println("7. Display task statistics");
    System.out.println("8. Perform bulk operations on tasks");
    System.out.println("9. Undo last action");
    // ... you can add other options here, like deleting a task, marking as
    // completed, etc. ...
    System.out.println("0. Exit");
  }

  public static void main(String[] args) {
    boolean continueRunning = true;
    while (continueRunning) {
      if (shouldClearScreen())
        clearScreen();
      displayMenu();

      int choice;
      try {
        choice = Integer.parseInt(getUserInput("Please choose an option: "));
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
        continue; // Move to the next iteration of the loop to prompt the user again
      }

      switch (choice) {
        case 1:
          createTask();
          break;
        case 2:
          viewAllTasks();
          break;
        case 3:
          updateTask();
          break;
        case 4:
          deleteTask();
          break;
        case 5:
          taskManager.saveTasksToFile();
          break;
        case 6:
          filterTasks();
          break;
        case 7:
          displayTaskStatistics();
          break;
        case 8:
          performBulkOperations();
          break;
        case 9:
          taskManager.undoLastAction();
          System.out.println("Last action undone.");
          break;
        // ... other cases ...
        case 0:
          continueRunning = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}

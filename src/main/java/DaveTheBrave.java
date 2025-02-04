import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Task {
    private String type; // "T" = To-Do, "D" = Deadline, "E" = Events
    private String task;
    private boolean status; // false = not done, true = done
    private String details; // Other info for Deadline and Event tasks

    public Task(String type, String task, String details) {
        this.type = type;
        this.task = task;
        this.status = false;
        this.details = details;
    }

    public void mark() {
        this.status = true;
    }

    public void unmark() {
        this.status = false;
    }

    public String getType() {
        return type;
    }

    public String getTask() {
        return task;
    }

    public boolean getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        String taskStatus = status ? "[X]" : "[ ]";
        String taskDetails = details != null ? " " + details : "";
        return "[" + type + "]" + taskStatus + " " + task + (taskDetails);
    }

    // Writing to file
    public String toFileFormat() {
        return type + " | " + (status ? "[X]" : "[ ]") + " | " + task + (details != null ? " | " + details : "");
    }

    // Loading from file
    public static Task fromFileFormat(String line) {
        String[] info = line.split("\\s*\\|\\s*");
        if (info.length < 3) {
            return null; // Ignore invalid lines
        }
        String type = info[0];
        boolean status = info[1].equals("[X]");
        String task = info[2];
        String details = info.length > 3 ? info[3] : null;
        Task loadedTask = new Task(type, task, details);
        if (status) {
            loadedTask.mark();
        }
        return loadedTask;
    }

}

class TaskManager {
    private List<Task> tasks;
    private static String FILE_PATH = Paths.get("data", "davethebrave.txt").toString();

    public TaskManager() {
        this.tasks = new ArrayList<>();
        checkFileExists();
        loadTasksFromFile();
        System.out.println("Previous tasks loaded: ");
        listTasks();
    }

    public void addTask(String type, String task, String details) {
        tasks.add(new Task(type, task, details));
        saveTasksToFile();
        System.out.println("    ____________________________________________________________");
        System.out.println("      Got it. I've added this task:");
        System.out.println("         " + tasks.getLast());
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }

    public void deleteTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task removedTask = tasks.remove(taskNumber - 1);
            saveTasksToFile();
            System.out.println("    ____________________________________________________________");
            System.out.println("      Noted. I've removed this task:");
            System.out.println("         " + removedTask);
            System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("    ____________________________________________________________");
        }
    }

    public void listTasks() {
        System.out.println("    ____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println("      No tasks to display.");
        } else {
            System.out.println("      Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("      " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("    ____________________________________________________________");
    }

    public void markTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task task = tasks.get(taskNumber - 1);
            if (!task.toString().contains("[X]")) {
                task.mark();
                saveTasksToFile();
                System.out.println("    ____________________________________________________________");
                System.out.println("      Nice! I've marked this task as done:");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      This task is already marked as done:");
            }
            System.out.println("         " + task);
            System.out.println("    ____________________________________________________________");
        }
    }

    public void unmarkTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task task = tasks.get(taskNumber - 1);
            if (task.toString().contains("[X]")) {
                task.unmark();
                saveTasksToFile();
                System.out.println("    ____________________________________________________________");
                System.out.println("      OK, I've marked this task as not done yet:");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      This task is already marked as not done:");
            }
            System.out.println("         " + task);
            System.out.println("    ____________________________________________________________");
        }
    }

    private boolean isValidTaskNumber(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("    ____________________________________________________________");
            System.out.println("      Invalid task number.");
            System.out.println("    ____________________________________________________________");
            return false;
        }
        return true;
    }

    private void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.println(task.toFileFormat());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    private void loadTasksFromFile() {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))){
            while (scanner.hasNextLine()) {
                Task task = Task.fromFileFormat(scanner.nextLine());
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    // Check if folder and file exists. Create folder and/or file if not.
    private void checkFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }
}

public class DaveTheBrave {
    public static void main(String[] args) {
        String name = "DaveTheBrave";
        TaskManager taskManager = new TaskManager();
        List<String> greetings = Arrays.asList("hello", "hi", "hey", "yo");
        List<String> goodbyes = Arrays.asList("bye", "goodbye");

        // Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Initial greeting
        System.out.println("    ____________________________________________________________");
        System.out.printf("      Boooo! I'm %s%n", name);
        System.out.println("      What can I do for you?");
        System.out.println("    ____________________________________________________________");

        while (true) {
            // Read user input
            String userInput = scanner.nextLine();

            // Exit when user types the command 'bye'
            if (goodbyes.contains(userInput.toLowerCase())) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Bye! Hope I didn't scare you away!");
                System.out.println("    ____________________________________________________________");
                break;
            }

            // Additional greetings
            if (greetings.contains(userInput.toLowerCase())) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Hey, what's up?");
                System.out.println("    ____________________________________________________________");
                continue;
            }

            // Display list when requested
            if (userInput.equalsIgnoreCase("list")) {
                taskManager.listTasks();
            }
            // Add To-Do tasks
            else if (userInput.toLowerCase().startsWith("todo ")) {
                String todoInfo = userInput.substring(5).trim();
                if (!todoInfo.isEmpty()) {
                    taskManager.addTask("T", todoInfo, null);
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Invalid format. Use: todo <task>");
                    System.out.println("    ____________________________________________________________");
                }
            }
            // Add Deadline tasks
            else if (userInput.toLowerCase().startsWith("deadline ")) {
                String[] deadlineInfo = userInput.substring(9).split(" /by", 2);
                if (deadlineInfo.length == 2) {
                    String task = deadlineInfo[0].trim();
                    String deadline = deadlineInfo[1].trim();
                    taskManager.addTask("D", task, "(by: " + deadline + ")");
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Invalid format. Use: deadline <task> /by <deadline date/time>");
                    System.out.println("    ____________________________________________________________");
                }
            }
            // Add Event tasks
            else if (userInput.toLowerCase().startsWith("event ")) {
                String[] eventInfo = userInput.substring(6).split(" /start | /end ", 3);
                if (eventInfo.length == 3) {
                    String task = eventInfo[0].trim();
                    String start = eventInfo[1].trim();
                    String end = eventInfo[2].trim();
                    taskManager.addTask("E", task, "(start: " + start + ", end: " + end + ")");
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Invalid format. Use: event <task> /start <start date/time> /end <end date/time>");
                    System.out.println("    ____________________________________________________________");
                }
            }
            // Delete tasks
            else if (userInput.toLowerCase().startsWith("delete ")) {
                if (userInput.substring(7).trim().isEmpty()) {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Task Number cannot be empty.");
                    System.out.println("    ____________________________________________________________");
                    continue;
                }
                int taskNumber = Integer.parseInt(userInput.substring(7).trim());
                taskManager.deleteTask(taskNumber);
            }
            // Mark tasks as done
            else if (userInput.toLowerCase().startsWith("mark ")) {
                if (userInput.substring(5).trim().isEmpty()) {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Task Number cannot be empty.");
                    System.out.println("    ____________________________________________________________");
                    continue;
                }
                int taskNumber = Integer.parseInt(userInput.substring(5).trim());
                taskManager.markTask(taskNumber);
            }
            // Unmark tasks as not done
            else if (userInput.toLowerCase().startsWith("unmark ")) {
                if (userInput.substring(7).trim().isEmpty()) {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Task Number cannot be empty.");
                    System.out.println("    ____________________________________________________________");
                    continue;
                }
                int taskNumber = Integer.parseInt(userInput.substring(7).trim());
                taskManager.unmarkTask(taskNumber);
            }
            // Handle Invalid Commands
            else {
                System.out.println("    ____________________________________________________________");
                System.out.println("    --Invalid Command--");
                System.out.println("      Add to list");
                System.out.println("            'todo':         todo <task>");
                System.out.println("            'deadline':     deadline <task> /by <deadline date/time");
                System.out.println("            'event':        event <task> /start <start date/time> /end <end date/time>");
                System.out.println("      View list");
                System.out.println("            'list':         list");
                System.out.println("      Mark/Unmark tasks in list");
                System.out.println("            'mark':         mark <task>");
                System.out.println("            'unmark':       unmark <task>");
                System.out.println("    ____________________________________________________________");
            }
        }
        // Close scanner
        scanner.close();
    }
}

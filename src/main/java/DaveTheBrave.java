import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Ui {
    public void showWelcome() {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Boooo! I'm DaveTheBrave");
        System.out.println("      What can I do for you?");
        System.out.println("    ____________________________________________________________");
    }

    public void respondHello() {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Hey, what's up?");
        System.out.println("    ____________________________________________________________");
    }

    public void showGoodbye() {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Bye! Hope I didn't scare you away!");
        System.out.println("    ____________________________________________________________");
    }

    public void showTaskAdded(List<Task> tasks) {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Got it. I've added this task:");
        System.out.println("         " + tasks.getLast());
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }

    public void showTaskDeleted(Task removedTask, List<Task> tasks) {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Noted. I've removed this task:");
        System.out.println("         " + removedTask);
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}
class Task {
    private String type; // "T" = To-Do, "D" = Deadline, "E" = Events
    private String task;
    private boolean status; // false = not done, true = done
    private String details; // Info for Event tasks
    private LocalDate deadline; // Info for Deadline tasks

    public static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Task(String type, String task, String details) {
        this.type = type;
        this.task = task;
        this.status = false;
        this.details = details;

        if (type.equals("D") && details != null) {
            try {
                this.deadline = LocalDate.parse(details.trim(), INPUT_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use yyyy-MM-dd (e.g., 2019-10-15).");
            }
        }
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
        if (type.equals("D")) {
            String taskDetails = details != null ? " (by: " + deadline.format(OUTPUT_FORMATTER) + ")" : "";
            return "[" + type + "]" + taskStatus + " " + task + taskDetails;
        } else {
            String taskDetails = details != null ? " " + details : "";
            return "[" + type + "]" + taskStatus + " " + task + (taskDetails);
        }
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

class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        checkFileExists();
    }

    public void saveTasksToFile(List<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.println(task.toFileFormat());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public List<Task> loadTasksFromFile() {
        List<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))){
            while (scanner.hasNextLine()) {
                Task task = Task.fromFileFormat(scanner.nextLine());
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    // Check if folder and file exists. Create folder and/or file if not.
    public void checkFileExists() {
        File file = new File(filePath);
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

class TaskManager {
    private List<Task> tasks;
    private Storage storage;
    private Ui ui;
//    private static String FILE_PATH = Paths.get("data", "davethebrave.txt").toString();

    public TaskManager(List<Task> tasks, Storage storage, Ui ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;
        storage.checkFileExists();
        storage.loadTasksFromFile();
        System.out.println("Previous tasks loaded: ");
        listTasks();
    }
    public void addTask(String type, String task, String details) {
        if (type.equals("D") && details != null) {
            try {
                LocalDate.parse(details.trim(), Task.INPUT_FORMATTER);
                System.out.println("Added deadline: " + task + " (by: " + details + ")");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use yyyy-MM-dd (e.g., 2019-10-15).");
                return;
            }
        }
        tasks.add(new Task(type, task, details));
        storage.saveTasksToFile(tasks);
        ui.showTaskAdded(tasks);
    }

    public void deleteTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task removedTask = tasks.remove(taskNumber - 1);
            storage.saveTasksToFile(tasks);
            ui.showTaskDeleted(removedTask, tasks);
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
                storage.saveTasksToFile(tasks);
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
                storage.saveTasksToFile(tasks);
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
}

class Parser {
    public static void parseCommand(String command, TaskManager taskManager) {
        // Display list when requested
        if (command.equalsIgnoreCase("list")) {
            taskManager.listTasks();
        }
        // Add To-Do tasks
        else if (command.toLowerCase().startsWith("todo ")) {
            String todoInfo = command.substring(5).trim();
            if (!todoInfo.isEmpty()) {
                taskManager.addTask("T", todoInfo, null);
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Invalid format. Use: todo <task>");
                System.out.println("    ____________________________________________________________");
            }
        }
        // Add Deadline tasks
        else if (command.toLowerCase().startsWith("deadline ")) {
            String[] deadlineInfo = command.substring(9).split(" /by", 2);
            if (deadlineInfo.length == 2) {
                String task = deadlineInfo[0].trim();
                String deadline = deadlineInfo[1].trim();
                taskManager.addTask("D", task, deadline);
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Invalid format. Use: deadline <task> /by yyyy-MM-dd");
                System.out.println("    ____________________________________________________________");
            }
        }
        // Add Event tasks
        else if (command.toLowerCase().startsWith("event ")) {
            String[] eventInfo = command.substring(6).split(" /start | /end ", 3);
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
        else if (command.toLowerCase().startsWith("delete ")) {
            if (command.substring(7).trim().isEmpty()) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Task Number cannot be empty.");
                System.out.println("    ____________________________________________________________");
            }
            int taskNumber = Integer.parseInt(command.substring(7).trim());
            taskManager.deleteTask(taskNumber);
        }
        // Mark tasks as done
        else if (command.toLowerCase().startsWith("mark ")) {
            if (command.substring(5).trim().isEmpty()) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Task Number cannot be empty.");
                System.out.println("    ____________________________________________________________");
            }
            int taskNumber = Integer.parseInt(command.substring(5).trim());
            taskManager.markTask(taskNumber);
        }
        // Unmark tasks as not done
        else if (command.toLowerCase().startsWith("unmark ")) {
            if (command.substring(7).trim().isEmpty()) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Task Number cannot be empty.");
                System.out.println("    ____________________________________________________________");
            }
            int taskNumber = Integer.parseInt(command.substring(7).trim());
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
            System.out.println("      Delete task from list");
            System.out.println("            'delete':       delete <task number>");
            System.out.println("    ____________________________________________________________");
        }
    }
}

public class DaveTheBrave {
    private Storage storage;
    private TaskManager taskManager;
    private Ui ui;

    public DaveTheBrave(String filePath) {
        ui = new Ui();

        storage = new Storage(filePath);
        taskManager = new TaskManager(storage.loadTasksFromFile(), storage, ui);
    }

    public void run() {
        List<String> greetings = Arrays.asList("hello", "hi", "hey", "yo");
        List<String> goodbyes = Arrays.asList("bye", "goodbye");

        // Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        ui.showWelcome();

        while (true) {
            // Read user input
            String command = scanner.nextLine();

            // Exit when user types the command 'bye'
            if (goodbyes.contains(command.toLowerCase())) {
                ui.showGoodbye();
                break;
            }

            // Additional greetings
            if (greetings.contains(command.toLowerCase())) {
                ui.respondHello();
                continue;
            }

            Parser.parseCommand(command, taskManager);
        }
        // Close scanner
        scanner.close();
    }
    public static void main(String[] args) {
        new DaveTheBrave("data/davethebrave.txt").run();
    }
}
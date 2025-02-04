package utils;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;

/**
 * Manages the list of tasks, including adding, removing, updating, and saving/loading tasks.
 * This class interacts with the file system to persist task data and provides methods to
 * display task-related messages through the Printer class.
 */
public class TaskManager {

    private final List<Task> tasks;
    private final Printer printer;
    private final String filePath = "./data/tasks.txt";

    /**
     * Constructs a new TaskManager and initializes the task list.
     * Loads tasks from the file at startup.
     */
    public TaskManager() {
        tasks = new ArrayList<>();
        printer = new Printer();
        loadTasks();
    }

    /**
     * Saves the current tasks to a file.
     * If the data directory does not exist, it is created automatically.
     */
    public void saveTasks() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();  // Create the data directory if it doesn't exist
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Task task : tasks) {
                writer.write(task.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the file. If the file does not exist, no tasks are loaded.
     */
    public void loadTasks() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseAndAddTask(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    /**
     * Parses a line from the task file and adds the corresponding task to the list.
     * The method handles both completed and incomplete tasks.
     *
     * @param line The line representing a task in the saved file.
     */
    private void parseAndAddTask(String line) {
        try {
            if (line.isBlank()) {
                return;  // Skip empty lines
            }

            String taskType = line.substring(1, 2);  // Get the task type: T, D, or E
            boolean isDone = line.charAt(4) == 'X';  // Get the completion status
            String taskDetails = line.substring(7).trim();  // Get the task description/details

            switch (taskType) {
            case "T":
                ToDo todo = new ToDo(taskDetails);
                if (isDone) todo.markAsDone();
                tasks.add(todo);
                break;
            case "D":
                if (!taskDetails.contains("(by: ")) {
                    throw new IllegalArgumentException("Missing deadline information");
                }
                String[] deadlineParts = taskDetails.split("\\(by: ", 2);
                String description = deadlineParts[0].trim();
                String deadline = deadlineParts[1].replace(")", "").trim();
                Deadline deadlineTask = new Deadline(description, deadline);
                if (isDone) deadlineTask.markAsDone();
                tasks.add(deadlineTask);
                break;
            case "E":
                if (!taskDetails.contains("from: ") || !taskDetails.contains(" to: ")) {
                    throw new IllegalArgumentException("Missing event information");
                }
                String[] eventParts = taskDetails.split("from: | to: ");
                String eventDescription = eventParts[0].trim();
                String start = eventParts[1].trim();
                String end = eventParts[2].trim();
                Event eventTask = new Event(eventDescription, start, end);
                if (isDone) eventTask.markAsDone();
                tasks.add(eventTask);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
            }
        } catch (Exception e) {
            System.out.println("Skipping malformed task line: " + line);
        }
    }

    /**
     * Adds a new ToDo task to the list.
     *
     * @param description The description of the ToDo task.
     */
    public void addToDo(String description) {
        Task newTask = new ToDo(description);
        tasks.add(newTask);
        saveTasks();
        printer.printTaskAdded(description, tasks.size());
    }

    /**
     * Adds a new Deadline task to the list.
     *
     * @param description The description of the Deadline task.
     * @param deadline    The deadline associated with the task.
     */
    public void addDeadline(String description, String deadline) {
        Task newTask = new Deadline(description, deadline);
        tasks.add(newTask);
        saveTasks();
        printer.printTaskAdded(description, tasks.size());
    }

    /**
     * Adds a new Event task to the list.
     *
     * @param description The description of the Event task.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public void addEvent(String description, String from, String to) {
        Task newTask = new Event(description, from, to);
        tasks.add(newTask);
        saveTasks();
        printer.printTaskAdded(description, tasks.size());
    }

    /**
     * Prints the list of tasks.
     */
    public void listTasks() {
        printer.printTaskList(tasks);
    }

    /**
     * Deletes a task from the list based on its index.
     *
     * @param taskNumber The 1-based index of the task to be deleted.
     */
    public void deleteTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.remove(taskNumber - 1);
            saveTasks();
            printer.printTaskDeleted(task, tasks.size());
        } else {
            printer.printInvalidTaskNumber();
        }
    }

    /**
     * Marks a task as completed.
     *
     * @param taskNumber The 1-based index of the task to be marked.
     */
    public void markTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.markAsDone();
            saveTasks();
            printer.printTaskMarked(task);
        } else {
            printer.printInvalidTaskNumber();
        }
    }

    /**
     * Unmarks a task (sets it to incomplete).
     *
     * @param taskNumber The 1-based index of the task to be unmarked.
     */
    public void unmarkTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.markAsNotDone();
            saveTasks();
            printer.printTaskUnmarked(task);
        } else {
            printer.printInvalidTaskNumber();
        }
    }
}

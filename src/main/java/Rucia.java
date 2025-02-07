import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;

/**
 * Rucia is a personal assistant chatbot that helps users with basic commands.
 * This class handles greeting the user, managing tasks, providing help, and exiting when requested.
 */
public class Rucia {

    // Constants for commands
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_ADD = "add ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_DELETE = "delete ";
    private static final String COMMAND_CLEAR = "clear";

    private static final String DATA_FILE = "./data/tasks.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = loadTasksFromFile();
        boolean isInteractive = System.console() != null; // Check if running interactively

        // Greeting message
        System.out.println("========================================");
        System.out.println("Hello! I'm Rucia");
        System.out.println("How can I assist you today?");
        System.out.println("Send \"Help\" or \"?\" for instructions on how to use me!");
        System.out.println("========================================");

        // Process user input until "bye" is entered
        while (true) {
            if (isInteractive) {
                System.out.print("You: "); // Only print this in interactive mode
            }
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase(COMMAND_BYE)) {
                break;
            } else if (input.equalsIgnoreCase(COMMAND_HELP) || input.equals("?")) {
                System.out.println("Rucia: Here is how you can use me:");
                System.out.println("1) add <task> - Add a new ToDo task to your list.");
                System.out.println("2) deadline <task> /by <date> - Add a Deadline task.");
                System.out.println("3) event <task> /from <start> /to <end> - Add an Event task.");
                System.out.println("4) list - View all your tasks.");
                System.out.println("5) mark <number> - Mark the corresponding task as complete.");
                System.out.println("6) unmark <number> - Mark the corresponding task as incomplete.");
                System.out.println("7) delete <number> - Delete the corresponding task from the list.");
                System.out.println("8) clear - Clear all tasks from the list.");
                System.out.println("9) bye - Exit the chatbot.");
            } else if (input.equalsIgnoreCase(COMMAND_CLEAR)) {
                tasks.clear();
                saveTasksToFile(tasks);
                System.out.println("Rucia: All tasks have been cleared.");
            } else if (input.startsWith(COMMAND_ADD)) {
                String taskDescription = input.substring(COMMAND_ADD.length());
                tasks.add(new ToDo(taskDescription));
                saveTasksToFile(tasks);
                System.out.println("Rucia: Added ToDo task - " + taskDescription);
                System.out.println("Rucia: You now have " + tasks.size() + " task(s) in your list.");
            } else if (input.startsWith(COMMAND_DEADLINE)) {
                try {
                    String[] parts = input.substring(COMMAND_DEADLINE.length()).split(" /by ");
                    tasks.add(new Deadline(parts[0], parts[1]));
                    saveTasksToFile(tasks);
                    System.out.println("Rucia: Added Deadline task - " + parts[0] + " (by: " + parts[1] + ")");
                    System.out.println("Rucia: You now have " + tasks.size() + " task(s) in your list.");
                } catch (Exception e) {
                    System.out.println("Rucia: Invalid format. Use: deadline <task> /by <date>");
                }
            } else if (input.startsWith(COMMAND_EVENT)) {
                try {
                    String[] parts = input.substring(COMMAND_EVENT.length()).split(" /from ");
                    String description = parts[0];
                    String[] timeParts = parts[1].split(" /to ");
                    tasks.add(new Event(description, timeParts[0], timeParts[1]));
                    saveTasksToFile(tasks);
                    System.out.println("Rucia: Added Event task - " + description + " (from: " + timeParts[0] + " to: " + timeParts[1] + ")");
                    System.out.println("Rucia: You now have " + tasks.size() + " task(s) in your list.");
                } catch (Exception e) {
                    System.out.println("Rucia: Invalid format. Use: event <task> /from <start> /to <end>");
                }
            } else if (input.equalsIgnoreCase(COMMAND_LIST)) {
                System.out.println("Rucia: Here are your tasks:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                if (tasks.isEmpty()) {
                    System.out.println("No tasks added yet.");
                }
            } else if (input.startsWith(COMMAND_MARK)) {
                try {
                    int index = Integer.parseInt(input.substring(COMMAND_MARK.length())) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        saveTasksToFile(tasks);
                        System.out.println("Rucia: Marked as done - " + tasks.get(index));
                    } else {
                        System.out.println("Rucia: Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Rucia: Please enter a valid task number.");
                }
            } else if (input.startsWith(COMMAND_UNMARK)) {
                try {
                    int index = Integer.parseInt(input.substring(COMMAND_UNMARK.length())) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        saveTasksToFile(tasks);
                        System.out.println("Rucia: Marked as not done - " + tasks.get(index));
                    } else {
                        System.out.println("Rucia: Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Rucia: Please enter a valid task number.");
                }
            } else if (input.startsWith(COMMAND_DELETE)) {
                try {
                    int index = Integer.parseInt(input.substring(COMMAND_DELETE.length())) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removedTask = tasks.remove(index);
                        saveTasksToFile(tasks);
                        System.out.println("Rucia: Deleted task - " + removedTask);
                        System.out.println("Rucia: You now have " + tasks.size() + " task(s) in your list.");
                    } else {
                        System.out.println("Rucia: Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Rucia: Please enter a valid task number.");
                }
            } else {
                System.out.println("Rucia: OOPS! I don't recognize that command. Please type \"Help\" or \"?\" to see the list of available commands.");
            }
        }

        // Exit message
        System.out.println("========================================");
        System.out.println("Rucia: Bye. Hope to see you again soon!");
        System.out.println("========================================");

        scanner.close();
    }

    private static void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs(); // Ensure the data folder exists
            FileWriter writer = new FileWriter(file);

            for (Task task : tasks) {
                writer.write(task.toString() + System.lineSeparator());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Rucia: Error saving tasks to file.");
        }
    }

    private static ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    tasks.add(parseTask(line));
                }
                fileScanner.close();
            }
        } catch (IOException e) {
            System.out.println("Rucia: Error loading tasks from file.");
        }

        return tasks;
    }

    private static Task parseTask(String line) {
        String type = line.substring(1, 2);
        boolean isDone = line.charAt(4) == 'X';
        String description = line.substring(7);

        Task task;
        if (type.equals("T")) {
            task = new ToDo(description);
        } else if (type.equals("D")) {
            String[] parts = description.split(" \\(by: ");
            task = new Deadline(parts[0], parts[1].replace(")", ""));
        } else if (type.equals("E")) {
            String[] parts = description.split(" \\(from: ");
            String[] timeParts = parts[1].replace(")", "").split(" to: ");
            task = new Event(parts[0], timeParts[0], timeParts[1]);
        } else {
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;
import ui.Ui;

/**
 * Rucia is a personal assistant chatbot that helps users with basic commands.
 * This class handles greeting the user, managing tasks, providing help, and exiting when requested.
 */
public class Rucia {

    // Enum for commands
    private enum Command {
        BYE("bye"),
        HELP("help"),
        ADD("add "),
        DEADLINE("deadline "),
        EVENT("event "),
        LIST("list"),
        LIST_DAY("list_day "),
        MARK("mark "),
        UNMARK("unmark "),
        DELETE("delete "),
        CLEAR("clear");

        private final String value;

        Command(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final String DATA_FILE = "./data/tasks.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * The main method to start the chatbot.
     * Handles user input and command execution.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ArrayList<Task> tasks = loadTasksFromFile();

        // Greeting message
        ui.showWelcome();

        // Process user input until "bye" is entered
        while (true) {
            String input = ui.readCommand();

            if (input.equalsIgnoreCase(Command.BYE.getValue())) {
                break;
            } else if (input.equalsIgnoreCase(Command.HELP.getValue()) || input.equals("?")) {
                ui.showMessage("Here is how you can use me:");
                ui.showMessage("1) add <task> - Add a new ToDo task to your list.");
                ui.showMessage("2) deadline <task> /by <date> - Add a Deadline task. (Format: dd/MM/yyyy HHmm)");
                ui.showMessage("3) event <task> /from <start> /to <end> - Add an Event task. (Format: dd/MM/yyyy HHmm)");
                ui.showMessage("4) list - View all your tasks.");
                ui.showMessage("5) list_day <date> - List all tasks for the specified day. (Format: dd/MM/yyyy)");
                ui.showMessage("6) mark <number> - Mark the corresponding task as complete.");
                ui.showMessage("7) unmark <number> - Mark the corresponding task as incomplete.");
                ui.showMessage("8) delete <number> - Delete the corresponding task from the list.");
                ui.showMessage("9) clear - Clear all tasks from the list.");
                ui.showMessage("10) bye - Exit the chatbot.");
            } else if (input.equalsIgnoreCase(Command.CLEAR.getValue())) {
                tasks.clear();
                saveTasksToFile(tasks);
                ui.showMessage("All tasks have been cleared.");
            } else if (input.startsWith(Command.ADD.getValue())) {
                String taskDescription = input.substring(Command.ADD.getValue().length());
                tasks.add(new ToDo(taskDescription));
                saveTasksToFile(tasks);
                ui.showMessage("Added ToDo task - " + taskDescription);
                ui.showMessage("You now have " + tasks.size() + " task(s) in your list.");
            } else if (input.startsWith(Command.DEADLINE.getValue())) {
                try {
                    String[] parts = input.substring(Command.DEADLINE.getValue().length()).split(" /by ");
                    String dateTimeString = parts[1].trim();
                    if (dateTimeString.length() == 10) { // Check if only date is provided
                        dateTimeString += " 1200"; // Append default time
                    }
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
                    tasks.add(new Deadline(parts[0], dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"))));
                    saveTasksToFile(tasks);
                    ui.showMessage("Added Deadline task - " + parts[0] + " (by: " + dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
                    ui.showMessage("You now have " + tasks.size() + " task(s) in your list.");
                } catch (DateTimeParseException e) {
                    ui.showError("Invalid date format. Use: dd/MM/yyyy HHmm (e.g., 02/03/2019 1800)");
                } catch (Exception e) {
                    ui.showError("Invalid format. Use: deadline <task> /by <date>");
                }
            } else if (input.startsWith(Command.EVENT.getValue())) {
                try {
                    String[] parts = input.substring(Command.EVENT.getValue().length()).split(" /from ");
                    String description = parts[0];
                    String[] timeParts = parts[1].split(" /to ");

                    String fromDateTimeString = timeParts[0].trim();
                    if (fromDateTimeString.length() == 10) { // Check if only date is provided
                        fromDateTimeString += " 1200"; // Append default time
                    }
                    LocalDateTime fromDateTime = LocalDateTime.parse(fromDateTimeString, DATE_TIME_FORMATTER);

                    String toDateTimeString = timeParts[1].trim();
                    if (toDateTimeString.length() == 10) { // Check if only date is provided
                        toDateTimeString += " 1200"; // Append default time
                    }
                    LocalDateTime toDateTime = LocalDateTime.parse(toDateTimeString, DATE_TIME_FORMATTER);

                    tasks.add(new Event(description, fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")), toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"))));
                    saveTasksToFile(tasks);
                    ui.showMessage("Added Event task - " + description + " (from: " + fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + " to: " + toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
                    ui.showMessage("You now have " + tasks.size() + " task(s) in your list.");
                } catch (DateTimeParseException e) {
                    ui.showError("Invalid date format. Use: dd/MM/yyyy HHmm (e.g., 02/03/2019 1800)");
                } catch (Exception e) {
                    ui.showError("Invalid format. Use: event <task> /from <start> /to <end>");
                }
            } else if (input.equalsIgnoreCase(Command.LIST.getValue())) {
                ui.showMessage("Here are your tasks:");
                for (int i = 0; i < tasks.size(); i++) {
                    ui.showMessage((i + 1) + ". " + tasks.get(i));
                }
                if (tasks.isEmpty()) {
                    ui.showMessage("No tasks added yet.");
                }
            } else if (input.startsWith(Command.MARK.getValue())) {
                try {
                    int index = Integer.parseInt(input.substring(Command.MARK.getValue().length())) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        saveTasksToFile(tasks);
                        ui.showMessage("Marked as done - " + tasks.get(index));
                    } else {
                        ui.showError("Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    ui.showError("Please enter a valid task number.");
                }
            } else if (input.startsWith(Command.UNMARK.getValue())) {
                try {
                    int index = Integer.parseInt(input.substring(Command.UNMARK.getValue().length())) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        saveTasksToFile(tasks);
                        ui.showMessage("Marked as not done - " + tasks.get(index));
                    } else {
                        ui.showError("Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    ui.showError("Please enter a valid task number.");
                }
            } else if (input.startsWith(Command.DELETE.getValue())) {
                try {
                    int index = Integer.parseInt(input.substring(Command.DELETE.getValue().length())) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task removedTask = tasks.remove(index);
                        saveTasksToFile(tasks);
                        ui.showMessage("Deleted task - " + removedTask);
                        ui.showMessage("You now have " + tasks.size() + " task(s) in your list.");
                    } else {
                        ui.showError("Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    ui.showError("Please enter a valid task number.");
                }
            } else if (input.startsWith(Command.LIST_DAY.getValue())) {
                try {
                    String dateString = input.substring(Command.LIST_DAY.getValue().length()).trim();
                    LocalDateTime date = LocalDateTime.parse(dateString + " 0000", DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
                    listTasksForDay(tasks, date);
                } catch (DateTimeParseException e) {
                    ui.showError("Invalid date format. Use: dd/MM/yyyy (e.g., 02/03/2019)");
                }
            } else {
                ui.showError("I don't recognize that command. Please type \"Help\" or \"?\" to see the list of available commands.");
            }
        }

        // Exit message
        ui.showExit();
    }

    /**
     * Saves the current list of tasks to a file.
     *
     * @param tasks The list of tasks to be saved.
     */
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

    /**
     * Loads tasks from the data file.
     *
     * @return The list of tasks loaded from the file.
     */
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

    /**
     * Parses a task from a string representation.
     *
     * @param line The string representation of the task.
     * @return The parsed Task object.
     */
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

    /**
     * Lists tasks that occur on a specific day.
     *
     * @param tasks The list of tasks to filter.
     * @param date The date to filter by.
     */
    private static void listTasksForDay(ArrayList<Task> tasks, LocalDateTime date) {
        System.out.println("Rucia: Here are your tasks for " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");
        int count = 0;
        for (Task task : tasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getByDateTime().toLocalDate().isEqual(date.toLocalDate())) {
                    System.out.println((count + 1) + ". " + task);
                    count++;
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.getFromDateTime().toLocalDate().isEqual(date.toLocalDate()) || event.getToDateTime().toLocalDate().isEqual(date.toLocalDate())) {
                    System.out.println((count + 1) + ". " + task);
                    count++;
                }
            }
        }
        if (count == 0) {
            System.out.println("No tasks found for this day.");
        }
    }
}
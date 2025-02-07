import java.util.Scanner;
import java.util.ArrayList;
import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;

/**
 * Rucia is a personal assistant chatbot that helps users with basic commands.
 * This class handles greeting the user, managing tasks, providing help, and exiting when requested.
 */
public class Rucia {
    /**
     * The main method that runs the Rucia chatbot.
     * It greets the user, manages tasks, provides help,
     * and exits when the command "bye" is received.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
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

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("help") || input.equals("?")) {
                System.out.println("Rucia: Here is how you can use me:");
                System.out.println("1) add <task> - Add a new ToDo task to your list.");
                System.out.println("2) deadline <task> /by <date> - Add a Deadline task.");
                System.out.println("3) event <task> /from <start> /to <end> - Add an Event task.");
                System.out.println("4) list - View all your tasks.");
                System.out.println("5) mark <number> - Mark the corresponding task as complete.");
                System.out.println("6) unmark <number> - Mark the corresponding task as incomplete.");
                System.out.println("7) bye - Exit the chatbot.");
            } else if (input.startsWith("add ")) {
                String taskDescription = input.substring(4);
                tasks.add(new ToDo(taskDescription));
                System.out.println("Rucia: Added ToDo task - " + taskDescription);
            } else if (input.startsWith("deadline ")) {
                try {
                    String[] parts = input.substring(9).split(" /by ");
                    tasks.add(new Deadline(parts[0], parts[1]));
                    System.out.println("Rucia: Added Deadline task - " + parts[0] + " (by: " + parts[1] + ")");
                } catch (Exception e) {
                    System.out.println("Rucia: Invalid format. Use: deadline <task> /by <date>");
                }
            } else if (input.startsWith("event ")) {
                try {
                    String[] parts = input.substring(6).split(" /from ");
                    String description = parts[0];
                    String[] timeParts = parts[1].split(" /to ");
                    tasks.add(new Event(description, timeParts[0], timeParts[1]));
                    System.out.println("Rucia: Added Event task - " + description + " (from: " + timeParts[0] + " to: " + timeParts[1] + ")");
                } catch (Exception e) {
                    System.out.println("Rucia: Invalid format. Use: event <task> /from <start> /to <end>");
                }
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("Rucia: Here are your tasks:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                if (tasks.isEmpty()) {
                    System.out.println("No tasks added yet.");
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        System.out.println("Rucia: Marked as done - " + tasks.get(index));
                    } else {
                        System.out.println("Rucia: Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Rucia: Please enter a valid task number.");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        System.out.println("Rucia: Marked as not done - " + tasks.get(index));
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
}

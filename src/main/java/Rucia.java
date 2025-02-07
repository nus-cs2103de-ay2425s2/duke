import java.util.Scanner;
import java.util.ArrayList;

/**
 * Rucia is a personal assistant chatbot that helps users with basic commands.
 * This class handles greeting the user, managing tasks, providing help, and exiting when requested.
 */
public class Rucia {

    static class Task {
        String description;
        boolean isDone;

        Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        void markAsDone() {
            this.isDone = true;
        }

        void markAsNotDone() {
            this.isDone = false;
        }

        @Override
        public String toString() {
            return (isDone ? "[X] " : "[ ] ") + description;
        }
    }

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

        // Greeting message
        System.out.println("========================================");
        System.out.println("Hello! I'm Rucia");
        System.out.println("How can I assist you today?");
        System.out.println("Send \"Help\" or \"?\" for instructions on how to use me!");
        System.out.println("========================================");

        // Process user input until "bye" is entered
        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.equalsIgnoreCase("help") || input.equals("?")) {
                System.out.println("Rucia: Here’s how you can use me:");
                System.out.println("1) add <task> - Add a new task to your list.");
                System.out.println("2) list - View all your tasks.");
                System.out.println("3) mark <number> - Mark the corresponding task as complete.");
                System.out.println("4) unmark <number> - Mark the corresponding task as incomplete.");
                System.out.println("5) bye - Exit the chatbot.");
            } else if (input.startsWith("add ")) {
                String taskDescription = input.substring(4);
                tasks.add(new Task(taskDescription));
                System.out.println("Rucia: Added task - " + taskDescription);
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
                System.out.println("Rucia: " + input);
            }
        }

        // Exit message
        System.out.println("========================================");
        System.out.println("Rucia: Bye. Hope to see you again soon!");
        System.out.println("========================================");

        scanner.close();
    }
}

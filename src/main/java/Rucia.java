import java.util.Scanner;
import java.util.ArrayList;

/**
 * Rucia is a personal assistant chatbot that helps users with basic commands.
 * This class handles greeting the user, echoing input, and exiting when requested.
 */
public class Rucia {

    /**
     * The main method that runs the Rucia chatbot.
     * It displays an ASCII art of "RUCIA", greets the user, echoes user input,
     * and exits when the command "bye" is received.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        // Display ASCII art
        System.out.println("______ _   _ _____ _____  ___  ");
        System.out.println("| ___ \\ | | /  __ \\_   _|/ _ \\ ");
        System.out.println("| |_/ / | | | /  \\/ | | / /_\\ \\");
        System.out.println("|    /| | | | |     | | |  _  |");
        System.out.println("| |\\ \\| |_| | \\__/\\_| |_| | | |");
        System.out.println("\\_| \\_|\\___/ \\____/\\___/\\_| |_/");

        // Greeting message
        System.out.println("========================================");
        System.out.println("Hello! I'm Rucia");
        System.out.println("How can I assist you today?");
        System.out.println("Start your message with \"add\" to add a task");
        System.out.println("========================================");

        // Process user input until "bye" is entered
        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            } else if (input.startsWith("add ")) {
                String task = input.substring(4);
                tasks.add(task);
                System.out.println("Rucia: Added task - " + task);
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("Rucia: Here are your tasks:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                if (tasks.isEmpty()) {
                    System.out.println("No tasks added yet.");
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
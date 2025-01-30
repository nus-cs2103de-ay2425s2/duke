import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class Dave {
    public static void main(String[] args) {
        String name = "Dave";

        // Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Initial greeting
        System.out.println("    ____________________________________________________________");
        System.out.printf("      Hello! I'm %s%n", name);
        System.out.println("      What can I do for you?");
        System.out.println("    ____________________________________________________________");

        List<String> greetings = Arrays.asList("hello", "hi", "hey", "yo");
        List<String> goodbyes = Arrays.asList("bye", "goodbye");
        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            // Read user input
            String userInput = scanner.nextLine();

            // Exit when user types the command 'bye'
            if (goodbyes.contains(userInput.toLowerCase())) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Bye. Hope to see you again soon!");
                System.out.println("    ____________________________________________________________");
                break;
            }

            if (greetings.contains(userInput.toLowerCase())) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Hey, what's up?");
                System.out.println("    ____________________________________________________________");
                continue;
            }

            // Display list when requested
            if (userInput.equalsIgnoreCase("list")) {
                System.out.println("    ____________________________________________________________");
                if (taskCount == 0) {
                    System.out.println("No tasks to display.");
                } else {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("    " + (i + 1) + ". " + tasks[i]);
                    }
                }
                System.out.println("    ____________________________________________________________");
                continue;
            }

            // Store text entered by user in list if less than 100 tasks
            if (taskCount < 100) {
                tasks[taskCount++] = userInput;
                System.out.println("    ____________________________________________________________");
                System.out.println("      added: " + userInput);
                System.out.println("    ____________________________________________________________");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Task list is full. Unable to add more tasks.");
                System.out.println("    ____________________________________________________________");
            }
            continue;
        }

        // Close scanner
        scanner.close();
    }
}

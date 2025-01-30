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

            // Echos commands entered by the user
            System.out.println("    ____________________________________________________________");
            System.out.println("      " + userInput);
            System.out.println("    ____________________________________________________________");
        }

        // Close scanner
        scanner.close();
    }
}

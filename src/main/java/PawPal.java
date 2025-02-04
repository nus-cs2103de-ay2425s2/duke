package pawpal;

import utils.TaskManager;
import utils.Printer;

import java.util.Scanner;

public class PawPal {
    public static void main(String[] args) {
        new PawPal().run();
    }

    private final Parser parser;

    public PawPal() {
        TaskManager taskManager = new TaskManager();
        this.parser = new Parser(taskManager);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Standard greeting message
        String name = "PawPal";
        System.out.println("-----------------------------------------");
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you? \n");
        System.out.println("-----------------------------------------");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon! Meow");
                break;
            }
            parser.parse(input);
        }
    }
}
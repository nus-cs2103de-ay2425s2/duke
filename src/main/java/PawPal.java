import java.io.IOException;
import java.util.Scanner;

import utils.TaskList;
import utils.Printer;
import utils.Storage;

/**
 * The main class for the PawPal chatbot application.
 * PawPal allows users to manage tasks such as ToDo, Deadline, and Event tasks.
 * It interacts with the user via the command line.
 */
public class PawPal {

    /**
     * The entry point of the PawPal application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new PawPal().run();
    }

    private final Parser parser;
    private final Printer printer;
    private final TaskList taskList;
    private final Storage storage;

    /**
     * Constructs a new PawPal instance.
     * Initializes the task manager, printer, and parser components.
     */
    public PawPal() {
        String filePath = "./data/tasks.txt";
        this.storage = new Storage(filePath);
        this.taskList = new TaskList();
        this.printer = new Printer();
        this.parser = new Parser(taskList);
    }

    /**
     * Runs the main loop of the PawPal chatbot.
     * Continuously reads user input, processes commands, and prints responses.
     * The loop exits when the user enters the "bye" command.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String name = "PawPal";
        printer.printGreeting(name);

        // Main input loop
        while (true) {
            String input = scanner.nextLine().trim();

            // Exit the application when the user types "bye"
            if (input.equalsIgnoreCase("bye")) {
                printer.printBye();
                break;
            }

            // Pass the user input to the parser for processing
            parser.parse(input);

            try {
                storage.saveTasks(taskList.getTasks());
            } catch (IOException e) {
                System.out.println("Error saving tasks: " + e.getMessage());
            }
        }
    }
}

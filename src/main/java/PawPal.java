import java.io.IOException;

import utils.Ui;
import utils.TaskList;
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
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructs a new PawPal instance.
     * Initializes the storage, task list, UI, and parser components.
     */
    public PawPal() {
        String filePath = "./data/tasks.txt";
        this.storage = new Storage(filePath);
        this.taskList = new TaskList();
        this.ui = new Ui();
        this.parser = new Parser(taskList);

        // Load tasks from storage
        try {
            taskList.getTasks().addAll(storage.loadTasks());
        } catch (IOException e) {
            ui.showLoadingError();
        }
    }

    /**
     * Runs the main loop of the PawPal chatbot.
     * Continuously reads user input, processes commands, and prints responses.
     * The loop exits when the user enters the "bye" command.
     */
    public void run() {
        ui.showGreeting();

        // Main input loop
        while (true) {
            String input = ui.readCommand();

            // Exit the application when the user types "bye"
            if (input.equalsIgnoreCase("bye")) {
                ui.showBye();
                break;
            }

            // Pass the user input to the parser for processing
            parser.parse(input);

            // Save tasks after each command
            try {
                storage.saveTasks(taskList.getTasks());
            } catch (IOException e) {
                ui.showSavingError();
            }
        }
    }
}

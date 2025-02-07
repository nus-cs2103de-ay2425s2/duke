package PawPal.core;

import java.io.IOException;

import PawPal.utils.Ui;
import PawPal.utils.TaskList;
import PawPal.utils.Storage;

/**
 * The main class for the PawPal.core.PawPal chatbot application.
 * PawPal.core.PawPal allows users to manage PawPal.core.PawPal.tasks such as ToDo, Deadline, and Event PawPal.core.PawPal.tasks.
 * It interacts with the user via the command line.
 */
public class PawPal {

    /**
     * The entry point of the PawPal.core.PawPal application.
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
     * Constructs a new PawPal.core.PawPal instance.
     * Initializes the storage, task list, UI, and parser components.
     */
    public PawPal() {
        String filePath = "./data/tasks.txt";
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage);
        this.ui = new Ui();
        this.parser = new Parser(taskList);
    }

    /**
     * Runs the main loop of the PawPal.core.PawPal chatbot.
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

            try {
                storage.saveTasks(taskList.getTasks());
            } catch (IOException e) {
                ui.showSavingError();
            }
        }
    }
}

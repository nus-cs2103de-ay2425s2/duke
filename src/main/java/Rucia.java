import utils.Parser;
import utils.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

/**
 * Rucia is a personal assistant chatbot that helps users with basic commands.
 * This class handles greeting the user, managing tasks, providing help, and exiting when requested.
 */
public class Rucia {

    /**
     * The main method to start the chatbot.
     * Handles user input and command execution.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList taskList = new TaskList(Storage.loadTasksFromFile());

        // Greeting message
        ui.showWelcome();

        // Process user input until "bye" is entered
        while (true) {
            String input = ui.readCommand();
            Parser.parseCommand(input, taskList, ui);
        }
    }
}
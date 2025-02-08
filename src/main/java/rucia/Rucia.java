// src/main/java/rucia/Rucia.java
package rucia;

import utils.CommandIdentifier;
import utils.CommandParser;
import utils.Storage;
import tasks.TaskList;
import ui.Ui;
import commands.Command;

import java.io.IOException;

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
        Storage storage = new Storage("data/tasks.txt");
        TaskList taskList;

        try {
            taskList = new TaskList(storage.loadTasksFromFile());
        } catch (IOException e) {
            ui.showMessage("Error loading tasks from file: " + e.getMessage());
            taskList = new TaskList();
        }

        // Greeting message
        ui.showWelcome();

        // Process user input until "bye" is entered
        while (true) {
            String input = ui.readCommand();
            String commandType = CommandIdentifier.identify(input);
            try {
                Command command = CommandParser.parse(input, commandType, storage);
                command.execute(taskList, ui);
            } catch (IllegalArgumentException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
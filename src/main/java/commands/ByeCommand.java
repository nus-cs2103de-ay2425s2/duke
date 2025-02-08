// src/main/java/commands/ByeCommand.java

package commands;

import tasks.TaskList;
import ui.Ui;

/**
 * The ByeCommand class implements the Command interface and is used to handle the "bye" command.
 * This command signals the application to exit.
 */
public class ByeCommand implements Command {

    /**
     * Executes the "bye" command, which shows the exit message and terminates the application.
     *
     * @param taskList The list of tasks (not used in this command).
     * @param ui The user interface to interact with the user.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        ui.showExit();
        System.exit(0);
    }
}
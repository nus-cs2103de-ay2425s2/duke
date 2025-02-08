// src/main/java/commands/Command.java

package commands;

import tasks.TaskList;
import ui.Ui;

/**
 * The Command interface defines the structure for all command classes.
 * Each command must implement the execute method to perform its specific action.
 */
public interface Command {

    /**
     * Executes the command with the given task list and user interface.
     *
     * @param taskList The list of tasks to be manipulated by the command.
     * @param ui The user interface to interact with the user.
     */
    void execute(TaskList taskList, Ui ui);
}
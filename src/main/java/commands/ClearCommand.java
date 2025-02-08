// src/main/java/commands/ClearCommand.java

package commands;

import tasks.TaskList;
import ui.Ui;
import utils.Storage;

import java.io.IOException;

/**
 * The ClearCommand class implements the Command interface and is used to handle the "clear" command.
 * This command clears all tasks from the task list and updates the storage.
 */
public class ClearCommand implements Command {
    private Storage storage;

    /**
     * Constructs a ClearCommand with the specified storage.
     *
     * @param storage The storage to update after clearing tasks.
     */
    public ClearCommand(Storage storage) {
        this.storage = storage;
    }

    /**
     * Executes the "clear" command, which clears all tasks from the task list and updates the storage.
     *
     * @param taskList The list of tasks to be cleared.
     * @param ui The user interface to interact with the user.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.clearTasks();
        try {
            storage.saveTasksToFile(taskList.getTasks());
            ui.showMessage("All tasks have been cleared.");
        } catch (IOException e) {
            ui.showMessage("Error clearing tasks: " + e.getMessage());
        }
    }
}
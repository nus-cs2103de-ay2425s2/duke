// src/main/java/commands/MarkCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;
import utils.Storage;
import java.io.IOException;

/**
 * Represents a command to mark a task as done in the task list.
 * Handles updating the task status, saving changes to storage,
 * and notifying the user.
 */
public class MarkCommand implements Command {
    private int taskIndex;
    private Storage storage;

    /**
     * Constructs a MarkCommand with the specified task index from user input
     * and initializes the storage handler.
     *
     * @param input   The user input containing the task number to mark as done.
     * @param storage The storage handler to save the updated task list.
     * @throws IllegalArgumentException if the input does not contain a valid task number.
     */
    public MarkCommand(String input, Storage storage) {
        try {
            this.taskIndex = Integer.parseInt(input.substring(4).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please provide a valid task number.");
        }
        this.storage = storage;
    }

    /**
     * Executes the mark command by marking the specified task as done,
     * saving the updated task list to storage, and displaying a confirmation message.
     * If the task is already marked as done, an appropriate message is shown.
     *
     * @param taskList The current task list containing tasks to be updated.
     * @param ui       The user interface for displaying messages.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        if (taskIndex < 0 || taskIndex >= taskList.getSize()) {
            ui.showMessage("Invalid task index.");
            return;
        }
        if (taskList.isTaskDone(taskIndex)) {
            ui.showMessage("Task is already marked as done.");
            return;
        }
        taskList.markTask(taskIndex);
        saveTasks(taskList, ui);
        ui.showMessage("Marked task as done - " + taskList.getTask(taskIndex));
    }

    /**
     * Saves the current tasks in the task list to persistent storage.
     * Displays an error message if the save operation fails.
     *
     * @param taskList The task list containing tasks to be saved.
     * @param ui       The user interface for displaying error messages.
     */
    private void saveTasks(TaskList taskList, Ui ui) {
        try {
            storage.saveTasksToFile(taskList.getTasks());
        } catch (IOException e) {
            ui.showMessage("Error saving tasks to file: " + e.getMessage());
        }
    }
}
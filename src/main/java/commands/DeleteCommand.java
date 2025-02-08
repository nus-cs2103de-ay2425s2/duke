// src/main/java/commands/DeleteCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;
import utils.Storage;
import java.io.IOException;

/**
 * Represents a command to delete a task from the task list.
 * Handles the removal of a task by its index and updates the storage accordingly.
 */
public class DeleteCommand implements Command {
    private int taskIndex;
    private Storage storage;

    /**
     * Constructs a DeleteCommand with the specified input string and storage handler.
     * Extracts the task index from the input.
     *
     * @param input   The user input containing the task number to delete.
     * @param storage The storage handler to update the task list after deletion.
     * @throws IllegalArgumentException if the input does not contain a valid task number.
     */
    public DeleteCommand(String input, Storage storage) {
        try {
            this.taskIndex = Integer.parseInt(input.substring(6).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please provide a valid task number.");
        }
        this.storage = storage;
    }

    /**
     * Executes the delete command by removing the specified task from the task list,
     * saving the updated list to storage, and displaying appropriate messages to the user.
     *
     * @param taskList The current task list from which the task will be deleted.
     * @param ui       The user interface for displaying messages.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        if (taskIndex < 0 || taskIndex >= taskList.getSize()) {
            ui.showMessage("Invalid task index.");
            return;
        }
        String taskDescription = taskList.getTask(taskIndex).getDescription();
        taskList.deleteTask(taskIndex);
        saveTasks(taskList, ui);
        ui.showMessage("Deleted task - " + taskDescription);
        ui.showMessage("You now have " + taskList.getSize() + " task(s) in your list.");
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
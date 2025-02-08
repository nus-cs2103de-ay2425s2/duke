// src/main/java/commands/AddCommand.java
package commands;

import tasks.TaskList;
import tasks.ToDo;
import ui.Ui;
import utils.Storage;
import java.io.IOException;

/**
 * Represents a command to add a ToDo task.
 */
public class AddCommand implements Command {
    private String taskDescription;
    private Storage storage;

    /**
     * Constructs an AddCommand with the specified task description and storage.
     *
     * @param taskDescription The description of the task to be added.
     * @param storage The storage to save the task.
     */
    public AddCommand(String taskDescription, Storage storage) {
        this.taskDescription = taskDescription;
        this.storage = storage;
    }

    /**
     * Executes the command to add a ToDo task to the task list and save it to storage.
     *
     * @param taskList The task list to add the task to.
     * @param ui The UI to interact with the user.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.addTask(new ToDo(taskDescription));
        try {
            storage.saveTasksToFile(taskList.getTasks());
        } catch (IOException e) {
            ui.showMessage("Error saving tasks to file: " + e.getMessage());
        }
        ui.showMessage("Added ToDo task - " + taskDescription);
        ui.showMessage("You now have " + taskList.getSize() + " task(s) in your list.");
    }
}
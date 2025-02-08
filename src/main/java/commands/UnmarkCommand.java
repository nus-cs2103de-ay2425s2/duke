// src/main/java/commands/UnmarkCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;
import utils.Storage;
import java.io.IOException;

public class UnmarkCommand implements Command {
    private int taskIndex;
    private Storage storage;

    public UnmarkCommand(String input, Storage storage) {
        try {
            this.taskIndex = Integer.parseInt(input.substring(6).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please provide a valid task number.");
        }
        this.storage = storage;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        if (taskIndex < 0 || taskIndex >= taskList.getSize()) {
            ui.showMessage("Invalid task index.");
            return;
        }
        if (!taskList.isTaskDone(taskIndex)) {
            ui.showMessage("Task is not marked as done.");
            return;
        }
        taskList.unmarkTask(taskIndex);
        try {
            storage.saveTasksToFile(taskList.getTasks());
        } catch (IOException e) {
            ui.showMessage("Error saving tasks to file: " + e.getMessage());
        }
        ui.showMessage("Unmarked task - " + taskList.getTask(taskIndex));
    }
}
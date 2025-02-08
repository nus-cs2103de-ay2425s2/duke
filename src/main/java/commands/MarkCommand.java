// src/main/java/commands/MarkCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;
import utils.Storage;
import java.io.IOException;

public class MarkCommand implements Command {
    private int taskIndex;
    private Storage storage;

    public MarkCommand(String input, Storage storage) {
        try {
            this.taskIndex = Integer.parseInt(input.substring(4).trim()) - 1;
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
        if (taskList.isTaskDone(taskIndex)) {
            ui.showMessage("Task is already marked as done.");
            return;
        }
        taskList.markTask(taskIndex);
        try {
            storage.saveTasksToFile(taskList.getTasks());
        } catch (IOException e) {
            ui.showMessage("Error saving tasks to file: " + e.getMessage());
        }
        ui.showMessage("Marked task as done - " + taskList.getTask(taskIndex));
    }
}
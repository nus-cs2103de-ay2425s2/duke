// src/main/java/commands/ClearCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;
import utils.Storage;

import java.io.IOException;

public class ClearCommand implements Command {
    private final Storage storage;

    public ClearCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.clearTasks();
        try {
            storage.saveTasksToFile(taskList.getTasks());
        } catch (IOException e) {
            ui.showMessage("Error saving tasks to file: " + e.getMessage());
        }
        ui.showMessage("All tasks have been cleared.");
    }
}
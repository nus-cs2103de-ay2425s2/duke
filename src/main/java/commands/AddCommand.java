// src/main/java/commands/AddCommand.java
package commands;

import tasks.TaskList;
import tasks.ToDo;
import ui.Ui;
import utils.Storage;

public class AddCommand implements Command {
    private String taskDescription;
    private Storage storage;

    public AddCommand(String taskDescription, Storage storage) {
        this.taskDescription = taskDescription;
        this.storage = storage;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        taskList.addTask(new ToDo(taskDescription));
        storage.saveTasksToFile(taskList.getTasks());
        ui.showMessage("Added ToDo task - " + taskDescription);
        ui.showMessage("You now have " + taskList.getSize() + " task(s) in your list.");
    }
}
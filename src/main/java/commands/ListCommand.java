// src/main/java/commands/ListCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;

public class ListCommand implements Command {

    @Override
    public void execute(TaskList taskList, Ui ui) {
        if (taskList.getSize() == 0) {
            ui.showMessage("Your task list is empty.");
        } else {
            ui.showMessage("Here are the tasks in your list:");
            for (int i = 0; i < taskList.getSize(); i++) {
                ui.showMessage((i + 1) + ". " + taskList.getTask(i));
            }
        }
    }
}
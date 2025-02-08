// src/main/java/commands/ListCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;

/**
 * Represents a command to display all tasks in the task list.
 * Lists each task with its corresponding index.
 */
public class ListCommand implements Command {

    /**
     * Executes the list command by displaying all tasks in the task list.
     * If the task list is empty, an appropriate message is shown.
     *
     * @param taskList The current task list containing tasks to be displayed.
     * @param ui       The user interface for displaying the task list.
     */
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
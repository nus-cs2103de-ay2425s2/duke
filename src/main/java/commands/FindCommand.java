// src/main/java/commands/FindCommand.java
package commands;

import tasks.Task;
import tasks.TaskList;
import ui.Ui;
import utils.Storage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The FindCommand class implements the Command interface and is responsible for
 * finding tasks that contain a specific keyword in their description.
 */
public class FindCommand implements Command {
    private String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the FindCommand, which searches for tasks containing the keyword
     * and displays the matching tasks.
     *
     * @param taskList The task list to search within.
     * @param ui The UI instance used to display messages.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        List<Task> matchingTasks = taskList.getTasks().stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toList());

        if (matchingTasks.isEmpty()) {
            ui.showMessage("No tasks found with the keyword: " + keyword);
        } else {
            ui.showMessage("Tasks found with the keyword: " + keyword);
            for (Task task : matchingTasks) {
                ui.showMessage(task.toString());
            }
        }
    }
}
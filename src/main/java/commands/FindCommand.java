// src/main/java/commands/FindCommand.java
package commands;

import tasks.Task;
import tasks.TaskList;
import ui.Ui;
import utils.Storage;

import java.util.List;
import java.util.stream.Collectors;

public class FindCommand implements Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

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
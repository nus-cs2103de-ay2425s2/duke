// src/main/java/commands/Command.java
package commands;

import tasks.TaskList;
import ui.Ui;

public interface Command {
    void execute(TaskList taskList, Ui ui);
}
// src/main/java/commands/ByeCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;

public class ByeCommand implements Command {

    @Override
    public void execute(TaskList taskList, Ui ui) {
        ui.showExit();
        System.exit(0);
    }
}
// src/main/java/commands/DeadlineCommand.java
package commands;

import tasks.TaskList;
import tasks.Deadline;
import ui.Ui;
import utils.Storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineCommand implements Command {
    private String taskDescription;
    private String dateTimeString;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public DeadlineCommand(String taskDescription, String dateTimeString) {
        this.taskDescription = taskDescription;
        this.dateTimeString = dateTimeString;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        try {
            if (dateTimeString.length() == 10) {
                dateTimeString += " 1200";
            }
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
            taskList.addTask(new Deadline(taskDescription, dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"))));
            Storage.saveTasksToFile(taskList.getTasks());
            ui.showMessage("Added Deadline task - " + taskDescription + " (by: " + dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
            ui.showMessage("You now have " + taskList.getSize() + " task(s) in your list.");
        } catch (DateTimeParseException e) {
            ui.showError("Invalid date format. Use: dd/MM/yyyy HHmm (e.g., 02/03/2019 1800)");
        }
    }
}
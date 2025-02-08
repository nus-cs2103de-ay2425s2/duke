// src/main/java/commands/DeadlineCommand.java
package commands;

import tasks.TaskList;
import tasks.Deadline;
import ui.Ui;
import utils.Storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineCommand implements Command {
    private String taskDescription;
    private String dateTimeString;
    private Storage storage;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public DeadlineCommand(String taskDescription, String dateTimeString, Storage storage) {
        this.taskDescription = taskDescription;
        this.dateTimeString = dateTimeString;
        this.storage = storage;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        try {
            if (dateTimeString.length() == 10) {
                dateTimeString += " 1200";
            }
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString.trim(), DATE_TIME_FORMATTER);
            taskList.addTask(new Deadline(taskDescription, dateTime.toEpochSecond(ZoneOffset.UTC)));
            try {
                storage.saveTasksToFile(taskList.getTasks());
            } catch (IOException e) {
                ui.showMessage("Error saving tasks to file: " + e.getMessage());
            }
            ui.showMessage("Added Deadline task - " + taskDescription + " (by: " + dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
            ui.showMessage("You now have " + taskList.getSize() + " task(s) in your list.");
        } catch (DateTimeParseException e) {
            ui.showError("Invalid date format. Use: dd/MM/yyyy HHmm (e.g., 02/03/2019 1800)");
        }
    }
}
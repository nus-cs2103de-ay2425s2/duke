// src/main/java/commands/EventCommand.java
package commands;

import tasks.TaskList;
import tasks.Event;
import ui.Ui;
import utils.Storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventCommand implements Command {
    private String description;
    private String fromDateTimeString;
    private String toDateTimeString;
    private Storage storage;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public EventCommand(String description, String fromDateTimeString, String toDateTimeString, Storage storage) {
        this.description = description;
        this.fromDateTimeString = fromDateTimeString;
        this.toDateTimeString = toDateTimeString;
        this.storage = storage;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        try {
            if (fromDateTimeString.length() == 10) {
                fromDateTimeString += " 1200";
            }
            LocalDateTime fromDateTime = LocalDateTime.parse(fromDateTimeString, DATE_TIME_FORMATTER);

            if (toDateTimeString.length() == 10) {
                toDateTimeString += " 1200";
            }
            LocalDateTime toDateTime = LocalDateTime.parse(toDateTimeString, DATE_TIME_FORMATTER);

            taskList.addTask(new Event(description, fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")), toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"))));
            storage.saveTasksToFile(taskList.getTasks());
            ui.showMessage("Added Event task - " + description + " (from: " + fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + " to: " + toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
            ui.showMessage("You now have " + taskList.getSize() + " task(s) in your list.");
        } catch (DateTimeParseException e) {
            ui.showError("Invalid date format. Use: dd/MM/yyyy HHmm (e.g., 02/03/2019 1800)");
        }
    }
}
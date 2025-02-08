// src/main/java/commands/EventCommand.java
package commands;

import tasks.TaskList;
import tasks.Event;
import ui.Ui;
import utils.Storage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a command to add an event task to the task list.
 * Handles parsing of start and end date-time inputs, creation of the event task,
 * updating the task list, and saving the tasks to persistent storage.
 */
public class EventCommand implements Command {
    private String description;
    private String fromDateTimeString;
    private String toDateTimeString;
    private Storage storage;

    /**
     * Formatter to parse date-time strings in the format "dd/MM/yyyy HHmm".
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * Constructs an EventCommand with the specified description, start and end date-time strings, and storage handler.
     *
     * @param description        The description of the event.
     * @param fromDateTimeString The start date and time in string format.
     * @param toDateTimeString   The end date and time in string format.
     * @param storage            The storage handler to save the task.
     */
    public EventCommand(String description, String fromDateTimeString, String toDateTimeString, Storage storage) {
        this.description = description;
        this.fromDateTimeString = fromDateTimeString;
        this.toDateTimeString = toDateTimeString;
        this.storage = storage;
    }

    /**
     * Executes the event command by adding a new event task to the task list,
     * saving the updated list to storage, and displaying appropriate messages to the user.
     *
     * @param taskList The current task list to which the new event task will be added.
     * @param ui       The user interface for displaying messages.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        try {
            if (fromDateTimeString.length() == 10) {
                fromDateTimeString += " 1200";  // Default time if only date is provided
            }
            LocalDateTime fromDateTime = LocalDateTime.parse(fromDateTimeString, DATE_TIME_FORMATTER);

            if (toDateTimeString.length() == 10) {
                toDateTimeString += " 1200";  // Default time if only date is provided
            }
            LocalDateTime toDateTime = LocalDateTime.parse(toDateTimeString, DATE_TIME_FORMATTER);

            taskList.addTask(new Event(description, fromDateTime.toEpochSecond(ZoneOffset.UTC), toDateTime.toEpochSecond(ZoneOffset.UTC)));
            saveTasks(taskList, ui);

            ui.showMessage("Added Event task - " + description + " (from: " + fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + " to: " + toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
            ui.showMessage("You now have " + taskList.getSize() + " task(s) in your list.");
        } catch (DateTimeParseException e) {
            ui.showError("Invalid date format. Use: dd/MM/yyyy HHmm (e.g., 02/03/2019 1800)");
        }
    }

    /**
     * Saves the current tasks in the task list to persistent storage.
     * Displays an error message if the save operation fails.
     *
     * @param taskList The task list containing tasks to be saved.
     * @param ui       The user interface for displaying error messages.
     */
    private void saveTasks(TaskList taskList, Ui ui) {
        try {
            storage.saveTasksToFile(taskList.getTasks());
        } catch (IOException e) {
            ui.showMessage("Error saving tasks to file: " + e.getMessage());
        }
    }
}

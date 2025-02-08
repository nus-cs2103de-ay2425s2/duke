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

/**
 * Represents a command to add a deadline task to the task list.
 * Handles parsing of date-time input, creation of the deadline task,
 * updating the task list, and saving the tasks to persistent storage.
 */
public class DeadlineCommand implements Command {
    private String taskDescription;
    private String dateTimeString;
    private Storage storage;

    /**
     * Formatter to parse date-time strings in the format "dd/MM/yyyy HHmm".
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * Constructs a DeadlineCommand with the specified task description, date-time string, and storage handler.
     *
     * @param taskDescription The description of the deadline task.
     * @param dateTimeString  The deadline date and time in string format.
     * @param storage         The storage handler to save the task.
     */
    public DeadlineCommand(String taskDescription, String dateTimeString, Storage storage) {
        this.taskDescription = taskDescription;
        this.dateTimeString = dateTimeString;
        this.storage = storage;
    }

    /**
     * Executes the deadline command by adding a new deadline task to the task list,
     * saving the updated list to storage, and displaying appropriate messages to the user.
     *
     * @param taskList The current task list to which the new deadline task will be added.
     * @param ui       The user interface for displaying messages.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        try {
            if (dateTimeString.length() == 10) {
                dateTimeString += " 1200";  // Default time if only date is provided
            }
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString.trim(), DATE_TIME_FORMATTER);
            taskList.addTask(new Deadline(taskDescription, dateTime.toEpochSecond(ZoneOffset.UTC)));
            saveTasks(taskList, ui);
            ui.showMessage("Added Deadline task - " + taskDescription + " (by: " + dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
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
     * @param ui       The user interface for displaying messages.
     */
    private void saveTasks(TaskList taskList, Ui ui) {
        try {
            storage.saveTasksToFile(taskList.getTasks());
        } catch (IOException e) {
            ui.showMessage("Error saving tasks to file: " + e.getMessage());
        }
    }
}
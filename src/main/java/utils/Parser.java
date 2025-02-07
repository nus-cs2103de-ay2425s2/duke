// src/main/java/utils/Parser.java
package utils;

import commands.*;
import tasks.Task;
import tasks.TaskList;
import tasks.Deadline;
import tasks.Event;
import ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public static void parseCommand(String input, TaskList taskList, Ui ui) {
        Command command = null;

        if (input.equalsIgnoreCase("bye")) {
            ui.showExit();
            System.exit(0);
        } else if (input.equalsIgnoreCase("help") || input.equals("?")) {
            ui.showMessage("Here is how you can use me:");
            ui.showMessage("1) add <task> - Add a new ToDo task to your list.");
            ui.showMessage("2) deadline <task> /by <date> - Add a Deadline task. (Format: dd/MM/yyyy HHmm)");
            ui.showMessage("3) event <task> /from <start> /to <end> - Add an Event task. (Format: dd/MM/yyyy HHmm)");
            ui.showMessage("4) list - View all your tasks.");
            ui.showMessage("5) list_day <date> - List all tasks for the specified day. (Format: dd/MM/yyyy)");
            ui.showMessage("6) mark <number> - Mark the corresponding task as complete.");
            ui.showMessage("7) unmark <number> - Mark the corresponding task as incomplete.");
            ui.showMessage("8) delete <number> - Delete the corresponding task from the list.");
            ui.showMessage("9) clear - Clear all tasks from the list.");
            ui.showMessage("10) bye - Exit the chatbot.");
        } else if (input.equalsIgnoreCase("clear")) {
            taskList.clearTasks();
            Storage.saveTasksToFile(taskList.getTasks());
            ui.showMessage("All tasks have been cleared.");
        } else if (input.startsWith("add ")) {
            String taskDescription = input.substring(4);
            command = new AddCommand(taskDescription);
        } else if (input.startsWith("deadline ")) {
            String[] parts = input.substring(9).split(" /by ");
            command = new DeadlineCommand(parts[0], parts[1].trim());
        } else if (input.startsWith("event ")) {
            String[] parts = input.substring(6).split(" /from ");
            String description = parts[0];
            String[] timeParts = parts[1].split(" /to ");
            command = new EventCommand(description, timeParts[0].trim(), timeParts[1].trim());
        } else if (input.equalsIgnoreCase("list")) {
            ui.showMessage("Here are your tasks:");
            for (int i = 0; i < taskList.getSize(); i++) {
                ui.showMessage((i + 1) + ". " + taskList.getTask(i));
            }
            if (taskList.getSize() == 0) {
                ui.showMessage("No tasks added yet.");
            }
        } else if (input.startsWith("mark ")) {
            int index = Integer.parseInt(input.substring(5)) - 1;
            taskList.getTask(index).markAsDone();
            Storage.saveTasksToFile(taskList.getTasks());
            ui.showMessage("Marked as done - " + taskList.getTask(index));
        } else if (input.startsWith("unmark ")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            taskList.getTask(index).markAsNotDone();
            Storage.saveTasksToFile(taskList.getTasks());
            ui.showMessage("Marked as not done - " + taskList.getTask(index));
        } else if (input.startsWith("delete ")) {
            int index = Integer.parseInt(input.substring(7)) - 1;
            Task removedTask = taskList.deleteTask(index);
            Storage.saveTasksToFile(taskList.getTasks());
            ui.showMessage("Deleted task - " + removedTask);
            ui.showMessage("You now have " + taskList.getSize() + " task(s) in your list.");
        } else if (input.startsWith("list_day ")) {
            String dateString = input.substring(9).trim();
            LocalDateTime date = LocalDateTime.parse(dateString + " 0000", DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
            listTasksForDay(taskList, date, ui);
        } else {
            ui.showError("I don't recognize that command. Please type \"Help\" or \"?\" to see the list of available commands.");
        }

        if (command != null) {
            command.execute(taskList, ui);
        }
    }

    private static void listTasksForDay(TaskList taskList, LocalDateTime date, Ui ui) {
        ui.showMessage("Here are your tasks for " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");
        int count = 0;
        for (Task task : taskList.getTasks()) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getByDateTime().toLocalDate().isEqual(date.toLocalDate())) {
                    ui.showMessage((count + 1) + ". " + task);
                    count++;
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.getFromDateTime().toLocalDate().isEqual(date.toLocalDate()) || event.getToDateTime().toLocalDate().isEqual(date.toLocalDate())) {
                    ui.showMessage((count + 1) + ". " + task);
                    count++;
                }
            }
        }
        if (count == 0) {
            ui.showMessage("No tasks found for this day.");
        }
    }
}
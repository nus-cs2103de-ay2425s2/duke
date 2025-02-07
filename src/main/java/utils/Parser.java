// src/main/java/utils/Parser.java
package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;
import ui.Ui;

public class Parser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public static void parseCommand(String input, ArrayList<Task> tasks, Ui ui) {
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
            tasks.clear();
            Storage.saveTasksToFile(tasks);
            ui.showMessage("All tasks have been cleared.");
        } else if (input.startsWith("add ")) {
            String taskDescription = input.substring(4);
            tasks.add(new ToDo(taskDescription));
            Storage.saveTasksToFile(tasks);
            ui.showMessage("Added ToDo task - " + taskDescription);
            ui.showMessage("You now have " + tasks.size() + " task(s) in your list.");
        } else if (input.startsWith("deadline ")) {
            try {
                String[] parts = input.substring(9).split(" /by ");
                String dateTimeString = parts[1].trim();
                if (dateTimeString.length() == 10) {
                    dateTimeString += " 1200";
                }
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
                tasks.add(new Deadline(parts[0], dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"))));
                Storage.saveTasksToFile(tasks);
                ui.showMessage("Added Deadline task - " + parts[0] + " (by: " + dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
                ui.showMessage("You now have " + tasks.size() + " task(s) in your list.");
            } catch (DateTimeParseException e) {
                ui.showError("Invalid date format. Use: dd/MM/yyyy HHmm (e.g., 02/03/2019 1800)");
            } catch (Exception e) {
                ui.showError("Invalid format. Use: deadline <task> /by <date>");
            }
        } else if (input.startsWith("event ")) {
            try {
                String[] parts = input.substring(6).split(" /from ");
                String description = parts[0];
                String[] timeParts = parts[1].split(" /to ");

                String fromDateTimeString = timeParts[0].trim();
                if (fromDateTimeString.length() == 10) {
                    fromDateTimeString += " 1200";
                }
                LocalDateTime fromDateTime = LocalDateTime.parse(fromDateTimeString, DATE_TIME_FORMATTER);

                String toDateTimeString = timeParts[1].trim();
                if (toDateTimeString.length() == 10) {
                    toDateTimeString += " 1200";
                }
                LocalDateTime toDateTime = LocalDateTime.parse(toDateTimeString, DATE_TIME_FORMATTER);

                tasks.add(new Event(description, fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")), toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma"))));
                Storage.saveTasksToFile(tasks);
                ui.showMessage("Added Event task - " + description + " (from: " + fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + " to: " + toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")) + ")");
                ui.showMessage("You now have " + tasks.size() + " task(s) in your list.");
            } catch (DateTimeParseException e) {
                ui.showError("Invalid date format. Use: dd/MM/yyyy HHmm (e.g., 02/03/2019 1800)");
            } catch (Exception e) {
                ui.showError("Invalid format. Use: event <task> /from <start> /to <end>");
            }
        } else if (input.equalsIgnoreCase("list")) {
            ui.showMessage("Here are your tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                ui.showMessage((i + 1) + ". " + tasks.get(i));
            }
            if (tasks.isEmpty()) {
                ui.showMessage("No tasks added yet.");
            }
        } else if (input.startsWith("mark ")) {
            try {
                int index = Integer.parseInt(input.substring(5)) - 1;
                if (index >= 0 && index < tasks.size()) {
                    tasks.get(index).markAsDone();
                    Storage.saveTasksToFile(tasks);
                    ui.showMessage("Marked as done - " + tasks.get(index));
                } else {
                    ui.showError("Invalid task number.");
                }
            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid task number.");
            }
        } else if (input.startsWith("unmark ")) {
            try {
                int index = Integer.parseInt(input.substring(7)) - 1;
                if (index >= 0 && index < tasks.size()) {
                    tasks.get(index).markAsNotDone();
                    Storage.saveTasksToFile(tasks);
                    ui.showMessage("Marked as not done - " + tasks.get(index));
                } else {
                    ui.showError("Invalid task number.");
                }
            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid task number.");
            }
        } else if (input.startsWith("delete ")) {
            try {
                int index = Integer.parseInt(input.substring(7)) - 1;
                if (index >= 0 && index < tasks.size()) {
                    Task removedTask = tasks.remove(index);
                    Storage.saveTasksToFile(tasks);
                    ui.showMessage("Deleted task - " + removedTask);
                    ui.showMessage("You now have " + tasks.size() + " task(s) in your list.");
                } else {
                    ui.showError("Invalid task number.");
                }
            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid task number.");
            }
        } else if (input.startsWith("list_day ")) {
            try {
                String dateString = input.substring(9).trim();
                LocalDateTime date = LocalDateTime.parse(dateString + " 0000", DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
                listTasksForDay(tasks, date, ui);
            } catch (DateTimeParseException e) {
                ui.showError("Invalid date format. Use: dd/MM/yyyy (e.g., 02/03/2019)");
            }
        } else {
            ui.showError("I don't recognize that command. Please type \"Help\" or \"?\" to see the list of available commands.");
        }
    }

    private static void listTasksForDay(ArrayList<Task> tasks, LocalDateTime date, Ui ui) {
        ui.showMessage("Here are your tasks for " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");
        int count = 0;
        for (Task task : tasks) {
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
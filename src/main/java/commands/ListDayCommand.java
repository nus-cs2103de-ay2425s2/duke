// src/main/java/commands/ListDayCommand.java
package commands;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;
import utils.Storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class ListDayCommand implements Command {
    private LocalDate date;

    public ListDayCommand(String input) {
        try {
            String dateString = input.substring(8).trim();
            this.date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use: list_day <dd/MM/yyyy>");
        }
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        List<Task> tasksForDay = taskList.getTasks().stream()
                .filter(task -> {
                    if (task instanceof Deadline) {
                        LocalDateTime byDateTime = ((Deadline) task).getByDateTime();
                        return byDateTime.toLocalDate().equals(date);
                    } else if (task instanceof Event) {
                        LocalDateTime fromDateTime = ((Event) task).getFromDateTime();
                        LocalDateTime toDateTime = ((Event) task).getToDateTime();
                        return !fromDateTime.toLocalDate().isAfter(date) && !toDateTime.toLocalDate().isBefore(date);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        if (tasksForDay.isEmpty()) {
            ui.showMessage("No tasks found for " + date);
        } else {
            ui.showMessage("Tasks for " + date + ":");
            for (Task task : tasksForDay) {
                ui.showMessage(task.toString());
            }
        }
    }
}
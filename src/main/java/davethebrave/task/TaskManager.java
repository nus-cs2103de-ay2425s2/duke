/*
Handles methods related to the list of tasks
 */

package davethebrave.task;

import davethebrave.storage.Storage;
import davethebrave.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;
    private Storage storage;
    private Ui ui;

    String todoType = "T";
    String deadlineType = "D";
    String eventType = "E";

    public TaskManager(List<Task> tasks, Storage storage, Ui ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;
        storage.checkFileExists();
        storage.loadTasksFromFile();
        System.out.println("Previous tasks loaded: ");
        listTasks();
    }
    public void addTask(String type, String task, String details) {
        if (type.equals(deadlineType) && details != null) {
            try {
                LocalDate.parse(details.trim(), Task.INPUT_FORMATTER);
                System.out.println("Added deadline: " + task + " (by: " + details + ")");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use yyyy-MM-dd (e.g., 2019-10-15).");
                return;
            }
        }
        tasks.add(new Task(type, task, details));
        storage.saveTasksToFile(tasks);
        ui.showTaskAdded(tasks);
    }

    public void deleteTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task removedTask = tasks.remove(taskNumber - 1);
            storage.saveTasksToFile(tasks);
            ui.showTaskDeleted(removedTask, tasks);
        }
    }

    public void listTasks() {
        System.out.println("    ____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println("      No tasks to display.");
        } else {
            System.out.println("      Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("      " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("    ____________________________________________________________");
    }

    public void markTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task task = tasks.get(taskNumber - 1);
            if (!task.toString().contains("[X]")) {
                task.mark();
                storage.saveTasksToFile(tasks);
                System.out.println("    ____________________________________________________________");
                System.out.println("      Nice! I've marked this task as done:");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      This task is already marked as done:");
            }
            System.out.println("         " + task);
            System.out.println("    ____________________________________________________________");
        }
    }

    public void unmarkTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task task = tasks.get(taskNumber - 1);
            if (task.toString().contains("[X]")) {
                task.unmark();
                storage.saveTasksToFile(tasks);
                System.out.println("    ____________________________________________________________");
                System.out.println("      OK, I've marked this task as not done yet:");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      This task is already marked as not done:");
            }
            System.out.println("         " + task);
            System.out.println("    ____________________________________________________________");
        }
    }
    private boolean isValidTaskNumber(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("    ____________________________________________________________");
            System.out.println("      Invalid task number.");
            System.out.println("    ____________________________________________________________");
            return false;
        }
        return true;
    }
}

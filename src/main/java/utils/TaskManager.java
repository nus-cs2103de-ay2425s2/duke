package pawpal.utils;

import pawpal.tasks.Task;
import pawpal.tasks.Deadline;
import pawpal.tasks.Event;
import pawpal.tasks.ToDo;

import java.util.ArrayList;
import java.util.List;

class TaskManager {
    private final List<Task> tasks;
    private final Printer printer;

    public TaskManager() {
        tasks = new ArrayList<>();
        printer = new Printer();
    }

    public void addToDo(String description) {
        Task newTask = new ToDo(description);
        tasks.add(newTask);
        printer.printTaskAdded(description, tasks.size());
    }

    public void addDeadline(String description, String deadline) {
        Task newTask = new Deadline(description, deadline);
        tasks.add(newTask);
        printer.printTaskAdded(description, tasks.size());
    }

    public void addEvent(String description, String from, String to) {
        Task newTask = new Event(description, from, to);
        tasks.add(newTask);
        printer.printTaskAdded(description, tasks.size());
    }

    public void listTasks() {
        printer.printTaskList(tasks);
    }

    public void deleteTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.remove(taskNumber - 1);
            printer.printTaskDeleted(task, tasks.size());
        } else {
            printer.printInvalidTaskNumber();
        }
    }

    public void markTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.markAsDone();
            printer.printTaskMarked(task);
        } else {
            printer.printInvalidTaskNumber();
        }
    }

    public void unmarkTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.markAsNotDone();
            printer.printTaskUnmarked(task);
        } else {
            printer.printInvalidTaskNumber();
        }
    }
}

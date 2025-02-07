package utils;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the task list, including adding, removing, and updating tasks.
 */
public class TaskList {

    private final List<Task> tasks;

    /**
     * Constructs a new {@code TaskList} with an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a new ToDo task to the list.
     *
     * @param description The description of the ToDo task.
     */
    public void addToDo(String description) {
        tasks.add(new ToDo(description));
    }

    /**
     * Adds a new Deadline task to the list.
     *
     * @param description The description of the Deadline task.
     * @param deadline    The deadline for the task.
     */
    public void addDeadline(String description, String deadline) {
        tasks.add(new Deadline(description, deadline));
    }

    /**
     * Adds a new Event task to the list.
     *
     * @param description The description of the Event task.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public void addEvent(String description, String from, String to) {
        tasks.add(new Event(description, from, to));
    }

    /**
     * Deletes a task from the list by its index.
     *
     * @param taskNumber The 1-based index of the task to be deleted.
     * @return The task that was deleted.
     */
    public Task deleteTask(int taskNumber) {
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Marks a task as done by its index.
     *
     * @param taskNumber The 1-based index of the task to be marked as done.
     */
    public void markTask(int taskNumber) {
        tasks.get(taskNumber - 1).markAsDone();
    }

    /**
     * Marks a task as not done by its index.
     *
     * @param taskNumber The 1-based index of the task to be marked as not done.
     */
    public void unmarkTask(int taskNumber) {
        tasks.get(taskNumber - 1).markAsNotDone();
    }

    /**
     * Returns the list of tasks.
     *
     * @return The list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }
}

// src/main/java/tasks/TaskList.java
package tasks;

import java.util.ArrayList;

/**
 * Represents a list of tasks, providing methods to manage tasks such as adding,
 * deleting, marking as done, and unmarking. Handles common operations on the task list.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The list of tasks to initialize the TaskList with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list based on its index.
     *
     * @param index The index of the task to be deleted.
     * @return The deleted task.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public Task deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
    }

    /**
     * Retrieves a task from the task list based on its index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public Task getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Retrieves the list of all tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Clears all tasks from the task list.
     */
    public void clearTasks() {
        tasks.clear();
    }

    /**
     * Marks a task as done based on its index.
     *
     * @param index The index of the task to mark as done.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public void markTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsDone();
        } else {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
    }

    /**
     * Unmarks a task (marks it as not done) based on its index.
     *
     * @param index The index of the task to unmark.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public void unmarkTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsNotDone();
        } else {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
    }

    /**
     * Checks if a task is marked as done based on its index.
     *
     * @param index The index of the task to check.
     * @return true if the task is done, false otherwise.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public boolean isTaskDone(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index).isDone();
        } else {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
    }
}

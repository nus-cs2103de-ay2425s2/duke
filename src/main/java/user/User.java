package user;

import task.DeadLineTask;
import task.EventTask;
import task.Task;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * User class
 */
public class User {
    private List<Task> taskList;
    private Path dataFilePath;

    /**
     * Default Constructor for User
     */
    public User() {
        this.taskList = new LinkedList<>();
    }

    public void addTask(Task task) {
        this.taskList.addLast(task);
    }

    /**
     * addTask method that returns the index that the task is inserted at
     * @param task Task to be inserted
     * @param shouldReturnIndex boolean that indicates if return index should be returned
     * @return int index where the task is inserted at else -1 if index should not be returned
     * If index is not to be returned, it is encouraged to use void addTask instead
     */
    public int addTask(Task task, boolean shouldReturnIndex) {
        this.taskList.addLast(task);
        if (shouldReturnIndex) {
            return this.taskList.size() - 1;
        }
        return -1;
    }

    /**
     * getTaskList method that returns a string representation of the task list currently
     * @return String that represents the task list currently
     */
    public String getTaskList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            stringBuilder.append("%s. %s".formatted(
                    i + 1,
                    getTaskInformation(i))
            );
            if (i != taskList.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * TODO: Refactor method to task class
     * Method that returns the string representation of the task
     * @param taskNumber int that indicates that current taskNumber that is 0-indexed
     * @return String that represents the information of the task
     */
    public String getTaskInformation(int taskNumber) {
        Task task = this.taskList.get(taskNumber);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[%s] [%s] %s".formatted(
                task.getTaskType(),
                (task.isTaskDone()) ? "X" : " ",
                task.getTaskDetail()));

        if (task instanceof DeadLineTask) {
            stringBuilder.append(" ");
            stringBuilder.append("(by: %s)".formatted(((DeadLineTask) task).getDeadLine()));
        }

        else if (task instanceof EventTask) {
            stringBuilder.append(" ");
            stringBuilder.append("(from: %s to: %s)".formatted(
                    ((EventTask) task).getStartDateTime(),
                    ((EventTask) task).getDeadLine()));
        }
        return stringBuilder.toString();
    }

    /**
     * Sets task to be done
     * @param taskNumber int that indicates the task to set
     * @return String representation of updated task information
     */
    public String markTaskAsDone(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        requiredTask.setTaskDone(true);

        return this.getTaskInformation(taskNumber);
    }

    /**
     * Sets task to be not done
     * @param taskNumber int that indicates the task to set
     * @return String representation of updated task information
     */
    public String markTaskAsNotDone(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        requiredTask.setTaskDone(false);

        return this.getTaskInformation(taskNumber);
    }

    public int getNumberOfTasks() {
        return this.taskList.size();
    }

    /**
     * Deletes task from the task list
     * @param taskNumber int taht indcates the task to delete
     * @return String representation of deleted task information
     */
    public String deleteTask(int taskNumber) {
        String removedTaskInformation = this.getTaskInformation(taskNumber);
        this.taskList.remove(taskNumber);
        return removedTaskInformation;
    }

}

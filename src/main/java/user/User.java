package user;

import task.DeadLineTask;
import task.EventTask;
import task.Task;

import java.util.LinkedList;
import java.util.List;

public class User {
    private List<Task> taskList;

    public User() {
        this.taskList = new LinkedList<>();
    }

    public void addTask(Task task) {
        this.taskList.addLast(task);
    }

    public int addTask(Task task, boolean shouldReturnIndex) {
        this.taskList.addLast(task);
        if (shouldReturnIndex) {
            return this.taskList.size() - 1;
        }
        return -1;
    }


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

    public String getTaskInformation (int taskNumber) {
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

    public String markTaskAsDone(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        requiredTask.setTaskDone(true);

        return this.getTaskInformation(taskNumber);
    }

    public String markTaskAsNotDone(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        requiredTask.setTaskDone(false);

        return this.getTaskInformation(taskNumber);
    }

    public int getNumberOfTasks() {
        return this.taskList.size();
    }

}

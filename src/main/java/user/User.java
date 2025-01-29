package user;

import event.Task;

import java.util.LinkedList;
import java.util.List;

public class User {
    private List<Task> taskList;

    public User() {
        this.taskList = new LinkedList<>();
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public String getTaskList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            stringBuilder.append("%s. %s %s".formatted(i + 1, task.getTaskName(), task.getTaskDetail()));
        }
        return stringBuilder.toString();
    }
}

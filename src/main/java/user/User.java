package user;

import task.Task;

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
            stringBuilder.append("%s. %s".formatted(i + 1, task.getTaskDetail()));
            if (i != taskList.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}

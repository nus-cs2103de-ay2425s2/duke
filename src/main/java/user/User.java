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
            stringBuilder.append("%s.[%s] %s".formatted(
                    i + 1,
                    (task.isTaskDone()) ? "X" : " ",
                    task.getTaskDetail())
            );
            if (i != taskList.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public String markTaskAsDone(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        requiredTask.setTaskDone(true);

        return this.getMarkTaskConsoleOutput(requiredTask.isTaskDone(), requiredTask.getTaskDetail());
    }

    public String markTaskAsNotDone(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        requiredTask.setTaskDone(false);

        return this.getMarkTaskConsoleOutput(requiredTask.isTaskDone(), requiredTask.getTaskDetail());
    }

    private String getMarkTaskConsoleOutput(boolean isTaskDone, String taskDetail) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[%s] %s".formatted((isTaskDone) ? "X" : " ", taskDetail));
        System.out.println("test" + stringBuilder.toString());
        return stringBuilder.toString();
    }

}

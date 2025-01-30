package task;

public class Task {
    private String taskDetail;
    private boolean isTaskDone;

    public Task(String taskDetail) {
        this.taskDetail = taskDetail;
        this.isTaskDone = false;
    }

    public String getTaskDetail() {
        return this.taskDetail;
    };

    public boolean isTaskDone() {
        return this.isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.isTaskDone = taskDone;
    }
}

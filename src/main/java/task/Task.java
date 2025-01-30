package task;

import action.ActionHandler.Event;

public class Task {
    private String taskDetail;
    private boolean isTaskDone;
    private Event taskType;

    public Task(String taskDetail) {
        this.taskDetail = taskDetail;
        this.isTaskDone = false;
        this.taskType = null;
    }

    public Task(String taskDetail, Event taskType) {
        this.taskDetail = taskDetail;
        this.isTaskDone = false;
        this.taskType = taskType;
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

    public String getTaskType() {
        return switch (this.taskType) {
            case Event.TODO -> "T";
            case Event.DEADLINE -> "D";
            case Event.EVENT -> "E";
            default -> "";
        };
    }
}

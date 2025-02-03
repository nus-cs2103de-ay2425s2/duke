package task;

import action.ActionHandler.Action;
import data.DataHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Task class
 */
public class Task {
    private String taskDetail;
    private boolean isTaskDone;
    private Action taskType;

    /**
     * Constructor that creates a task without a type
     * @param taskDetail String that indicates the task information
     */
    public Task(String taskDetail) {
        this.taskDetail = taskDetail;
        this.isTaskDone = false;
        this.taskType = null;
    }

    /**
     * Constructor that creates a task with a taskType
     * @param taskDetail String that indicates the task information
     * @param taskType Action enum that indicates the task type
     */
    public Task(String taskDetail, Action taskType) {
        this.taskDetail = taskDetail;
        this.isTaskDone = false;
        this.taskType = taskType;
    }

    public String getTaskDetail() {
        return this.taskDetail;
    }

    public boolean isTaskDone() {
        return this.isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.isTaskDone = taskDone;
    }

    /**
     * Maps the task type to a String
     * @return String of mapped task type
     */
    public String getTaskType() {
        return switch (this.taskType) {
            case Action.TODO -> "T";
            case Action.DEADLINE -> "D";
            case Action.EVENT -> "E";
            default -> "";
        };
    }

    public String getSaveInformation() {
        List<String> saveInformation = new ArrayList<>();
        saveInformation.add(this.getTaskType());
        saveInformation.add(String.valueOf(this.isTaskDone() ? 1 : 0));
        saveInformation.add(this.taskDetail);
        return String.join(DataHandler.saveDelimiter, saveInformation);
    }
}

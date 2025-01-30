package task;

import action.ActionHandler;

public class ToDoTask extends Task {
    public ToDoTask(String taskDetail) {
        super(taskDetail, ActionHandler.Action.TODO);
    }
}

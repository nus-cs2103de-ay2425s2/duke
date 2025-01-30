package task;

import action.ActionHandler;

public class DeadLineTask extends Task {
    public DeadLineTask(String taskDetail) {
        super(taskDetail, ActionHandler.Action.DEADLINE);
    }
}

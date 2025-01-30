package task;

import action.ActionHandler.Action;

public class EventTask extends Task {
    public EventTask(String taskDetail) {
        super(taskDetail, Action.EVENT);
    }
}

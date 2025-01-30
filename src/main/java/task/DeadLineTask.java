package task;

import action.ActionHandler;

public class DeadLineTask extends Task implements HasDeadline {
    private String deadLine;

    public DeadLineTask(String taskDetail, String deadLine) {
        super(taskDetail, ActionHandler.Action.DEADLINE);
        this.deadLine = deadLine;
    }

    @Override
    public String getDeadLine() {
        return this.deadLine;
    }
}

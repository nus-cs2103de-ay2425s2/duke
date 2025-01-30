package task;

import action.ActionHandler.Action;

public class EventTask extends Task implements HasStart, HasDeadline {
    private String fromDateTime;
    private String toDateTime;

    public EventTask(String taskDetail, String fromDateTime, String toDateTime) {
        super(taskDetail, Action.EVENT);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    @Override
    public String getDeadLine() {
        return this.getToDateTime();
    }

    @Override
    public String getStartDateTime() {
        return this.getFromDateTime();
    }

    private String getFromDateTime() {
        return this.fromDateTime;
    }

    private String getToDateTime() {
        return this.toDateTime;
    }
}

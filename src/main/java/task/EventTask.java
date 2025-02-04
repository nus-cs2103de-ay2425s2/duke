package task;

import action.ActionHandler.Action;
import data.DataHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * EventTask class
 */
public class EventTask extends Task implements HasStart, HasDeadline {
    private String fromDateTime;
    private String toDateTime;

    /**
     * EventTask constructor
     * @param taskDetail String that indicates the task detail
     * @param fromDateTime String that is a valid datetime/day format that indicates when the event starts
     * @param toDateTime String that is a valid datetime/day format that indicates when the event ends
     */
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

    @Override
    public String createSaveData() {
        List<String> saveInformation = new ArrayList<>();
        saveInformation.add(super.createSaveData());
        saveInformation.add("/from %s /to %s".formatted(this.getFromDateTime(), this.getToDateTime()));
        return String.join(DataHandler.saveDelimiter, saveInformation);
    }

    @Override
    public String getTaskInformation() {
        List<String> taskInformationList = new ArrayList<>();
        taskInformationList.add(super.getTaskInformation());
        taskInformationList.add("(from: %s to: %s)".formatted(
                this.getStartDateTime(),
                this.getDeadLine()));
        return String.join(" ", taskInformationList);
    }
}

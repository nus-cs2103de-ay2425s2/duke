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
    public String getSaveInformation() {
        List<String> saveInformation = new ArrayList<>();
        saveInformation.add(super.getSaveInformation());
        saveInformation.add(this.fromDateTime);
        saveInformation.add(this.toDateTime);
        return String.join(DataHandler.saveDelimiter, saveInformation);
    }
}

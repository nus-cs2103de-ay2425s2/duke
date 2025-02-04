package task;

import action.ActionHandler.Action;
import data.DataHandler;
import io.InputValidator;
import io.UI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * EventTask class
 */
public class EventTask extends Task implements HasStart, HasDeadline {
    private LocalDate fromDate;
    private LocalDateTime fromDateTime;
    private LocalDate toDate;
    private LocalDateTime toDateTime;

    /**
     * EventTask constructor
     * @param taskDetail String that indicates the task detail
     * @param fromDateTime String that is a valid datetime/day format that indicates when the event starts
     * @param toDateTime String that is a valid datetime/day format that indicates when the event ends
     */
    public EventTask(String taskDetail, String fromDateTime, String toDateTime) {
        super(taskDetail, Action.EVENT);
        if (InputValidator.isValidDate(fromDateTime, false)) {
            this.fromDate = super.parseDate(fromDateTime);
        }
        else {
            this.fromDateTime = super.parseDateTime(toDateTime);
        }

        if (InputValidator.isValidDate(toDateTime, true)) {
            this.toDate = super.parseDate(toDateTime);
        }
        else {
            this.toDateTime = super.parseDateTime(toDateTime);
        }
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
        if (this.fromDate == null) {
            return this.fromDateTime.toString();
        }
        return this.fromDate.toString();
    }

    private String getToDateTime() {
        if (this.toDate == null) {
            return this.toDateTime.toString();
        }
        return this.toDate.toString();
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

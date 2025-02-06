package task;

import action.ActionHandler;
import data.DataHandler;
import io.InputValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DeadLineTask class
 */
public class DeadLineTask extends Task implements HasDeadline {
    private LocalDateTime deadLineDateTime;
    private LocalDate deadLineDate;

    /**
     * Constructor for DeadLineTask
     *
     * @param taskDetail String that indicates the task details
     * @param deadLine   String that is a valid datetime/day format
     */
    public DeadLineTask(String taskDetail, String deadLine) {
        super(taskDetail, ActionHandler.Action.DEADLINE);
        if (InputValidator.isValidDate(deadLine, true)) {
            this.deadLineDate = super.parseDate(deadLine);
        } else {
            this.deadLineDateTime = super.parseDateTime(deadLine);
        }
    }

    @Override
    public String getDeadLine() {
        if (this.deadLineDate == null) {
            return HasDeadline.DATE_TIME_FORMATTER.format(this.deadLineDateTime);
        }
        return HasDeadline.DATE_FORMATTER.format(this.deadLineDate);
    }

    private String getSaveDeadLine() {
        if (this.deadLineDate == null) {
            return DataHandler.dateTimeSaveFormat.format(this.deadLineDateTime);
        }
        return DataHandler.dateSaveFormat.format(this.deadLineDate);
    }

    @Override
    public String createSaveData() {
        List<String> saveInformation = new ArrayList<>();
        saveInformation.add(super.createSaveData());
        saveInformation.add("/by %s".formatted(this.getSaveDeadLine()));
        return String.join(DataHandler.saveDelimiter, saveInformation);
    }

    @Override
    public String getTaskInformation() {
        List<String> taskInformationList = new ArrayList<>();
        taskInformationList.add(super.getTaskInformation());
        taskInformationList.add("(by: %s)".formatted(this.getDeadLine()));
        return String.join(" ", taskInformationList);
    }
}

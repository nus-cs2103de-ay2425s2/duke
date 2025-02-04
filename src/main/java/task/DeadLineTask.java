package task;

import action.ActionHandler;
import data.DataHandler;
import io.InputValidator;
import io.UI;

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
     * @param taskDetail String that indicates the task details
     * @param deadLine String that is a valid datetime/day format
     */
    public DeadLineTask(String taskDetail, String deadLine) {
        super(taskDetail, ActionHandler.Action.DEADLINE);
        if (InputValidator.isValidDate(deadLine, true)) {
            this.deadLineDate = super.parseDate(deadLine);
        }
        else {
            this.deadLineDateTime = super.parseDateTime(deadLine);
        }
    }

    @Override
    public String getDeadLine() {
        if (this.deadLineDate == null) {
            return this.deadLineDateTime.toString();
        }

        return this.deadLineDate.toString();
    }

    @Override
    public String createSaveData() {
        List<String> saveInformation = new ArrayList<>();
        saveInformation.add(super.createSaveData());
        saveInformation.add("/by %s".formatted(this.getDeadLine()));
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

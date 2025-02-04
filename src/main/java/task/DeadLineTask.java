package task;

import action.ActionHandler;
import data.DataHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * DeadLineTask class
 */
public class DeadLineTask extends Task implements HasDeadline {
    private String deadLine;

    /**
     * Constructor for DeadLineTask
     * @param taskDetail String that indicates the task details
     * @param deadLine String that is a valid datetime/day format
     */
    public DeadLineTask(String taskDetail, String deadLine) {
        super(taskDetail, ActionHandler.Action.DEADLINE);
        this.deadLine = deadLine;
    }

    @Override
    public String getDeadLine() {
        return this.deadLine;
    }

    @Override
    public String createSaveData() {
        List<String> saveInformation = new ArrayList<>();
        saveInformation.add(super.createSaveData());
        saveInformation.add("/by %s".formatted(this.getDeadLine()));
        return String.join(DataHandler.saveDelimiter, saveInformation);
    }
}

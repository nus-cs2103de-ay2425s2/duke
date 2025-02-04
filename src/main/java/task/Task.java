package task;

import action.ActionHandler.Action;
import data.DataHandler;
import io.InputValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Task class
 */
public class Task {
    private String taskDetail;
    private boolean isTaskDone;
    private Action taskType;

    /**
     * Constructor that creates a task without a type
     * @param taskDetail String that indicates the task information
     */
    public Task(String taskDetail) {
        this.taskDetail = taskDetail;
        this.isTaskDone = false;
        this.taskType = null;
    }

    /**
     * Constructor that creates a task with a taskType
     * @param taskDetail String that indicates the task information
     * @param taskType Action enum that indicates the task type
     */
    public Task(String taskDetail, Action taskType) {
        this.taskDetail = taskDetail;
        this.isTaskDone = false;
        this.taskType = taskType;
    }

    public String getTaskDetail() {
        return this.taskDetail;
    }

    public boolean isTaskDone() {
        return this.isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.isTaskDone = taskDone;
    }

    /**
     * Maps the task type to a String
     * @return String of mapped task type
     */
    public String getTaskType() {
        return switch (this.taskType) {
            case Action.TODO -> "T";
            case Action.DEADLINE -> "D";
            case Action.EVENT -> "E";
            default -> "";
        };
    }

    public static Action mapTaskType(String actionString) {
        return switch(actionString) {
            case "T" -> Action.TODO;
            case "D" -> Action.DEADLINE;
            case "E" -> Action.EVENT;
            default -> Action.DEFAULT;
        };
    }

    public String createSaveData() {
        List<String> saveInformation = new ArrayList<>();
        saveInformation.add(this.getTaskType());
        saveInformation.add(String.valueOf(this.isTaskDone() ? 1 : 0));
        saveInformation.add(this.taskDetail);
        return String.join(DataHandler.saveDelimiter, saveInformation);
    }

    public String getTaskInformation() {
        return "[%s] [%s] %s".formatted(this.getTaskType(),
                (this.isTaskDone()) ? "X" : " ",
                this.getTaskDetail());
    };

    protected LocalDateTime parseDateTime(String stringDateTime) {
        List<String> stringDateList = new ArrayList<>(List.of(stringDateTime.split(" ")));

        try {
            return LocalDateTime.parse(String.join(" ", stringDateList), InputValidator.DAY_MONTH_YEAR_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            e.getMessage();
        }

        try {
            String removedItem = stringDateList.removeFirst();
            stringDateList.addFirst(removedItem + "/" + Year.now());
            return LocalDateTime.parse(String.join(" ", stringDateList), InputValidator.DAY_MONTH_YEAR_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            e.getMessage();
        }

        throw new IllegalArgumentException("Input is not correct");
    }

    protected LocalDate parseDate(String stringDate) {
        // parse information without a year
        try {
            return LocalDate.parse(stringDate + "/" + Year.now(), InputValidator.DAY_MONTH_YEAR_FORMATTER);
        } catch (DateTimeParseException e) {
            e.getMessage();
        }

        try {
            return LocalDate.parse(stringDate, InputValidator.DAY_MONTH_YEAR_FORMATTER);
        } catch (DateTimeParseException e) {
            e.getMessage();
        }

        throw new IllegalArgumentException("Input is not correct");
    }
}

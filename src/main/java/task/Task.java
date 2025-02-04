package task;

import action.ActionHandler.Action;
import data.DataHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dayMonthYearTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:m");
        DateTimeFormatter dayMonthTimeFormatter = DateTimeFormatter.ofPattern("dd/MM H:m");

        List<DateTimeFormatter> dateYearFormatters = new ArrayList<>();
        dateYearFormatters.add(dayMonthYearTimeFormatter);
        dateYearFormatters.add(dayMonthTimeFormatter);

        List<String> stringDateList = new ArrayList<>(List.of(stringDateTime.split(" ")));

        for (DateTimeFormatter formatter : dateYearFormatters) {
            try {
                LocalDateTime dateTime;
                if (formatter.equals(dayMonthTimeFormatter)) {
                    String date = stringDateList.removeFirst();
                    stringDateList.addFirst(date + "-" + Year.now());
                    dateTime = LocalDateTime.parse(String.join(" ", stringDateList), dayMonthYearTimeFormatter);
                }
                else {
                    dateTime = LocalDateTime.parse(stringDateTime, formatter);
                }
                return dateTime;

            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        }

        return null;
    }

    protected LocalDate parseDate(String stringDate) {
        DateTimeFormatter dayMonthYearFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("dd/MM");

        List<DateTimeFormatter> dateFormatters = new ArrayList<>();
        dateFormatters.add(dayMonthYearFormatter);
        dateFormatters.add(dayMonthFormatter);

        // parse information without a year
        for (DateTimeFormatter formatter : dateFormatters) {
            try {
                LocalDate date;
                if (formatter.equals(dayMonthFormatter)) {
                    date = LocalDate.parse(stringDate + "/" + Year.now(), dayMonthYearFormatter);
                }
                else {
                    date = LocalDate.parse(stringDate, formatter);
                }
                return date;

            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}

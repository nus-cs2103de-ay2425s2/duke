// src/main/java/tasks/Deadline.java
package tasks;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a specific due date and time.
 * Inherits from the Task class and provides additional functionality
 * for managing deadlines.
 */
public class Deadline extends Task {
    protected long byTimestamp;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * Constructs a Deadline task using a description and a date-time string.
     *
     * @param description The description of the deadline task.
     * @param by          The due date and time in the format "dd/MM/yyyy HHmm".
     */
    public Deadline(String description, String by) {
        super(description);
        this.byTimestamp = LocalDateTime.parse(by, DATE_TIME_FORMATTER).toEpochSecond(ZoneOffset.UTC);
    }

    /**
     * Constructs a Deadline task using a description and a timestamp.
     *
     * @param description The description of the deadline task.
     * @param byTimestamp The due date and time as a Unix timestamp.
     */
    public Deadline(String description, long byTimestamp) {
        super(description);
        this.byTimestamp = byTimestamp;
    }

    /**
     * Returns the type of the task.
     *
     * @return A string "D" representing a Deadline task.
     */
    @Override
    protected String getType() {
        return "D";
    }

    /**
     * Converts the Deadline task to a formatted string suitable for file storage.
     *
     * @return A string representing the task in a file-friendly format.
     */
    @Override
    public String toFileString() {
        return String.format("%s | %d | %s | %d", getType(), isDone ? 1 : 0, description, byTimestamp);
    }

    /**
     * Returns a string representation of the Deadline task for display.
     *
     * @return A string with the task type, status, description, and due date.
     */
    @Override
    public String toString() {
        LocalDateTime byDateTime = LocalDateTime.ofEpochSecond(byTimestamp, 0, ZoneOffset.UTC);
        return String.format("[%s][%s] %s (by: %s)", getType(), isDone ? "X" : " ", description, byDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")));
    }

    /**
     * Retrieves the due date and time of the Deadline task as a LocalDateTime object.
     *
     * @return The due date and time of the task.
     */
    public LocalDateTime getByDateTime() {
        return LocalDateTime.ofEpochSecond(byTimestamp, 0, ZoneOffset.UTC);
    }
}

// src/main/java/tasks/Event.java
package tasks;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a specific start and end date-time.
 * Inherits from the Task class and provides additional functionality
 * for managing event tasks.
 */
public class Event extends Task {
    protected long fromTimestamp;
    protected long toTimestamp;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * Constructs an Event task using a description and date-time strings for the start and end times.
     *
     * @param description The description of the event task.
     * @param from        The start date and time in the format "dd/MM/yyyy HHmm".
     * @param to          The end date and time in the format "dd/MM/yyyy HHmm".
     */
    public Event(String description, String from, String to) {
        super(description);
        this.fromTimestamp = LocalDateTime.parse(from, DATE_TIME_FORMATTER).toEpochSecond(ZoneOffset.UTC);
        this.toTimestamp = LocalDateTime.parse(to, DATE_TIME_FORMATTER).toEpochSecond(ZoneOffset.UTC);
    }

    /**
     * Constructs an Event task using a description and timestamps for the start and end times.
     *
     * @param description  The description of the event task.
     * @param fromTimestamp The start date and time as a Unix timestamp.
     * @param toTimestamp   The end date and time as a Unix timestamp.
     */
    public Event(String description, long fromTimestamp, long toTimestamp) {
        super(description);
        this.fromTimestamp = fromTimestamp;
        this.toTimestamp = toTimestamp;
    }

    /**
     * Returns the type of the task.
     *
     * @return A string "E" representing an Event task.
     */
    @Override
    protected String getType() {
        return "E";
    }

    /**
     * Converts the Event task to a formatted string suitable for file storage.
     *
     * @return A string representing the task in a file-friendly format.
     */
    @Override
    public String toFileString() {
        return String.format("%s | %d | %s | %d | %d", getType(), isDone ? 1 : 0, description, fromTimestamp, toTimestamp);
    }

    /**
     * Returns a string representation of the Event task for display.
     *
     * @return A string with the task type, status, description, start date, and end date.
     */
    @Override
    public String toString() {
        LocalDateTime fromDateTime = LocalDateTime.ofEpochSecond(fromTimestamp, 0, ZoneOffset.UTC);
        LocalDateTime toDateTime = LocalDateTime.ofEpochSecond(toTimestamp, 0, ZoneOffset.UTC);
        return String.format("[%s][%s] %s (from: %s to: %s)", getType(), isDone ? "X" : " ", description, fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")), toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")));
    }

    /**
     * Retrieves the start date and time of the Event task as a LocalDateTime object.
     *
     * @return The start date and time of the event.
     */
    public LocalDateTime getFromDateTime() {
        return LocalDateTime.ofEpochSecond(fromTimestamp, 0, ZoneOffset.UTC);
    }

    /**
     * Retrieves the end date and time of the Event task as a LocalDateTime object.
     *
     * @return The end date and time of the event.
     */
    public LocalDateTime getToDateTime() {
        return LocalDateTime.ofEpochSecond(toTimestamp, 0, ZoneOffset.UTC);
    }
}
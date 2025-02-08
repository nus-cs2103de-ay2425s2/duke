// src/main/java/tasks/Event.java
package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected String from;
    protected String to;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    protected String getType() {
        return "E";
    }

    @Override
    public String toFileString() {
        return String.format("%s | %d | %s | %s | %s", getType(), isDone ? 1 : 0, description, from, to);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)", getType(), isDone ? "X" : " ", description, from, to);
    }

    public LocalDateTime getFromDateTime() {
        return LocalDateTime.parse(from, DATE_TIME_FORMATTER);
    }

    public LocalDateTime getToDateTime() {
        return LocalDateTime.parse(to, DATE_TIME_FORMATTER);
    }
}
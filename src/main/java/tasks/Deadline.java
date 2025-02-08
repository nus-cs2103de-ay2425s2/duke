// src/main/java/tasks/Deadline.java
package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected String by;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    protected String getType() {
        return "D";
    }

    @Override
    public String toFileString() {
        return String.format("%s | %d | %s | %s", getType(), isDone ? 1 : 0, description, by);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (by: %s)", getType(), isDone ? "X" : " ", description, by);
    }

    public LocalDateTime getByDateTime() {
        return LocalDateTime.parse(by, DATE_TIME_FORMATTER);
    }
}
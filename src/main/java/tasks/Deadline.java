// src/main/java/tasks/Deadline.java
package tasks;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected long byTimestamp;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public Deadline(String description, String by) {
        super(description);
        this.byTimestamp = LocalDateTime.parse(by, DATE_TIME_FORMATTER).toEpochSecond(ZoneOffset.UTC);
    }

    public Deadline(String description, long byTimestamp) {
        super(description);
        this.byTimestamp = byTimestamp;
    }

    @Override
    protected String getType() {
        return "D";
    }

    @Override
    public String toFileString() {
        return String.format("%s | %d | %s | %d", getType(), isDone ? 1 : 0, description, byTimestamp);
    }

    @Override
    public String toString() {
        LocalDateTime byDateTime = LocalDateTime.ofEpochSecond(byTimestamp, 0, ZoneOffset.UTC);
        return String.format("[%s][%s] %s (by: %s)", getType(), isDone ? "X" : " ", description, byDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")));
    }

    public LocalDateTime getByDateTime() {
        return LocalDateTime.ofEpochSecond(byTimestamp, 0, ZoneOffset.UTC);
    }
}
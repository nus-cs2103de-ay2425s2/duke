// src/main/java/tasks/Event.java
package tasks;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected long fromTimestamp;
    protected long toTimestamp;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public Event(String description, String from, String to) {
        super(description);
        this.fromTimestamp = LocalDateTime.parse(from, DATE_TIME_FORMATTER).toEpochSecond(ZoneOffset.UTC);
        this.toTimestamp = LocalDateTime.parse(to, DATE_TIME_FORMATTER).toEpochSecond(ZoneOffset.UTC);
    }

    public Event(String description, long fromTimestamp, long toTimestamp) {
        super(description);
        this.fromTimestamp = fromTimestamp;
        this.toTimestamp = toTimestamp;
    }

    @Override
    protected String getType() {
        return "E";
    }

    @Override
    public String toFileString() {
        return String.format("%s | %d | %s | %d | %d", getType(), isDone ? 1 : 0, description, fromTimestamp, toTimestamp);
    }

    @Override
    public String toString() {
        LocalDateTime fromDateTime = LocalDateTime.ofEpochSecond(fromTimestamp, 0, ZoneOffset.UTC);
        LocalDateTime toDateTime = LocalDateTime.ofEpochSecond(toTimestamp, 0, ZoneOffset.UTC);
        return String.format("[%s][%s] %s (from: %s to: %s)", getType(), isDone ? "X" : " ", description, fromDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")), toDateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mma")));
    }

    public LocalDateTime getFromDateTime() {
        return LocalDateTime.ofEpochSecond(fromTimestamp, 0, ZoneOffset.UTC);
    }

    public LocalDateTime getToDateTime() {
        return LocalDateTime.ofEpochSecond(toTimestamp, 0, ZoneOffset.UTC);
    }
}
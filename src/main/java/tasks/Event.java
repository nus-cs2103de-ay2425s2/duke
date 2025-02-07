// src/main/java/tasks/Event.java
package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task that starts and ends at specific date/time.
 */
public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    protected String getType() {
        return "E";
    }

    public LocalDateTime getFromDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
        return LocalDateTime.parse(from, formatter);
    }

    public LocalDateTime getToDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
        return LocalDateTime.parse(to, formatter);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
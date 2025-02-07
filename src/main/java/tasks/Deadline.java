// src/main/java/tasks/Deadline.java
package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    protected String getType() {
        return "D";
    }

    public LocalDateTime getByDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
        return LocalDateTime.parse(by, formatter);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
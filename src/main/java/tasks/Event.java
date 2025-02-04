package tasks;

/**
 * Represents a task that has a specific time range (start and end).
 * Extends the {@link Task} class by adding start and end times.
 */
public class Event extends Task {

    protected String start;
    protected String end;

    /**
     * Constructs a new {@code Event} task.
     *
     * @param description The description of the event task.
     * @param start       The starting time of the event.
     * @param end         The ending time of the event.
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the string representation of the event task.
     *
     * @return A string in the format "[E][status] description from: start to: end".
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " from: " + start + " to: " + end + ")";
    }
}

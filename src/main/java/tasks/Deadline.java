package tasks;

/**
 * Represents a task with a deadline.
 * Extends the Task class by adding a deadline field.
 */
public class Deadline extends Task {

    protected String deadline;

    /**
     * Constructs a new {@code Deadline} task.
     *
     * @param description The description of the deadline task.
     * @param deadline    The date or time by which the task must be completed.
     */
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    /**
     * Returns the string representation of the deadline task.
     *
     * @return A string in the format "[D][status] description (by: deadline)".
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + " )";
    }
}

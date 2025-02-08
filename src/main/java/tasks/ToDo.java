package tasks;

/**
 * Represents a ToDo task without any date/time attached to it.
 * Inherits from the Task class and provides basic functionality
 * for managing ToDo tasks.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the specified description.
     *
     * @param description The description of the ToDo task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the type of the task.
     *
     * @return A string "T" representing a ToDo task.
     */
    @Override
    protected String getType() {
        return "T";
    }
}

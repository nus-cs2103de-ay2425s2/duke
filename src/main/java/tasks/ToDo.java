package tasks;

/**
 * Represents a ToDo task without any date/time attached to it.
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    protected String getType() {
        return "T";
    }
}

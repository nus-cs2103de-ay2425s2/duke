package tasks;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    protected abstract String getType();

    @Override
    public String toString() {
        return "[" + getType() + "]" + (isDone ? "[X] " : "[ ] ") + description;
    }
}
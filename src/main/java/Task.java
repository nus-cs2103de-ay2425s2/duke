public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    // Mark the task as done
    public void markAsDone() {
        isDone = true;
    }

    // Mark the task as not done
    public void markAsNotDone() {
        isDone = false;
    }

    // Get the task description
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

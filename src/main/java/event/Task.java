package event;

public abstract class Task {
    protected TaskName taskName;

    public enum TaskName {
        READ,
        RETURN,
        ERROR
    }

    public String getTaskName() {
        return this.taskName.toString();
    }

    public static TaskName mapTaskName(String stringTaskName) {
        return switch (stringTaskName) {
            case "read" -> TaskName.READ;
            case "return" -> TaskName.RETURN;
            default -> TaskName.ERROR;
        };
    }

    public abstract String getTaskDetail();
}

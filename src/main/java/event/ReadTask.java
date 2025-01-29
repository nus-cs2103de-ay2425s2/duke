package event;

public class ReadTask extends Task {
    private TaskName taskName;
    private String taskDetail;

    public ReadTask(String taskDetail) {
        this.taskName = TaskName.READ;
        this.taskDetail = taskDetail;
    }

    @Override
    public String getTaskDetail() {
        return this.taskDetail;
    }
}

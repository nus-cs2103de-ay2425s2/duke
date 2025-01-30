package task;

public class ReturnTask extends Task {
    private String taskDetail;

    public ReturnTask (String taskDetail) {
        this.taskName = TaskName.RETURN;
        this.taskDetail = taskDetail;
    }

    @Override
    public String getTaskDetail() {
        return this.taskDetail;
    }
}

package task;

import action.ActionHandler;

/**
 * ToDoTask class
 */
public class ToDoTask extends Task {
    /**
     * Constructor for Todo Task
     * @param taskDetail String that indicates the task details
     */
    public ToDoTask(String taskDetail) {
        super(taskDetail, ActionHandler.Action.TODO);
    }

    @Override
    public String createSaveData() {
        return super.createSaveData();
    }
}

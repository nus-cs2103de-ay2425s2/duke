package user;

import action.ActionHandler;
import data.DataHandler;
import task.DeadLineTask;
import task.EventTask;
import task.Task;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * User class
 */
public class User {
    private List<Task> taskList;
    private Path dataFilePath;

    /**
     * Default Constructor for User
     */
    public User() {
        this.taskList = new ArrayList<>();
    }

    public User(String userName) throws IOException {
        this.taskList = new ArrayList<>();
        this.dataFilePath = DataHandler.programRoot.resolve("data").resolve("%s.txt".formatted(userName));
        buildTaskList(DataHandler.readFile(dataFilePath));
    }

    private void buildTaskList(List<String> inputDataList) {
        for (String inputData : inputDataList) {
            // Added escape character for | as "|" is considered as an or operator in regex
            List<String> data = List.of(inputData.split("\\%s".formatted(DataHandler.saveDelimiter)));

            // Recreate the task details from the saved file
            // Format of todo save: taskType|isTaskDone|taskDetails
            // Format of deadline save: taskType|isTaskDone|taskDetails|/by dateTime
            // Format of event save: taskType|isTaskDone|taskDetails|/from dateTime /to toDateTime
            List<String> taskDetails = List.of(String.join(" ", data.subList(2, data.size())).split(" "));

            Task taskCreated = ActionHandler.createTask(
                    Task.mapTaskType(data.getFirst()),
                    taskDetails);

            if (taskCreated != null) {
                taskCreated.setTaskDone(Boolean.parseBoolean(data.get(1)));
                addTask(taskCreated);
            }
        }
    }

    public void addTask(Task task) {
        this.taskList.addLast(task);
    }

    /**
     * getTaskList method that returns a string representation of the task list currently
     * @return String that represents the task list currently
     */
    public String getTaskList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            stringBuilder.append("%s. %s".formatted(
                    i + 1,
                    task.getTaskInformation())
            );
            if (i != taskList.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Sets task to be done
     * @param taskNumber int that indicates the task to set
     * @return String representation of updated task information
     */
    public String markTaskAsDone(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        requiredTask.setTaskDone(true);

        return requiredTask.getTaskInformation();
    }

    /**
     * Sets task to be not done
     * @param taskNumber int that indicates the task to set
     * @return String representation of updated task information
     */
    public String markTaskAsNotDone(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        requiredTask.setTaskDone(false);

        return requiredTask.getTaskInformation();
    }

    public int getNumberOfTasks() {
        return this.taskList.size();
    }

    /**
     * Deletes task from the task list
     * @param taskNumber int that indicates the task to delete, taskNumber is 0-indexed
     * @return String representation of deleted task information
     */
    public String deleteTask(int taskNumber) {
        Task requiredTask = this.taskList.get(taskNumber);
        String removedTaskInformation = requiredTask.getTaskInformation();
        this.taskList.remove(taskNumber);
        return removedTaskInformation;
    }

    public List<String> createSaveData() {
        List<String> saveInformationList = new ArrayList<>();
        for (Task task : taskList) {
            saveInformationList.add(task.createSaveData());
        }
        return saveInformationList;
    }

    public Path getDataFilePath() {
        return dataFilePath;
    }
}

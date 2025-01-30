package event;

import event.Task.TaskName;

import user.User;

import java.util.List;
import java.util.ArrayList;

/**
 * Class to handle events that should be happen
 */
public class EventHandler {

    /**
     * Method to process events that are input to the user
     * Precondition: eventString is already a valid input by the user
     * @param eventString Input String provided by the user
     * @param user User class that indicates the current user
     * @return List of Strings that indicate what needs to be output to the console
     */
    public List<String> processEvent(String eventString, User user) {
        List<String> outputMessages = new ArrayList<>();

        String[] eventStringTokens = eventString.split(" ");

        if (eventStringTokens[0].equalsIgnoreCase("list")) {
            outputMessages.add("Here are the tasks in your list: ");
            outputMessages.add(user.getTaskList());
        }
        else if (eventStringTokens[0].equalsIgnoreCase("bye")) {
            return outputMessages;
        }
        else {
            String stringTaskName = eventStringTokens[0];

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < eventStringTokens.length; i++) {
                stringBuilder.append(eventStringTokens[i]);

                if (i != eventStringTokens.length - 1) {
                    stringBuilder.append(" ");
                }
            }

            String taskDetail = stringBuilder.toString();

            Task task = createTask(eventStringTokens[0], taskDetail);

            user.addTask(task);

            outputMessages.add("added: %s %s".formatted(stringTaskName, taskDetail));
        }

        return outputMessages;
    }

    /**
     * Create Task
     * @param stringTaskName String representation of TaskName enum
     * @param taskDetail String representation for the details of the task
     * @return Task created
     */
    private Task createTask(String stringTaskName, String taskDetail) {
        TaskName taskName = Task.mapTaskName(stringTaskName);

        switch (taskName) {
            case READ -> {
                return new ReadTask(taskDetail);
            }
            case RETURN -> {
                return new ReturnTask(taskDetail);
            }
            default -> {
                return null;
            }
        }
    }
}

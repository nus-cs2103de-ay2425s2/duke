package event;

import task.Task;

import user.User;

import java.util.List;
import java.util.ArrayList;

/**
 * Class to handle events
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
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < eventStringTokens.length; i++) {
                stringBuilder.append(eventStringTokens[i]);

                if (i != eventStringTokens.length - 1) {
                    stringBuilder.append(" ");
                }
            }

            String taskDetail = stringBuilder.toString();

            Task task = createTask(taskDetail);

            user.addTask(task);

            outputMessages.add("added: %s".formatted(taskDetail));
        }

        return outputMessages;
    }

    /**
     * Create Task
     * @param taskDetail String representation for the details of the task
     * @return Task created
     */
    private Task createTask(String taskDetail) {
        return new Task(taskDetail);
    }
}

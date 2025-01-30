package action;

import task.DeadLineTask;
import task.EventTask;
import task.Task;
import task.ToDoTask;
import user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Class to handle events
 */
public class ActionHandler {

    /**
     * Method to process events that are input to the user
     * Precondition: eventString is already a valid input by the user
     *
     * @param eventString Input String provided by the user
     * @param user        User class that indicates the current user
     * @return List of Strings that indicate what needs to be output to the console
     */
    public List<String> processEvent(String eventString, User user) {
        List<String> outputMessages = new ArrayList<>();

        List<String> eventStringTokens = Arrays.asList(eventString.split(" "));

        if (eventStringTokens.getFirst().equalsIgnoreCase(Action.LIST.toString())) {
            outputMessages.add("Here are the tasks in your list: ");
            outputMessages.add(user.getTaskList());
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.BYE.toString())) {
            return outputMessages;
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.MARK.toString())) {
            outputMessages.add("Nice! I've marked this task as done:");
            outputMessages.add(user.markTaskAsDone(parseInt(eventStringTokens.get(1)) - 1));
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.UNMARK.toString())) {
            outputMessages.add("OK, I've marked this task as not done yet:");
            outputMessages.add(user.markTaskAsNotDone(parseInt(eventStringTokens.get(1)) - 1));
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.TODO.toString())) {
            outputMessages.add("Got it. I've added this todo task:");
            int taskIndex = user.addTask(
                    createTask(
                            Action.TODO,
                            eventStringTokens.subList(1, eventStringTokens.size())
                    ),
                    true
            );
            outputMessages.add(user.getTaskInformation(taskIndex));
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.DEADLINE.toString())) {
            outputMessages.add("Got it. I've added this deadline:");
            int taskIndex = user.addTask(
                    createTask(
                            Action.DEADLINE,
                            eventStringTokens.subList(1, eventStringTokens.size())
                    ),
                    true
            );
            outputMessages.add(user.getTaskInformation(taskIndex));
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.EVENT.toString())) {
            outputMessages.add("Got it. I've added this event:");
            int taskIndex = user.addTask(
                    createTask(
                            Action.EVENT,
                            eventStringTokens.subList(1, eventStringTokens.size())
                    ),
                    true
            );
            outputMessages.add(user.getTaskInformation(taskIndex));
        }
        // by default, should be adding a new task
        else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < eventStringTokens.size(); i++) {
                stringBuilder.append(eventStringTokens.get(i));

                if (i != eventStringTokens.size() - 1) {
                    stringBuilder.append(" ");
                }
            }

            String taskDetail = stringBuilder.toString();

            Task task = createTask(taskDetail);

            user.addTask(task);

            outputMessages.add("added: %s".formatted(taskDetail));
        }

        if (eventStringTokens.getFirst().equalsIgnoreCase(Action.EVENT.toString())
                || eventStringTokens.getFirst().equalsIgnoreCase(Action.TODO.toString())
                || eventStringTokens.getFirst().equalsIgnoreCase(Action.DEADLINE.toString())) {
            outputMessages.add("Now you have %s tasks in your list".formatted(user.getNumberOfTasks()));
        }

        return outputMessages;
    }

    private Task createTask(Action action, List<String> taskDetails) {
        if (action.equals(Action.TODO)) {
            return new ToDoTask(String.join(" ", taskDetails));
        }
        else if (action.equals(Action.DEADLINE)) {
            int deadLineIndex = taskDetails.indexOf("/by");

            return new DeadLineTask(
                    String.join(" ", taskDetails.subList(0, deadLineIndex)),
                    String.join(" ", taskDetails.subList(deadLineIndex + 1, taskDetails.size()))
            );
        }
        else if (action.equals(Action.EVENT)) {
            int fromIndex = taskDetails.indexOf("/from");
            int toIndex = taskDetails.indexOf("/to");

            return new EventTask(
                    String.join(" ", taskDetails.subList(0, fromIndex)),
                    String.join(" ", taskDetails.subList(fromIndex + 1, toIndex)),
                    String.join(" ", taskDetails.subList(toIndex + 1, taskDetails.size()))
            );
        }
        return null;
    }

    /**
     * Create Task
     *
     * @param taskDetail String representation for the details of the task
     * @return Task created
     */
    private Task createTask(String taskDetail) {
        return new Task(taskDetail);
    }

    public enum Action {
        LIST,
        MARK,
        UNMARK,
        BYE,
        TODO,
        DEADLINE,
        EVENT
    }
}

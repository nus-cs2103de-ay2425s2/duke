package action;

import data.DataHandler;
import task.DeadLineTask;
import task.EventTask;
import task.Task;
import task.ToDoTask;
import user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Class to handle actions
 */
public class ActionHandler {

    /**
     * Method to process events that are input to the user
     * Precondition: eventString is already a valid input by the user
     *
     * @param eventString Valid Input String provided by the user
     * @param user        User class that indicates the current user
     * @return List of Strings that indicates what needs to be output to the console
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
            Task createdTask = createTask(
                    Action.TODO,
                    eventStringTokens.subList(1, eventStringTokens.size())
            );
            user.addTask(createdTask);
            outputMessages.add(createdTask.getTaskInformation());
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.DEADLINE.toString())) {
            outputMessages.add("Got it. I've added this deadline:");
            Task createdTask = createTask(
                    Action.DEADLINE,
                    eventStringTokens.subList(1, eventStringTokens.size())
            );
            user.addTask(createdTask);
            outputMessages.add(createdTask.getTaskInformation());
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.EVENT.toString())) {
            outputMessages.add("Got it. I've added this event:");
            Task createdTask = createTask(
                    Action.EVENT,
                    eventStringTokens.subList(1, eventStringTokens.size())
            );
            user.addTask(createdTask);
            outputMessages.add(createdTask.getTaskInformation());
        }
        else if (eventStringTokens.getFirst().equalsIgnoreCase(Action.DELETE.toString())) {
            outputMessages.add("Noted. I've removed this task:");
            outputMessages.add(user.deleteTask(parseInt(eventStringTokens.get(1)) - 1));
            outputMessages.add("Now you have %s tasks in your list".formatted(user.getNumberOfTasks()));
        }

        // conditional add that indicates the new number of tasks in the list only when a new task is added
        if (eventStringTokens.getFirst().equalsIgnoreCase(Action.EVENT.toString())
                || eventStringTokens.getFirst().equalsIgnoreCase(Action.TODO.toString())
                || eventStringTokens.getFirst().equalsIgnoreCase(Action.DEADLINE.toString())) {
            outputMessages.add("Now you have %s tasks in your list".formatted(user.getNumberOfTasks()));
        }

        try {
            DataHandler.writeFile(user.getDataFilePath(), user.createSaveData(), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputMessages;
    }

    /**
     * Method to create a TODO/DEADLINE/EVENT task
     * @param action Action enum field that indicates what is the action to do
     * @param taskDetails Valid input string provided by the user
     * @return Task that encapsulates the required information to create TODO/DEADLINE/EVENT task
     */
    public static Task createTask(Action action, List<String> taskDetails) {
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
    @Deprecated
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
        EVENT,
        DELETE,
        DEFAULT
    }
}

package event;

import event.Task.TaskName;

import java.util.HashMap;
import java.util.Map;

public class EventHandler {
    // to figure out a way to process an event
    public void processEvent(String eventString) {

    }

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

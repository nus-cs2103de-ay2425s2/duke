import java.util.List;
import java.util.ArrayList;

public class TaskManager {
    private List<String> tasks;
    private List<Boolean> taskStatus;

    public TaskManager() {
        //false = not done, true = done
        tasks = new ArrayList<>();
        taskStatus = new ArrayList<>();
    }

    //addition of tasks
    public void addTask(String task) {
        tasks.add(task);
        taskStatus.add(false);
        System.out.println("Purr-sonally, I’d rather nap, but your task is on the list now! " + task);
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks in your list you silly cat!");
        } else {
            System.out.println("Here are the tasks in your list you silly cat!");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }
}

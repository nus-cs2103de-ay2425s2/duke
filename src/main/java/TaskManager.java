import java.util.List;
import java.util.ArrayList;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        //false = not done, true = done
        tasks = new ArrayList<>();
    }

    // addition of tasks
    public void addToDo(String description) {
        Task newTask = new ToDo(description);
        tasks.add(newTask);
        System.out.println("Purr-sonally, I’d rather nap, but your " + description + " task is on the list now!");
        System.out.println("Now you have " + tasks.size() + " tasks.");
    }

    public void addDeadline(String description, String deadline) {
        Task newTask = new Deadline(description, deadline);
        tasks.add(newTask);
        System.out.println("Purr-sonally, I’d rather nap, but your " + description + " task is on the list now!");
        System.out.println("Now you have " + tasks.size() + " tasks.");
    }

    public void addEvent(String description, String from, String to) {
        Task newTask = new Event(description, from, to);
        tasks.add(newTask);
        System.out.println("Purr-sonally, I’d rather nap, but your " + description + " task is on the list now!");
        System.out.println("Now you have " + tasks.size() + " tasks.");
    }

    // listing out tasks
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks in your list you silly cat!");
        } else {
            System.out.println("Here are the tasks in your list you silly cat!");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
    }

    // marking a task as completed
    public void markTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            tasks.get(taskNumber - 1).markAsDone();
            System.out.println("A paw-sitive development! Wanna celebrate with some head pats?");
            System.out.println(task);
        } else {
            System.out.println("Invalid task number! Are you playing with yarn again?");
        }
    }

    // unmark a task as completed
    public void unmarkTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            tasks.get(taskNumber - 1).markAsNotDone();
            System.out.println("We don't judge around here.");
            System.out.println(task);
        } else {
            System.out.println("Invalid task number! Are you playing with yarn again?");
        }
    }
}

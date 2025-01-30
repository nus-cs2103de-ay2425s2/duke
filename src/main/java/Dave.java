import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Task {
    private String type; // "T" = To-Do, "D" = Deadline, "E" = Events
    private String task;
    private boolean status; // false = not done, true = done
    private String details; // Other info for Deadline and Event tasks

    public Task(String type, String task, String details) {
        this.type = type;
        this.task = task;
        this.status = false;
        this.details = details;
    }

    public void mark() {
        this.status = true;
    }

    public void unmark() {
        this.status = false;
    }

    public String toString() {
        String taskStatus = status ? "[X]" : "[ ]";
        String taskDetails = details != null ? " " + details : "";
        return "[" + type + "]" + taskStatus + " " + task + (taskDetails);
    }
}

class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String type, String task, String details) {
        tasks.add(new Task(type, task, details));
        System.out.println("    ____________________________________________________________");
        System.out.println("      Got it. I've added this task:");
        System.out.println("         " + tasks.getLast());
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }

    public void listTasks() {
        System.out.println("    ____________________________________________________________");
        if (tasks.isEmpty()) {
            System.out.println("      No tasks to display.");
        } else {
            System.out.println("      Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("      " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("    ____________________________________________________________");
    }

    public void markTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task task = tasks.get(taskNumber - 1);
            if (!task.toString().contains("[X]")) {
                task.mark();
                System.out.println("    ____________________________________________________________");
                System.out.println("      Nice! I've marked this task as done:");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      This task is already marked as done:");
            }
            System.out.println("         " + task);
            System.out.println("    ____________________________________________________________");
        }
    }

    public void unmarkTask(int taskNumber) {
        if (isValidTaskNumber(taskNumber)) {
            Task task = tasks.get(taskNumber - 1);
            if (task.toString().contains("[X]")) {
                task.unmark();
                System.out.println("    ____________________________________________________________");
                System.out.println("      OK, I've marked this task as not done yet:");
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      This task is already marked as not done:");
            }
            System.out.println("         " + task);
            System.out.println("    ____________________________________________________________");
        }
    }

    private boolean isValidTaskNumber(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("    ____________________________________________________________");
            System.out.println("      Invalid task number.");
            System.out.println("    ____________________________________________________________");
            return false;
        }
        return true;
    }
}

public class Dave {
    public static void main(String[] args) {
        String name = "Dave";
        TaskManager taskManager = new TaskManager();
        List<String> greetings = Arrays.asList("hello", "hi", "hey", "yo");
        List<String> goodbyes = Arrays.asList("bye", "goodbye");

        // Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Initial greeting
        System.out.println("    ____________________________________________________________");
        System.out.printf("      Hello! I'm %s%n", name);
        System.out.println("      What can I do for you?");
        System.out.println("    ____________________________________________________________");

        while (true) {
            // Read user input
            String userInput = scanner.nextLine();

            // Exit when user types the command 'bye'
            if (goodbyes.contains(userInput.toLowerCase())) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Bye. Hope to see you again soon!");
                System.out.println("    ____________________________________________________________");
                break;
            }

            // Additional greetings
            if (greetings.contains(userInput.toLowerCase())) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Hey, what's up?");
                System.out.println("    ____________________________________________________________");
                continue;
            }

            // Display list when requested
            if (userInput.equalsIgnoreCase("list")) {
                taskManager.listTasks();
            }
            // Add To-Do tasks
            else if (userInput.toLowerCase().startsWith("todo ")) {
                String todoInfo = userInput.substring(5).trim();
                if (!todoInfo.isEmpty()) {
                    taskManager.addTask("T", todoInfo, null);
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Invalid format. Use: todo <task>");
                    System.out.println("    ____________________________________________________________");
                }
            }
            // Add Deadline tasks
            else if (userInput.toLowerCase().startsWith("deadline ")) {
                String[] deadlineInfo = userInput.substring(9).split(" /by", 2);
                if (deadlineInfo.length == 2) {
                    String task = deadlineInfo[0].trim();
                    String deadline = deadlineInfo[1].trim();
                    taskManager.addTask("D", task, "(by: " + deadline + ")");
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Invalid format. Use: deadline <task> /by <deadline date/time>");
                    System.out.println("    ____________________________________________________________");
                }
            }
            // Add Event tasks
            else if (userInput.toLowerCase().startsWith("event ")) {
                String[] eventInfo = userInput.substring(6).split(" /start | /end ", 3);
                if (eventInfo.length == 3) {
                    String task = eventInfo[0].trim();
                    String start = eventInfo[1].trim();
                    String end = eventInfo[2].trim();
                    taskManager.addTask("E", task, "(start: " + start + ", end: " + end + ")");
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Invalid format. Use: event <task> /start <start date/time> /end <end date/time>");
                    System.out.println("    ____________________________________________________________");
                }
            }
            // Mark tasks as done
            else if (userInput.toLowerCase().startsWith("mark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(5).trim());
                taskManager.markTask(taskNumber);
            }
            // Unmark tasks as not done
            else if (userInput.toLowerCase().startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(7).trim());
                taskManager.unmarkTask(taskNumber);
            }
            // Handle Invalid Commands
            else {
                System.out.println("    ____________________________________________________________");
                System.out.println("    --Invalid Command--");
                System.out.println("      Add to list");
                System.out.println("            'todo':         todo <task>");
                System.out.println("            'deadline':     deadline <task> /by <deadline date/time");
                System.out.println("            'event':        event <task> /start <start date/time> /end <end date/time>");
                System.out.println("      View list");
                System.out.println("            'list':         list");
                System.out.println("      Mark/Unmark tasks in list");
                System.out.println("            'mark':         mark <task>");
                System.out.println("            'unmark':       unmark <task>");
                System.out.println("    ____________________________________________________________");
            }
        }
        // Close scanner
        scanner.close();
    }
}

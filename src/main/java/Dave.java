import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class Dave {
    public static void main(String[] args) {
        String name = "Dave";
        List<String> greetings = Arrays.asList("hello", "hi", "hey", "yo");
        List<String> goodbyes = Arrays.asList("bye", "goodbye");
        String[] tasks = new String[100];
        boolean[] taskStatus = new boolean[100]; // false = not done, true = done
        String[] taskTypes = new String[100]; // "T" = To-Do, "D" = Deadline, "E" = Events
        String[] taskDetails = new String[100]; // Other info for Deadline and Event tasks
        int taskCount = 0;

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

            if (greetings.contains(userInput.toLowerCase())) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Hey, what's up?");
                System.out.println("    ____________________________________________________________");
                continue;
            }

            // Display list when requested
            if (userInput.equalsIgnoreCase("list")) {
                System.out.println("    ____________________________________________________________");
                if (taskCount == 0) {
                    System.out.println("      No tasks to display.");
                } else {
                    System.out.println("      Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        String status = taskStatus[i] ? "[X]" : "[ ]";
                        String type = "[" + taskTypes[i] + "]";
                        String details =  taskDetails[i];
                        System.out.print("      " + (i + 1) + "." + type + status + " " + tasks[i] + " ");
                        System.out.println(details != null ? details : "");
                    }
                }
                System.out.println("    ____________________________________________________________");
                continue;
            }

            // Add To-Do tasks
            if (userInput.toLowerCase().startsWith("todo ")) {
                String task = userInput.substring(5).trim();
                if (taskCount < 100) {
                    tasks[taskCount] = task;
                    taskTypes[taskCount] = "T";
                    taskStatus[taskCount] = false;
                    taskCount++;
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Got it. I've added this task:");
                    System.out.println("         [T][ ] " + task);
                    System.out.println("      Now you have " + taskCount + " tasks in the list.");
                    System.out.println("    ____________________________________________________________");
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Task list is full. Unable to add more tasks.");
                    System.out.println("    ____________________________________________________________");
                }
                continue;
            }

            // Add Deadline tasks
            if (userInput.toLowerCase().startsWith("deadline ")) {
                String[] deadlineInfo = userInput.substring(9).split(" /by", 2);
                if (deadlineInfo.length == 2) {
                    String task = deadlineInfo[0].trim();
                    String deadline = deadlineInfo[1].trim();
                    if (taskCount < 100) {
                        tasks[taskCount] = task;
                        taskTypes[taskCount] = "D";
                        taskDetails[taskCount] = "(by: " + deadline + ")";
                        taskStatus[taskCount] = false;
                        taskCount++;
                        System.out.println("    ____________________________________________________________");
                        System.out.println("      Got it. I've added this task:");
                        System.out.println("         [D][ ] " + task + " " + taskDetails[taskCount - 1]);
                        System.out.println("      Now you have " + taskCount + " tasks in the list.");
                        System.out.println("    ____________________________________________________________");
                    } else {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("      Task list is full. Unable to add more tasks.");
                        System.out.println("    ____________________________________________________________");
                    }
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Invalid format. Use: deadline <task> /by <deadline date/time>");
                    System.out.println("    ____________________________________________________________");
                }
                continue;
            }

            // Add Event tasks
            if (userInput.toLowerCase().startsWith("event ")) {
                String[] eventInfo = userInput.substring(6).split(" /start | /end ", 3);
                if (eventInfo.length == 3) {
                    String task = eventInfo[0].trim();
                    String start = eventInfo[1].trim();
                    String end = eventInfo[2].trim();
                    if (taskCount < 100) {
                        tasks[taskCount] = task;
                        taskTypes[taskCount] = "E";
                        taskDetails[taskCount] = "(start: " + start + ", end: " + end + ")";
                        taskStatus[taskCount] = false;
                        taskCount++;
                        System.out.println("    ____________________________________________________________");
                        System.out.println("      Got it. I've added this task:");
                        System.out.println("         [E][ ] " + task + " " + taskDetails[taskCount - 1]);
                        System.out.println("      Now you have " + taskCount + " tasks in the list.");
                        System.out.println("    ____________________________________________________________");
                    } else {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("      Task list is full. Unable to add more tasks.");
                        System.out.println("    ____________________________________________________________");
                    }
                } else {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Invalid format. Use: event <task> /start <start date/time> /end <end date/time>");
                    System.out.println("    ____________________________________________________________");
                }
                continue;
            }

            // Mark tasks as done
            if (userInput.toLowerCase().startsWith("mark ")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.substring(5).trim()) - 1;
                    if (taskNumber >= 0 && taskNumber < taskCount) {
                        if (!taskStatus[taskNumber]) {
                            taskStatus[taskNumber] = true;
                            System.out.println("    ____________________________________________________________");
                            System.out.println("      Nice! I've marked this task as done:");
                        } else {
                            System.out.println("    ____________________________________________________________");
                            System.out.println("      This task is already marked as done:");
                        }
                        System.out.println("         [" + taskTypes[taskNumber] + "][X] " + tasks[taskNumber] + " ");
                        System.out.println(taskDetails[taskNumber] != null ? taskDetails[taskNumber] : "");
                        System.out.println("    ____________________________________________________________");
                    } else {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("      Invalid task number.");
                        System.out.println("    ____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Please enter a valid task number.");
                    System.out.println("    ____________________________________________________________");
                }
                continue;
            }

            // Unmark tasks as not done
            if (userInput.toLowerCase().startsWith("unmark ")) {
                try {
                    int taskNumber = Integer.parseInt(userInput.substring(7).trim()) - 1;
                    if (taskNumber >= 0 && taskNumber < taskCount) {
                        if (taskStatus[taskNumber]) {
                            taskStatus[taskNumber] = false;
                            System.out.println("    ____________________________________________________________");
                            System.out.println("      OK, I've marked this task as not done yet:");
                        } else {
                            System.out.println("    ____________________________________________________________");
                            System.out.println("      This task is already marked as not done:");
                        }
                        System.out.println("         [" + taskTypes[taskNumber] + "][ ] " + tasks[taskNumber] + " ");
                        System.out.println(taskDetails[taskNumber] != null ? taskDetails[taskNumber] : "");
                        System.out.println("    ____________________________________________________________");
                    } else {
                        System.out.println("    ____________________________________________________________");
                        System.out.println("      Invalid task number.");
                        System.out.println("    ____________________________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("      Please enter a valid task number.");
                    System.out.println("    ____________________________________________________________");
                }
                continue;
            }

            // Store text entered by user in list if less than 100 tasks
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

        // Close scanner
        scanner.close();
    }
}

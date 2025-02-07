package davethebrave.parser;

import davethebrave.task.TaskManager;

public class Parser {
    public static void parseCommand(String command, TaskManager taskManager) {
        // Display list when requested
        if (command.equalsIgnoreCase("list")) {
            taskManager.listTasks();
        }
        else if (command.toLowerCase().startsWith("find ")) {
            String keyword = command.substring(5).trim();
            if (!keyword.isEmpty()) {
                taskManager.findTask(keyword);
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Invalid format. Use: find <keyword>");
                System.out.println("    ____________________________________________________________");
            }
        }
        // Add To-Do tasks
        else if (command.toLowerCase().startsWith("todo ")) {
            String todoInfo = command.substring(5).trim();
            if (!todoInfo.isEmpty()) {
                taskManager.addTask("T", todoInfo, null);
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Invalid format. Use: todo <task>");
                System.out.println("    ____________________________________________________________");
            }
        }
        // Add Deadline tasks
        else if (command.toLowerCase().startsWith("deadline ")) {
            String[] deadlineInfo = command.substring(9).split(" /by", 2);
            if (deadlineInfo.length == 2) {
                String task = deadlineInfo[0].trim();
                String deadline = deadlineInfo[1].trim();
                taskManager.addTask("D", task, deadline);
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("      Invalid format. Use: deadline <task> /by yyyy-MM-dd");
                System.out.println("    ____________________________________________________________");
            }
        }
        // Add Event tasks
        else if (command.toLowerCase().startsWith("event ")) {
            String[] eventInfo = command.substring(6).split(" /start | /end ", 3);
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
        // Delete tasks
        else if (command.toLowerCase().startsWith("delete ")) {
            if (command.substring(7).trim().isEmpty()) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      davethebrave.Task Number cannot be empty.");
                System.out.println("    ____________________________________________________________");
            }
            int taskNumber = Integer.parseInt(command.substring(7).trim());
            taskManager.deleteTask(taskNumber);
        }
        // Mark tasks as done
        else if (command.toLowerCase().startsWith("mark ")) {
            if (command.substring(5).trim().isEmpty()) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      davethebrave.Task Number cannot be empty.");
                System.out.println("    ____________________________________________________________");
            }
            int taskNumber = Integer.parseInt(command.substring(5).trim());
            taskManager.markTask(taskNumber);
        }
        // Unmark tasks as not done
        else if (command.toLowerCase().startsWith("unmark ")) {
            if (command.substring(7).trim().isEmpty()) {
                System.out.println("    ____________________________________________________________");
                System.out.println("      davethebrave.Task Number cannot be empty.");
                System.out.println("    ____________________________________________________________");
            }
            int taskNumber = Integer.parseInt(command.substring(7).trim());
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
            System.out.println("      Delete task from list");
            System.out.println("            'delete':       delete <task number>");
            System.out.println("      Find task from list");
            System.out.println("            'find':       find <keyword>");
            System.out.println("    ____________________________________________________________");
        }
    }
}

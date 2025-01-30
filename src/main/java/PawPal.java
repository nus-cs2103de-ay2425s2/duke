import java.util.Scanner;

public class PawPal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        String name = "PawPal";
        System.out.println("-----------------------------------------");
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you? \n");
        System.out.println("-----------------------------------------");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon! Meow");
                break;
            } else if (input.equalsIgnoreCase("list")) {
                taskManager.listTasks();
            } else if (input.startsWith("mark")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    taskManager.markTask(taskNumber);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("That's not a valid number!");
                }
            } else if (input.startsWith("unmark")) {
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    taskManager.unmarkTask(taskNumber);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("That's not a valid number!");
                }
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                taskManager.addToDo(description);
            } else if (input.startsWith("deadline")) {
                String[] parts = input.substring(9).split(" /by ",2);
                if (parts.length == 2) {
                    taskManager.addDeadline(parts[0].trim(), parts[1].trim());
                } else {
                    System.out.println("That's not a valid deadline! Use deadline <description> /by <deadline>");
                }
            } else if (input.startsWith("event")) {
                String[] parts = input.substring(6).split(" /from | /to ",3);
                if (parts.length == 3) {
                    taskManager.addEvent(parts[0].trim(), parts[1].trim(), parts[2].trim());
                } else {
                    System.out.println("That's not a valid deadline! Use event <description> /from <start> /to <end>");
                }
            } else {
                System.out.println("Sorry, that's not a valid command!");
            }
        }
    }
}

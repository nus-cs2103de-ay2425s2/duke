package PawPal.core;

import PawPal.utils.Command;
import PawPal.utils.TaskList;
import PawPal.utils.Printer;

/**
 * Parses and processes user input for the PawPal.core.PawPal chatbot.
 * Determines the command type and delegates actions to the {@link TaskList}.
 */
class Parser {

    private final TaskList taskList;
    private final Printer printer;

    /**
     * Constructs a new {@code PawPal.core.Parser} instance.
     *
     * @param taskList The {@link TaskList} responsible for managing PawPal.core.PawPal.tasks.
     */
    public Parser(TaskList taskList) {
        this.taskList = taskList;
        this.printer = new Printer();
    }

    /**
     * Parses the user input and assigns it to the appropriate task manager method.
     *
     * @param input The user input string.
     */
    public void parse(String input) {
        Command command = parseCommand(input);
        switch (command) {
        case LIST:
            processListCommand();
            break;
        case MARK:
            processMarkCommand(input, true);
            break;
        case UNMARK:
            processMarkCommand(input, false);
            break;
        case TODO:
            processToDoCommand(input);
            break;
        case DEADLINE:
            processDeadlineCommand(input);
            break;
        case EVENT:
            processEventCommand(input);
            break;
        case DELETE:
            processDeleteCommand(input);
            break;
        default:
            printer.printInvalidCommand();
            break;
        }
    }

    private void processListCommand() {
        printer.printTaskList(taskList.getTasks());
    }

    /**
     * Determines the command type from the user input.
     *
     * @param input The user input string.
     * @return The corresponding Command enum value.
     */
    private Command parseCommand(String input) {
        String commandWord = input.split(" ")[0].toUpperCase();
        try {
            return Command.valueOf(commandWord);
        } catch (IllegalArgumentException e) {
            return Command.INVALID;
        }
    }

    /**
     * Processes the "mark" or "unmark" command.
     *
     * @param input The user input string.
     * @param mark  true if the command is "mark", false if it is "unmark".
     */
    private void processMarkCommand(String input, boolean mark) {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                throw new NumberFormatException();
            }
            int taskNumber = Integer.parseInt(parts[1].trim());
            if (mark) {
                taskList.markTask(taskNumber);
            } else {
                taskList.unmarkTask(taskNumber);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            printer.printInvalidTaskNumber();
        }
    }

    /**
     * Processes the "delete" command.
     *
     * @param input The user input string.
     */
    private void processDeleteCommand(String input) {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                throw new NumberFormatException();
            }
            int taskNumber = Integer.parseInt(parts[1].trim());
            taskList.deleteTask(taskNumber);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            printer.printInvalidTaskNumber();
        }
    }

    /**
     * Processes the "todo" command to add a ToDo task.
     *
     * @param input The user input string.
     */
    private void processToDoCommand(String input) {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            printer.printMissingToDoDetails();
        } else {
            taskList.addToDo(description);
        }
    }

    /**
     * Processes the "deadline" command to add a Deadline task.
     *
     * @param input The user input string.
     */
    private void processDeadlineCommand(String input) {
        if (input.trim().equalsIgnoreCase("deadline")) {
            printer.printMissingDeadlineDetails();
            return;
        }
        String[] parts = input.substring(9).split(" /by ", 2);
        if (parts.length == 2) {
            taskList.addDeadline(parts[0].trim(), parts[1].trim());
        } else {
            printer.printMissingDeadlineDetails();
        }
    }

    /**
     * Processes the "event" command to add an Event task.
     *
     * @param input The user input string.
     */
    private void processEventCommand(String input) {
        if (input.trim().equalsIgnoreCase("event")) {
            printer.printMissingEventDetails();
            return;
        }
        String[] parts = input.substring(6).split(" /from | /to ", 3);
        if (parts.length == 3) {
            taskList.addEvent(parts[0].trim(), parts[1].trim(), parts[2].trim());
        } else {
            printer.printMissingEventDetails();
        }
    }
}

import utils.Command;
import utils.TaskManager;
import utils.Printer;

/**
 * Parses and processes user input for the PawPal chatbot.
 * Determines the command type and delegates actions to the {@link TaskManager}.
 */
class Parser {

    private final TaskManager taskManager;
    private final Printer printer;

    /**
     * Constructs a new {@code Parser} instance.
     *
     * @param taskManager The {@link TaskManager} responsible for managing tasks.
     */
    public Parser(TaskManager taskManager) {
        this.taskManager = taskManager;
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
            taskManager.listTasks();
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
                taskManager.markTask(taskNumber);
            } else {
                taskManager.unmarkTask(taskNumber);
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
            taskManager.deleteTask(taskNumber);
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
            taskManager.addToDo(description);
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
            taskManager.addDeadline(parts[0].trim(), parts[1].trim());
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
            taskManager.addEvent(parts[0].trim(), parts[1].trim(), parts[2].trim());
        } else {
            printer.printMissingEventDetails();
        }
    }
}

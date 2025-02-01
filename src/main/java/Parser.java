class Parser {
    private final TaskManager taskManager;
    private final Printer printer;

    public Parser(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.printer = new Printer();
    }

    // Parse the user input and assign to the appropriate task manager method
    public void parse(String input) {
        Command command = parseCommand(input);
        switch (command) {  // Determine the command by splitting input
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

    // Determine the command type from the input
    private Command parseCommand(String input) {
        String commandWord = input.split(" ")[0].toUpperCase();
        try {
            return Command.valueOf(commandWord);
        } catch (IllegalArgumentException e) {
            return Command.INVALID;
        }
    }

    // method to process "mark" or "unmark" command
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

    // Process Delete Command
    private void processDeleteCommand(String input) {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) throw new NumberFormatException();
            int taskNumber = Integer.parseInt(parts[1].trim());
            taskManager.deleteTask(taskNumber);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            printer.printInvalidTaskNumber();
        }
    }

    // method to process adding a "ToDo"
    private void processToDoCommand(String input) {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            printer.printMissingToDoDetails();
        } else {
            taskManager.addToDo(description);
        }
    }

    // Process the command to add a deadline task
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

    // Process the command to add an event task
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

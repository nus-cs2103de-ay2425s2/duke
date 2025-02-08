// src/main/java/utils/CommandParser.java
package utils;

import commands.*;

public class CommandParser {
    public static Command parse(String input, String commandType, Storage storage) {
        switch (commandType) {
            case "bye":
                return new ByeCommand();
            case "add":
                String taskDescription = input.substring(3).trim();
                if (taskDescription.isEmpty()) {
                    throw new IllegalArgumentException("Task description cannot be empty. Use: add <task description>");
                }
                return new AddCommand(taskDescription, storage);
            case "deadline":
                String[] parts = input.substring(8).trim().split(" /by ");
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Invalid input format. Use: deadline <task> /by <date>");
                }
                return new DeadlineCommand(parts[0].trim(), parts[1].trim(), storage);
            case "event":
                String[] eventParts = input.substring(5).trim().split(" /from ");
                if (eventParts.length < 2) {
                    throw new IllegalArgumentException("Invalid input format. Use: event <task> /from <start> /to <end>");
                }
                String description = eventParts[0];
                String[] timeParts = eventParts[1].split(" /to ");
                if (timeParts.length < 2) {
                    throw new IllegalArgumentException("Invalid input format. Use: event <task> /from <start> /to <end>");
                }
                return new EventCommand(description, timeParts[0].trim(), timeParts[1].trim(), storage);
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(input, storage);
            case "unmark":
                return new UnmarkCommand(input, storage);
            case "delete":
                return new DeleteCommand(input, storage);
            case "list_day":
                return new ListDayCommand(input);
            case "help":
            case "?":
                return new HelpCommand();
            case "clear":
                return new ClearCommand(storage);
            case "cheer": // Add this case
                return new CheerCommand();
            default:
                throw new IllegalArgumentException("Unknown command type.");
        }
    }
}
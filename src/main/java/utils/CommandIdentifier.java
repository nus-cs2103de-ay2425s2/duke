// src/main/java/utils/CommandIdentifier.java
package utils;

/**
 * Utility class for identifying commands from user input.
 * Determines the type of command based on specific keywords.
 */
public class CommandIdentifier {

    /**
     * Identifies the command type from the given user input.
     *
     * @param input The user input string.
     * @return A string representing the identified command type,
     *         such as "add", "deadline", "event", "list", "mark",
     *         "unmark", "delete", "list_day", or "unknown" if no match is found.
     */
    public static String identify(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return "bye";
        } else if (input.trim().equalsIgnoreCase("help") || input.trim().equals("?")) {
            return "help";
        } else if (input.trim().equalsIgnoreCase("clear")) {
            return "clear";
        } else if (input.startsWith("add")) {
            return "add";
        } else if (input.startsWith("deadline")) {
            return "deadline";
        } else if (input.startsWith("event")) {
            return "event";
        } else if (input.trim().equalsIgnoreCase("list")) {
            return "list";
        } else if (input.startsWith("mark")) {
            return "mark";
        } else if (input.startsWith("unmark")) {
            return "unmark";
        } else if (input.startsWith("delete")) {
            return "delete";
        } else if (input.startsWith("list_day")) {
            return "list_day";
        } else if (input.startsWith("find")) { // Add this line
            return "find";
        } else if (input.trim().equalsIgnoreCase("cheer")) { // Add this line
            return "cheer";
        } else {
            return "unknown";
        }
    }
}
// src/main/java/utils/CommandIdentifier.java
package utils;

public class CommandIdentifier {
    public static String identify(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return "bye";
        } else if (input.equalsIgnoreCase("help") || input.equals("?")) {
            return "help";
        } else if (input.equalsIgnoreCase("clear")) {
            return "clear";
        } else if (input.startsWith("add ")) {
            return "add";
        } else if (input.startsWith("deadline ")) {
            return "deadline";
        } else if (input.startsWith("event ")) {
            return "event";
        } else if (input.equalsIgnoreCase("list")) {
            return "list";
        } else if (input.startsWith("mark ")) {
            return "mark";
        } else if (input.startsWith("unmark ")) {
            return "unmark";
        } else if (input.startsWith("delete ")) {
            return "delete";
        } else if (input.startsWith("list_day ")) {
            return "list_day";
        } else {
            return "unknown";
        }
    }
}
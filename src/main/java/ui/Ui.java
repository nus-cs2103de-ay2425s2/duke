package ui;

import java.util.Scanner;

/**
 * Handles all interactions with the user, including displaying messages
 * and reading user input.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("========================================");
        System.out.println("Hello! I'm Rucia");
        System.out.println("How can I assist you today?");
        System.out.println("Send \"Help\" or \"?\" for instructions on how to use me!");
        System.out.println("========================================");
    }

    /**
     * Displays the exit message to the user.
     */
    public void showExit() {
        System.out.println("========================================");
        System.out.println("Rucia: Bye. Hope to see you again soon!");
        System.out.println("========================================");
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        System.out.println("Rucia: " + message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println("Rucia: OOPS! " + message);
    }

    /**
     * Reads the next command from the user.
     *
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a divider line.
     */
    public void showLine() {
        System.out.println("========================================");
    }
}

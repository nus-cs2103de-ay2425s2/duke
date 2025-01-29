package io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UI class to handle tasks related to the User Interface
 */
public class UI {
    private final Input INPUT = new Input();
    private final Output OUTPUT = new Output();
    private final String DIVIDER = "_______________";
    private final String CHATBOT_NAME;

    /**
     * Constructor
     */
    public UI(String botName) {
        this.CHATBOT_NAME = botName;
    }

    /**
     * Get valid user input from console
     * @return User input String
     */
    public String getUserInput() {
        return INPUT.getValidUserInput();
    }

    /**
     * Displays custom message to the console
     * @param messages list of messages to output to console
     * @throws IOException When IO fails
     */
    public void displayMessageWithDivider(List<String> messages) throws IOException {
        messages.addFirst(this.DIVIDER);
        messages.add(this.DIVIDER);
        OUTPUT.printOutput(messages, "\n", "\n");
    }

    public void displayMessage(List<String> messages) throws IOException {
        OUTPUT.printOutput(messages, "\n", "\n");
    }

    /**
     * Display welcome message to the console
     * @throws IOException When IO fails
     */
    public void displayWelcomeMessage() throws IOException {
        List<String> welcomeMessages = new ArrayList<>();
        welcomeMessages.add(this.DIVIDER);
        welcomeMessages.add(String.format("Hello! I'm %s", this.CHATBOT_NAME));
        welcomeMessages.add("What can I do for you?");
        welcomeMessages.add(this.DIVIDER);
        OUTPUT.printOutput(welcomeMessages, "\n", "\n");
    }

    /**
     * Display exit message to the console
     * @throws IOException When IO fails
     */
    public void displayExitMessage() throws IOException{
        List<String> exitMessages = new ArrayList<>();
        exitMessages.add(this.DIVIDER);
        exitMessages.add("Bye. Hope to see you again soon!");
        exitMessages.add(this.DIVIDER);
        OUTPUT.printOutput(exitMessages, "\n", "\n");
    }
}

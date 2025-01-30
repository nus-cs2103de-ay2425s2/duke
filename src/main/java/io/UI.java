package io;

import event.EventHandler;
import event.EventHandler.Event;

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
    public String getValidUserInput() throws IOException {
        boolean isUserInputValid = false;

        String userInput;

        do {
            userInput = INPUT.getUserInput();
            if (this.isInputValid(userInput)) {
                isUserInputValid = true;
            }
        }
        while (!isUserInputValid);

        return userInput;
    }

    /**
     * Check if user input is valid
     * @param userInput Input string provided by the user with no trailing or leading whitespaces
     * @return boolean to indicate if it is a valid userInput
     */
    private boolean isInputValid(String userInput) {
        String[] userInputTokens = userInput.split(" ");

        if (userInputTokens[0].equalsIgnoreCase(Event.LIST.toString())
                || userInputTokens[0].equalsIgnoreCase(Event.BYE.toString())) {
            return userInputTokens.length == 1;
        }
        else if (userInputTokens[0].equalsIgnoreCase(Event.MARK.toString())
                || (userInputTokens[0].equalsIgnoreCase(Event.UNMARK.toString()))) {
            if (userInputTokens.length == 1) {
                return false;
            }
            else if (userInputTokens.length > 2) {
                return false;
            }
            else {
                try {
                    Integer.parseInt(userInputTokens[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
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

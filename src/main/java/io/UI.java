package io;

import action.ActionHandler.Action;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.DayOfWeek;
import java.time.MonthDay;

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

        if (userInputTokens[0].equalsIgnoreCase(Action.LIST.toString())
                || userInputTokens[0].equalsIgnoreCase(Action.BYE.toString())) {
            return userInputTokens.length == 1;
        }
        else if (userInputTokens[0].equalsIgnoreCase(Action.MARK.toString())
                || (userInputTokens[0].equalsIgnoreCase(Action.UNMARK.toString()))) {
            if (userInputTokens.length == 1) {
                return false;
            }
            else if (userInputTokens.length > 2) {
                return false;
            }

            try {
                Integer.parseInt(userInputTokens[1]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        }
        else if (userInputTokens[0].equalsIgnoreCase(Action.TODO.toString())) {
            return userInputTokens.length > 1;
        }
        else if (userInputTokens[0].equalsIgnoreCase(Action.DEADLINE.toString())) {
            if (userInputTokens.length == 1) {
                return false;
            }

            if (!Arrays.asList(userInputTokens).contains("/by")) {
                return false;
            }

            int deadLineIndex = Arrays.asList(userInputTokens).indexOf("/by") + 1;

            if (deadLineIndex == userInputTokens.length) {
                return false;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = deadLineIndex; i < userInputTokens.length; i++)  {
                stringBuilder.append(userInputTokens[i]);
                if (i != userInputTokens.length - 1) {
                    stringBuilder.append(" ");
                }
            }
            String stringDateTime = stringBuilder.toString();

            return isValidDay(stringDateTime) || isValidDateTime(stringDateTime);
        }
        else if (userInputTokens[0].equalsIgnoreCase(Action.EVENT.toString())) {
            if (!(Arrays.asList(userInputTokens).contains("/from") && Arrays.asList(userInputTokens).contains("/to"))) {
                return false;
            }

            int fromIndex = Arrays.asList(userInputTokens).indexOf("/from");
            int toIndex = Arrays.asList(userInputTokens).indexOf("/to");

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = fromIndex + 1; i < toIndex; i++) {
                stringBuilder.append(userInputTokens[i]);
                if (i != toIndex - 1) {
                    stringBuilder.append(" ");
                }
            }
            String fromDateTime = stringBuilder.toString();

            if (!(isValidDay(fromDateTime) || isValidDateTime(fromDateTime))) {
                return false;
            }

            stringBuilder.setLength(0);

            for (int i = toIndex + 1; i < userInputTokens.length; i++) {
                stringBuilder.append(userInputTokens[i]);
                if (i != userInputTokens.length - 1) {
                    stringBuilder.append(" ");
                }
            }
            String toDateTime = stringBuilder.toString();

            return isValidDay(toDateTime) || isValidDateTime(toDateTime);
        }
        return false;
    }

    private boolean isValidDateTime(String stringDateTime) {
        // must be in the format dd/mm or dd/mm H:m
        DateTimeFormatter month_day_formatter = DateTimeFormatter.ofPattern("dd/MM");
        DateTimeFormatter month_day_time_formatter = DateTimeFormatter.ofPattern("dd/MM H:m");

        try {
            MonthDay.parse(stringDateTime, month_day_formatter);
            return true;
        } catch (DateTimeParseException e1) {
            try {
                MonthDay.parse(stringDateTime, month_day_time_formatter);
                return true;
            }
            catch (DateTimeParseException e2) {
                return false;
            }
        }
    }

    private boolean isValidDay(String stringDay) {
        try {
            DayOfWeek.valueOf(stringDay.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
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

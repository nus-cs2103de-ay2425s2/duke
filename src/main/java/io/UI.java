package io;

import action.ActionHandler.Action;
import io.ValidationToken.InputError;
import user.User;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
     *
     * @return User input String
     */
    public String getValidUserInput(User user) throws IOException {
        boolean isUserInputValid = false;

        String userInput;

        do {
            userInput = INPUT.getUserInput();
            ValidationToken validationToken = this.isInputValid(userInput, user);
            if (validationToken.isValid()) {
                isUserInputValid = true;
            }
            else {
                List<String> outputMessage = new ArrayList<>();
                outputMessage.add(validationToken.getErrorMessage());
                displayMessageWithDivider(outputMessage);
            }
        }
        while (!isUserInputValid);

        return userInput;
    }

    /**
     * Check if user input is valid
     *
     * @param userInput Input string provided by the user with no trailing or leading whitespaces
     * @return ValidationToken with isValid status and error message if it is not valid
     */
    // TODO: Create a InputValidator class to handle the input validation
    private ValidationToken isInputValid(String userInput, User user) {
        List<String> userInputTokens = Arrays.asList(userInput.split(" "));

        if (userInputTokens.getFirst().equalsIgnoreCase(Action.LIST.toString())
                || userInputTokens.getFirst().equalsIgnoreCase(Action.BYE.toString())) {
            if (userInputTokens.size() == 1) {
                return new ValidationToken(true);
            }

            if (userInputTokens.getFirst().equalsIgnoreCase(Action.LIST.toString())) {
                return new ValidationToken(false, InputError.LIST_TOO_MANY_ARGUMENTS);
            }

            return new ValidationToken(false, InputError.BYE_TOO_MANY_ARGUMENTS);
        }
        else if (userInputTokens.getFirst().equalsIgnoreCase(Action.MARK.toString())
                || (userInputTokens.getFirst().equalsIgnoreCase(Action.UNMARK.toString()))) {
            if (userInputTokens.size() == 1) {
                if (userInputTokens.getFirst().equalsIgnoreCase(Action.MARK.toString())) {
                    return new ValidationToken(false, InputError.MARK_TOO_LITTLE_ARGUMENTS);
                }

                return new ValidationToken(false, InputError.UNMARK_TOO_LITTLE_ARGUMENTS);
            }
            else if (userInputTokens.size() > 2) {
                if (userInputTokens.getFirst().equalsIgnoreCase(Action.MARK.toString())) {
                    return new ValidationToken(false, InputError.MARK_TOO_MANY_ARGUMENTS);
                }

                return new ValidationToken(false, InputError.UNMARK_TOO_MANY_ARGUMENTS);
            }

            try {
                Integer.parseInt(userInputTokens.get(1));
            } catch (NumberFormatException e) {
                if (userInputTokens.getFirst().equalsIgnoreCase(Action.MARK.toString())) {
                    return new ValidationToken(false, InputError.MARK_INCORRECT_ARGUMENT_TYPE);
                }

                return new ValidationToken(false, InputError.UNMARK_INCORRECT_ARGUMENT_TYPE);
            }

            if (Integer.parseInt(userInputTokens.get(1)) < 1
                    || Integer.parseInt(userInputTokens.get(1)) > user.getNumberOfTasks()) {
                return new ValidationToken(false, InputError.INVALID_TASK_NUMBER);
            }

            return new ValidationToken(true);
        }
        else if (userInputTokens.getFirst().equalsIgnoreCase(Action.TODO.toString())) {
            if (userInputTokens.size() == 1) {
                return new ValidationToken(false, InputError.TODO_TOO_LITTLE_ARGUMENTS);
            }

            return new ValidationToken(true);
        }
        else if (userInputTokens.getFirst().equalsIgnoreCase(Action.DEADLINE.toString())) {
            if (userInputTokens.size() <= 2) {
                return new ValidationToken(false, InputError.DEADLINE_TOO_LITTLE_ARGUMENTS);
            }

            if (!userInputTokens.contains("/by")) {
                return new ValidationToken(false, InputError.DEADLINE_MISSING_BY);
            }

            int deadLineIndex = userInputTokens.indexOf("/by") + 1;

            if (deadLineIndex == userInputTokens.size()) {
                return new ValidationToken(false, InputError.DEADLINE_MISSING_DEADLINE);
            }

            String stringDateTime = String.join(" ",
                    userInputTokens.subList(deadLineIndex, userInputTokens.size()));

            if (!(isValidDay(stringDateTime) || isValidDateTime(stringDateTime))) {
                return new ValidationToken(false, InputError.DEADLINE_INVALID_DATETIME);
            }

            return new ValidationToken(true);
        }
        else if (userInputTokens.getFirst().equalsIgnoreCase(Action.EVENT.toString())) {
            if (!(userInputTokens.contains("/from") && userInputTokens.contains("/to"))) {
                return new ValidationToken(false, InputError.EVENT_MISSING_FROM_TO_ARGUMENTS);
            }

            int fromIndex = userInputTokens.indexOf("/from");
            int toIndex = userInputTokens.indexOf("/to");

            String fromDateTime = String.join(" ",
                    userInputTokens.subList(fromIndex + 1, toIndex));

            if (!(isValidDay(fromDateTime) || isValidDateTime(fromDateTime))) {
                return new ValidationToken(false, InputError.EVENT_INVALID_DATETIME);
            }

            String toDateTime = String.join(" ", userInputTokens.subList(toIndex + 1, userInputTokens.size()));

            if (!(isValidDay(toDateTime) || isValidDateTime(toDateTime))) {
                return new ValidationToken(false, InputError.EVENT_INVALID_DATETIME);
            }

            return new ValidationToken(true);
        }
        else if (userInputTokens.getFirst().equalsIgnoreCase(Action.DELETE.toString())) {
            if (userInputTokens.size() > 2) {
                // too many arguments
                return new ValidationToken(false, InputError.DELETE_TOO_MANY_ARGUMENTS);
            }

            try {
                Integer.parseInt(userInputTokens.get(1));
            } catch (NumberFormatException e) {
                // second argument not a number
                return new ValidationToken(false, InputError.DELETE_INCORRECT_ARGUMENT_TYPE);
            }

            return new ValidationToken(true);
        }

        return new ValidationToken(false, InputError.INVALID_COMMAND);
    }

    /**
     * Method to check if the provided string follows the dd/MM or dd/MM H:m format
     * @param stringDateTime String to be checked
     * @return boolean that indicates if it is a valid time format
     */
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
            } catch (DateTimeParseException e2) {
                return false;
            }
        }
    }

    /**
     * Method to check if the provided string is a valid day
     * @param stringDay String to be checked
     * @return boolean that indicates if it is a valid day
     */
    private boolean isValidDay(String stringDay) {
        try {
            DayOfWeek.valueOf(stringDay.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }


    /**
     * Displays custom message to the console with dividers
     *
     * @param messages list of messages to output to console
     * @throws IOException When IO fails
     */
    public void displayMessageWithDivider(List<String> messages) throws IOException {
        messages.addFirst(this.DIVIDER);
        messages.add(this.DIVIDER);
        OUTPUT.printOutput(messages, "\n", "\n");
    }

    /**
     * Display custom message to the console without dividers
     *
     * @param messages list of messages to output to console
     * @throws IOException When IO fails
     */
    public void displayMessage(List<String> messages) throws IOException {
        OUTPUT.printOutput(messages, "\n", "\n");
    }

    /**
     * Display welcome message to the console
     *
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
     *
     * @throws IOException When IO fails
     */
    public void displayExitMessage() throws IOException {
        List<String> exitMessages = new ArrayList<>();
        exitMessages.add(this.DIVIDER);
        exitMessages.add("Bye. Hope to see you again soon!");
        exitMessages.add(this.DIVIDER);
        OUTPUT.printOutput(exitMessages, "\n", "\n");
    }
}

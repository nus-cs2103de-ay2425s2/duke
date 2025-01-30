package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Input class to handle the reading of all user inputs
 */
class Input {
    private final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Constructor
     */
    public Input() {
    }

    /**
     * Method to get user input
     * @return Input string that user provides
     * @throws IOException when IO fails
     */
    public String getUserInput() throws IOException {
        return this.cleanUserInput(this.BUFFERED_READER.readLine());
    }

    /**
     * Remove leading and trailing whitespaces
     * @param userInput userInput String
     * @return String with no trailing or leading whitespaces
     */
    private String cleanUserInput(String userInput) {
        return userInput.strip();
    }
}

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
    private String getUserInput() throws IOException {
        return this.BUFFERED_READER.readLine();
    }

    /**
     * Checks if input is valid
     * @param userInput Input string provided by the user
     * @return boolean that indicates if the input is valid
     */
    private boolean isInputValid(String userInput) {
        return true;
    }

    /**
     * Gets valid user input
     * @return Input String if the user input is valid
     */
    public String getValidUserInput() {
        boolean isUserInputValid = false;

        String userInput;

        do {
            try {
                userInput = this.getUserInput();
                if (this.isInputValid(userInput)) {
                    isUserInputValid = true;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        while (!isUserInputValid);

        return userInput;

    }
}

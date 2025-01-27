package bob;

import io.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bob {
    private final String CHATBOT_NAME = "Bob";

    public String getCHATBOT_NAME() {
        return CHATBOT_NAME;
    }

    public static void main(String[] args) throws IOException {
        Bob bob = new Bob();
        UI ui = new UI(bob.getCHATBOT_NAME());

        ui.displayWelcomeMessage();
        while (true) {
            String userInput = ui.getUserInput();

            // check if userInput bye
            if (userInput.equals("bye")) {
                break;
            }

            List<String> outputMessages = new ArrayList<>();
            outputMessages.add(userInput);

            ui.displayMessage(outputMessages);
        }

        ui.displayExitMessage();
    }


}

package bob;

import action.ActionHandler;
import io.UI;
import user.User;

import java.io.IOException;
import java.util.List;

/**
 * Chatbot class
 */
public class Bob {
    private final String CHATBOT_NAME = "Bob";

    public static void main(String[] args) throws IOException {
        // initial setup
        Bob bob = new Bob();
        UI ui = new UI(bob.getCHATBOT_NAME());
        User user = new User();
        ActionHandler actionHandler = new ActionHandler();
        List<String> outputMessages;

        ui.displayWelcomeMessage();

        while (true) {
            String userInput = ui.getValidUserInput(user);

            outputMessages = actionHandler.processEvent(userInput, user);

            if (outputMessages.isEmpty()) {
                ui.displayExitMessage();
                break;
            }

            ui.displayMessageWithDivider(outputMessages);
        }

    }

    public String getCHATBOT_NAME() {
        return CHATBOT_NAME;
    }


}

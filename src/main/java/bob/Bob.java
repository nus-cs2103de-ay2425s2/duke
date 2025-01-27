package bob;

import io.UI;

import java.io.IOException;

public class Bob {
    private final String CHATBOT_NAME = "Bob";

    public String getCHATBOT_NAME() {
        return CHATBOT_NAME;
    }

    public static void main(String[] args) throws IOException {
        Bob bob = new Bob();
        UI ui = new UI(bob.getCHATBOT_NAME());
        ui.displayWelcomeMessage();
        ui.displayExitMessage();
    }


}

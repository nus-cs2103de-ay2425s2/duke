// src/main/java/commands/CheerCommand.java
package commands;

import tasks.TaskList;
import ui.Ui;
import utils.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * The CheerCommand class implements the Command interface and is responsible for
 * displaying a random motivational quote from a file.
 */
public class CheerCommand implements Command {
    private static final String FILE_PATH = "./data/cheer.txt";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_ITALIC = "\u001B[3m";
    private static final String[] ANSI_COLORS = {
            "\u001B[33m", // Yellow
            "\u001B[32m", // Green
            "\u001B[31m", // Red
            "\u001B[34m", // Blue
            "\u001B[35m", // Purple
            "\u001B[36m", // Cyan (used as Orange)
            "\u001B[95m"  // Bright Magenta (used as Pink)
    };

    /**
     * Executes the CheerCommand, which reads a random quote from the file and displays it
     * with random color and italic formatting.
     *
     * @param taskList The task list (not used in this command).
     * @param ui The UI instance used to display messages.
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        try {
            List<String> quotes = Files.readAllLines(Paths.get(FILE_PATH));
            if (quotes.isEmpty()) {
                ui.showMessage("No quotes available.");
            } else {
                Random random = new Random();
                String randomQuote = quotes.get(random.nextInt(quotes.size()));
                String randomColor = ANSI_COLORS[random.nextInt(ANSI_COLORS.length)];
                String formattedQuote = randomColor + ANSI_ITALIC + randomQuote + ANSI_RESET;
                ui.showMessage(formattedQuote);
            }
        } catch (IOException e) {
            ui.showMessage("Error reading quotes: " + e.getMessage());
        }
    }
}
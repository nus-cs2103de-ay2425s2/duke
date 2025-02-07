/*
Handles the main logic of running the program and scanning user input
 */

package davethebrave;

import davethebrave.ui.Ui;
import davethebrave.task.TaskManager;
import davethebrave.storage.Storage;
import davethebrave.parser.Parser;

import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class DaveTheBrave {
    private Storage storage;
    private TaskManager taskManager;
    private Ui ui;

    public DaveTheBrave(String filePath) {
        ui = new Ui();

        storage = new Storage(filePath);
        taskManager = new TaskManager(storage.loadTasksFromFile(), storage, ui);
    }

    public void run() {
        List<String> greetings = Arrays.asList("hello", "hi", "hey", "yo");
        List<String> goodbyes = Arrays.asList("bye", "goodbye");

        /*
        Scanner object for user input
         */
        Scanner scanner = new Scanner(System.in);

        ui.showWelcome();

        while (true) {
            /*
            Read user input
             */
            String command = scanner.nextLine();

            /*
            Exit when user types the command 'bye'
             */
            if (goodbyes.contains(command.toLowerCase())) {
                ui.showGoodbye();
                break;
            }

            /*
            Additional greetings
             */
            if (greetings.contains(command.toLowerCase())) {
                ui.respondHello();
                continue;
            }

            // Cheer
            if (command.equalsIgnoreCase("cheer")) {
                ui.showCheer();
                continue;
            }

            Parser.parseCommand(command, taskManager);
        }
        /*
        Close scanner
         */
        scanner.close();
    }
    public static void main(String[] args) {
        new DaveTheBrave("data/davethebrave.txt").run();
    }
}
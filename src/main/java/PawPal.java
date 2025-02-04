import java.util.Scanner;

import utils.TaskManager;
import utils.Printer;

public class PawPal {
    public static void main(String[] args) {
        new PawPal().run();
    }

    private final Parser parser;
    private final Printer printer;

    public PawPal() {
        TaskManager taskManager = new TaskManager();
        printer = new Printer();
        this.parser = new Parser(taskManager);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Standard greeting message
        String name = "PawPal";
        printer.printGreeting(name);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("bye")){
                printer.printBye();
                break;
            }
            parser.parse(input);
        }
    }
}
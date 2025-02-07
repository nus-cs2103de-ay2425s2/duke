/**
 * Rucia is a personal assistant chatbot that helps users with basic commands.
 * This class handles greeting the user and automatically exits after greeting.
 */
public class Rucia {

    /**
     * The main method that runs the Rucia chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Display ASCII art
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .----------------. ");
        System.out.println("| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |");
        System.out.println("| |  _______     | || | _____  _____ | || |     ______   | || |     _____    | || |      __      | |");
        System.out.println("| | |_   __ \\    | || ||_   _||_   _|| || |   .' ___  |  | || |    |_   _|   | || |     /  \\     | |");
        System.out.println("| |   | |__) |   | || |  | |    | |  | || |  / .'   \\_|  | || |      | |     | || |    / /\\ \\    | |");
        System.out.println("| |   |  __ /    | || |  | '    ' |  | || |  | |         | || |      | |     | || |   / ____ \\   | |");
        System.out.println("| |  _| |  \\ \\_  | || |   \\ `--' /   | || |  \\ `.___.'\\  | || |     _| |_    | || | _/ /    \\ \\_ | |");
        System.out.println("| | |____| |___| | || |    `.__.'    | || |   `._____.'  | || |    |_____|   | || ||____|  |____|| |");
        System.out.println("| |              | || |              | || |              | || |              | || |              | |");
        System.out.println("| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |");
        System.out.println(" '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");

        // Greeting message
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Rucia");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        // Exit message
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
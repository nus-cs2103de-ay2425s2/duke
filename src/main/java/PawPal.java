import java.util.Scanner;
import java.util.ArrayList;

public class PawPal {
    public static void main(String[] args) {
        String name = "PawPal";
        System.out.println("-----------------------------------------");
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you? \n");
        System.out.println("-----------------------------------------");

        while (true) {
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();

            if (input.equalsIgnoreCase("bye")){
                System.out.println("Bye. Hope to see you again soon! Meow");
                break;
            }
            System.out.println("PawPal: " + input + " meow");
        }
    }
}

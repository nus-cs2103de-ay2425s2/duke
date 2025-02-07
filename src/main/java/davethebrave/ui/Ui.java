/*
Handles the formatting of basic display messages to be called by TaskManager and Main classes
 */

package davethebrave.ui;

import davethebrave.task.Task;

import java.util.List;

public class Ui {
    public void showWelcome() {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Boooo! I'm davethebrave.DaveTheBrave");
        System.out.println("      What can I do for you?");
        System.out.println("    ____________________________________________________________");
    }

    public void respondHello() {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Hey, what's up?");
        System.out.println("    ____________________________________________________________");
    }

    public void showGoodbye() {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Bye! Hope I didn't scare you away!");
        System.out.println("    ____________________________________________________________");
    }

    public void showCheer() {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Keep going - even the best programmers");
        System.out.println("      started out writing 'Hello World'!");
        System.out.println("    ____________________________________________________________");
    }

    public void showTaskAdded(List<Task> tasks) {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Got it. I've added this task:");
        System.out.println("         " + tasks.getLast());
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }

    public void showTaskDeleted(Task removedTask, List<Task> tasks) {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Noted. I've removed this task:");
        System.out.println("         " + removedTask);
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}

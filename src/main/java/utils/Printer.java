package utils;

import tasks.*;

import java.util.List;

public class Printer {
    // response for when a task is added
    public void printTaskAdded(String description, int taskCount) {
        System.out.println("Purr-sonally, I’d rather nap, but your " + description + " task is on the list now!");
        System.out.println("Now you have " + taskCount + " tasks.");
    }

    public void printGreeting(String name) {
        System.out.println("-----------------------------------------");
        System.out.println("Hello! I'm " + name + "\n" + "What can I do for you? \n");
        System.out.println("-----------------------------------------");
    }

    public void printBye(){
        System.out.println("Bye. Hope to see you again soon! Meow");
    }
    // method to print the task lists
    public void printTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks in your list you silly cat!");
        } else {
            System.out.println("Here are the tasks in your list you silly cat!");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
    }

    // response for when a task is marked
    public void printTaskMarked(Task task) {
        System.out.println("A paw-sitive development! Wanna celebrate with some head pats?");
        System.out.println(task);
    }

    // response for when a task is unmarked
    public void printTaskUnmarked(Task task) {
        System.out.println("We don't judge around here.");
        System.out.println(task);
    }

    // Print a message when a task is deleted
    public void printTaskDeleted(Task task, int taskCount) {
        System.out.println("Task removed: " + task);
        System.out.println("Now you have " + taskCount + " tasks left.");
    }

    // error message for invalid task number
    public void printInvalidTaskNumber() {
        System.out.println("Invalid task number! Are you playing with yarn again?");
    }

    // error message for invalid command
    public void printInvalidCommand() {
        System.out.println("Sorry, I don't understand that command :(");
    }

    // error message for missing toDo description
    public void printMissingToDoDetails() {
        System.out.println("You need to provide a task description after 'todo'!");
    }

    // error message for missing deadline details
    public void printMissingDeadlineDetails() {
        System.out.println("Invalid deadline! Use: deadline <description> /by <deadline>");
    }

    // error message for missing event details
    public void printMissingEventDetails() {
        System.out.println("Invalid event! Use: event <description> /from <start> /to <end>");
    }
}

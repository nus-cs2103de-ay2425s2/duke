// src/main/java/storage/Storage.java
package utils;

import tasks.Task;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Storage {
    private static final String DATA_FILE = "./data/tasks.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * Saves the current list of tasks to a file.
     *
     * @param tasks The list of tasks to be saved.
     */
    public static void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs(); // Ensure the data folder exists
            FileWriter writer = new FileWriter(file);

            for (Task task : tasks) {
                writer.write(task.toString() + System.lineSeparator());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Rucia: Error saving tasks to file.");
        }
    }

    /**
     * Loads tasks from the data file.
     *
     * @return The list of tasks loaded from the file.
     */
    public static ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    tasks.add(parseTask(line));
                }
                fileScanner.close();
            }
        } catch (IOException e) {
            System.out.println("Rucia: Error loading tasks from file.");
        }

        return tasks;
    }

    /**
     * Parses a task from a string representation.
     *
     * @param line The string representation of the task.
     * @return The parsed Task object.
     */
    private static Task parseTask(String line) {
        String type = line.substring(1, 2);
        boolean isDone = line.charAt(4) == 'X';
        String description = line.substring(7);

        Task task;
        if (type.equals("T")) {
            task = new ToDo(description);
        } else if (type.equals("D")) {
            String[] parts = description.split(" \\(by: ");
            task = new Deadline(parts[0], parts[1].replace(")", ""));
        } else if (type.equals("E")) {
            String[] parts = description.split(" \\(from: ");
            String[] timeParts = parts[1].replace(")", "").split(" to: ");
            task = new Event(parts[0], timeParts[0], timeParts[1]);
        } else {
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
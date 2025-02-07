package PawPal.utils;

import PawPal.tasks.Task;
import PawPal.tasks.Deadline;
import PawPal.tasks.Event;
import PawPal.tasks.ToDo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving PawPal.core.PawPal.tasks from and to a file.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructs a new Storage instance.
     *
     * @param filePath The path to the file where PawPal.core.PawPal.tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads PawPal.core.PawPal.tasks from the file and returns them as a list of PawPal.core.PawPal.tasks.
     *
     * @return A list of PawPal.core.PawPal.tasks loaded from the file.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;  // Return an empty list if the file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        }

        return tasks;
    }

    /**
     * Saves the current PawPal.core.PawPal.tasks to the file.
     *
     * @param tasks The list of PawPal.core.PawPal.tasks to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveTasks(List<Task> tasks) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();  // Ensure the directory exists

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(task.toString() + "\n");
            }
        }
    }

    /**
     * Parses a line from the task file and creates the corresponding task object.
     *
     * @param line The line representing a task in the saved file.
     * @return The parsed task object, or null if the line is malformed.
     */
    private Task parseTask(String line) {
        try {
            if (line.isBlank()) {
                return null;
            }

            String taskType = line.substring(1, 2);
            boolean isDone = line.charAt(4) == 'X';
            String details = line.substring(7).trim();

            Task task;
            switch (taskType) {
            case "T":
                task = new ToDo(details);
                break;
            case "D":
                String[] deadlineParts = details.split("\\(by: ", 2);
                task = new Deadline(deadlineParts[0].trim(), deadlineParts[1].replace(")", "").trim());
                break;
            case "E":
                String[] eventParts = details.split("from: | to: ");
                task = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                break;
            default:
                return null;
            }

            if (isDone) {
                task.markAsDone();
            }
            return task;

        } catch (Exception e) {
            System.out.println("Skipping malformed task line: " + line);
            return null;
        }
    }
}

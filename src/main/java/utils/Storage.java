// src/main/java/utils/Storage.java
package utils;

import tasks.Task;
import tasks.TaskList;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private String filePath;
    private static final String DEFAULT_FILE_PATH = "./data/tasks.txt";

    public Storage() {
        this.filePath = DEFAULT_FILE_PATH;
    }

    public Storage(String filePath) {
        this.filePath = (filePath == null || filePath.isEmpty()) ? DEFAULT_FILE_PATH : filePath;
    }

    public ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    tasks.add(Task.parse(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
        }
        return tasks;
    }

    public void saveTasksToFile(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}
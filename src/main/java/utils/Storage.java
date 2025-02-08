// src/main/java/utils/Storage.java
        package utils;

import tasks.Task;
import tasks.TaskList;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private static final String DATA_FILE = "./data/tasks.txt";

    public static ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
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

    public static void saveTasksToFile(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks to file: " + e.getMessage());
        }
    }
}
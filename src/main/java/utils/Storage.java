// src/main/java/utils/Storage.java
package utils;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(System.getProperty("user.dir"), filePath).toString();
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        Path path = Paths.get(filePath);
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    public void saveTasksToFile(List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.toFileString());
        }
        Files.write(Paths.get(filePath), lines);
    }

    public ArrayList<Task> loadTasksFromFile() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            switch (type) {
                case "T":
                    tasks.add(new ToDo(description));
                    break;
                case "D":
                    long byTimestamp = Long.parseLong(parts[3]);
                    tasks.add(new Deadline(description, byTimestamp));
                    break;
                case "E":
                    long fromTimestamp = Long.parseLong(parts[3]);
                    long toTimestamp = Long.parseLong(parts[4]);
                    tasks.add(new Event(description, fromTimestamp, toTimestamp));
                    break;
            }
        }
        return tasks;
    }
}
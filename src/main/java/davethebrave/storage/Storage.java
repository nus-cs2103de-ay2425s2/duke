package davethebrave.storage;

import davethebrave.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
        checkFileExists();
    }

    public void saveTasksToFile(List<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.println(task.toFileFormat());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public List<Task> loadTasksFromFile() {
        List<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))){
            while (scanner.hasNextLine()) {
                Task task = Task.fromFileFormat(scanner.nextLine());
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    /*
    Check if folder and file exists. Create folder and/or file if not.
     */
    public void checkFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }
}

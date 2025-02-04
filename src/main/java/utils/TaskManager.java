package utils;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;

public class TaskManager {
    private final List<Task> tasks;
    private final Printer printer;
    private final String filePath = "./data/tasks.txt";

    public TaskManager() {
        tasks = new ArrayList<>();
        printer = new Printer();
        loadTasks();
    }

    public void saveTasks() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();  // Create the data directory if it doesn't exist
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Task task : tasks) {
                writer.write(task.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void loadTasks() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseAndAddTask(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    private void parseAndAddTask(String line) {
        String[] parts = line.split("\\|");
        String taskType = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        switch (taskType) {
        case "T":
            ToDo todo = new ToDo(description);
            if (isDone) todo.markAsDone();
            tasks.add(todo);
            break;
        case "D":
            String deadline = parts[3].trim();
            Deadline deadlineTask = new Deadline(description, deadline);
            if (isDone) deadlineTask.markAsDone();
            tasks.add(deadlineTask);
            break;
        case "E":
            String start = parts[3].trim();
            String end = parts[4].trim();
            Event eventTask = new Event(description, start, end);
            if (isDone) eventTask.markAsDone();
            tasks.add(eventTask);
            break;
        }
    }

    public void addToDo(String description) {
        Task newTask = new ToDo(description);
        tasks.add(newTask);
        saveTasks();
        printer.printTaskAdded(description, tasks.size());
    }

    public void addDeadline(String description, String deadline) {
        Task newTask = new Deadline(description, deadline);
        tasks.add(newTask);
        saveTasks();
        printer.printTaskAdded(description, tasks.size());
    }

    public void addEvent(String description, String from, String to) {
        Task newTask = new Event(description, from, to);
        tasks.add(newTask);
        saveTasks();
        printer.printTaskAdded(description, tasks.size());
    }

    public void listTasks() {
        printer.printTaskList(tasks);
    }

    public void deleteTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.remove(taskNumber - 1);
            printer.printTaskDeleted(task, tasks.size());
        } else {
            printer.printInvalidTaskNumber();
        }
    }

    public void markTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.markAsDone();
            saveTasks();
            printer.printTaskMarked(task);
        } else {
            printer.printInvalidTaskNumber();
        }
    }

    public void unmarkTask(int taskNumber) {
        if (taskNumber > 0 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.markAsNotDone();
            saveTasks();
            printer.printTaskUnmarked(task);
        } else {
            printer.printInvalidTaskNumber();
        }
    }
}

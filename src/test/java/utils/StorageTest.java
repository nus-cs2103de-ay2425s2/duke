// src/test/java/utils/StorageTest.java
package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskList;
import tasks.ToDo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {
    private static final String TEST_FILE_PATH = "test_tasks.txt";
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        storage = new Storage(TEST_FILE_PATH);
    }

    @Test
    public void testWriteNewTasks() throws IOException {
        taskList.addTask(new ToDo("Test task 1"));
        taskList.addTask(new ToDo("Test task 2"));
        storage.saveTasksToFile(taskList.getTasks());

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE_PATH));
        assertEquals(2, lines.size());
        assertEquals("T | 0 | Test task 1", lines.get(0));
        assertEquals("T | 0 | Test task 2", lines.get(1));
    }

    @Test
    public void testReadFromStorage() throws IOException {
        Files.write(Paths.get(TEST_FILE_PATH), List.of("T | 0 | Test task 1", "T | 0 | Test task 2"));
        List<Task> tasks = storage.loadTasksFromFile();

        assertEquals(2, tasks.size());
        assertEquals("Test task 1", tasks.get(0).getDescription());
        assertEquals("Test task 2", tasks.get(1).getDescription());
    }

    @Test
    public void testDeleteFromList() throws IOException {
        taskList.addTask(new ToDo("Test task 1"));
        taskList.addTask(new ToDo("Test task 2"));
        storage.saveTasksToFile(taskList.getTasks());

        taskList.deleteTask(0);
        storage.saveTasksToFile(taskList.getTasks());

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE_PATH));
        assertEquals(1, lines.size());
        assertEquals("T | 0 | Test task 2", lines.get(0));
    }
}
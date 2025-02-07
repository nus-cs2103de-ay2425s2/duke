package davethebrave;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import davethebrave.task.TaskManager;
import davethebrave.task.Task;
import davethebrave.storage.Storage;
import davethebrave.ui.Ui;

public class TodoTest {
    private TaskManager taskManager;
    private List<Task> taskList;
    private Storage storage;
    private Ui ui;

    @BeforeEach
    void setUp() {
        taskList = new ArrayList<>();
        storage = new Storage("data/test.txt");
        ui = new Ui();
        taskManager = new TaskManager(taskList, storage, ui);
    }

    // Test UI task added display
    @Test
    public void displayTaskTest() {
        Task myFirstTask = new Task("T", "Complete Assignment", null);
        assertEquals("[T][ ] Complete Assignment", myFirstTask.toString());
    }

    // Test adding task
    @Test
    public void addTaskTest() {
        taskManager.addTask("T", "Complete Assignment", null);
        assertEquals(1, taskList.size());
    }

    // Test deleting task
    @Test
    public void deleteTaskTest() {
        taskManager.deleteTask(1);
        assertTrue(taskList.isEmpty());
    }
}

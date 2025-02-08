// src/test/java/commands/TestCommands.java
package commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.TaskList;
import ui.Ui;
import utils.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCommands {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        ui = new Ui();
        storage = new Storage();
    }

    @Test
    public void testAddCommand() {
        AddCommand addCommand = new AddCommand("Test task", storage);
        addCommand.execute(taskList, ui);

        assertEquals(1, taskList.getSize());
        assertEquals("Test task", taskList.getTasks().get(0).getDescription());
    }

    @Test
    public void testDeadlineCommand() {
        DeadlineCommand deadlineCommand = new DeadlineCommand("Submit report", "31/12/2025 1200", storage);
        deadlineCommand.execute(taskList, ui);

        assertEquals(1, taskList.getSize());
        assertEquals("Submit report", taskList.getTasks().get(0).getDescription());
    }

    @Test
    public void testEventCommand() {
        EventCommand eventCommand = new EventCommand("Team meeting", "12/12/2025 1200", "14/12/2025 1200", storage);
        eventCommand.execute(taskList, ui);

        assertEquals(1, taskList.getSize());
        assertEquals("Team meeting", taskList.getTasks().get(0).getDescription());
    }
}
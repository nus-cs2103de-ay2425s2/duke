// src/test/java/rucia/RuciaTest.java
package rucia;

import commands.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import tasks.TaskList;
import tasks.ToDo;
import tasks.Deadline;
import tasks.Event;
import ui.Ui;
import utils.CommandIdentifier;
import utils.CommandParser;
import utils.Storage;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RuciaTest {
    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Rucia rucia;

    @BeforeEach
    public void setUp() throws IOException {
        ui = Mockito.mock(Ui.class);
        storage = Mockito.mock(Storage.class);
        taskList = new TaskList();
        rucia = new Rucia();
    }

    @Test
    public void testAddCommand() throws IOException {
        String input = "add Test ToDo task";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(2)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();
        assertEquals("Added ToDo task - Test ToDo task", capturedMessages.get(0));
        assertEquals("You now have 1 task(s) in your list.", capturedMessages.get(1));
    }

    @Test
    public void testDeadlineCommand() throws IOException {
        String input = "deadline Submit report /by 31/12/2023 1000";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(2)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();

        assertEquals("Added Deadline task - Submit report (by: Dec 31 2023, 10:00am)", capturedMessages.get(0));
        assertEquals("You now have 1 task(s) in your list.", capturedMessages.get(1));
    }

    @Test
    public void testEventCommand() throws IOException {
        String input = "event Team meeting /from 25/12/2023 0800 /to 25/12/2023 1000";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(2)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();

        assertEquals("Added Event task - Team meeting (from: Dec 25 2023, 8:00am to: Dec 25 2023, 10:00am)", capturedMessages.get(0));
        assertEquals("You now have 1 task(s) in your list.", capturedMessages.get(1));
    }

    @Test
    public void testListCommand() throws IOException {
        // Add some tasks to the task list
        taskList.addTask(new ToDo("Buy Food"));
        taskList.addTask(new ToDo("Read Book"));

        String input = "list";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(3)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();

        assertEquals("Here are the tasks in your list:", capturedMessages.get(0));
        assertEquals("1. [T][ ] Buy Food", capturedMessages.get(1));
        assertEquals("2. [T][ ] Read Book", capturedMessages.get(2));
    }

    @Test
    public void testMarkCommand() throws IOException {
        // Add a task to the task list
        taskList.addTask(new ToDo("Buy Food"));

        // Mark the task as complete
        String input = "mark 1";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(1)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();
        assertEquals("Marked task as done - [T][X] Buy Food", capturedMessages.get(0));
    }

    @Test
    public void testUnmarkCommand() throws IOException {
        // Add a task to the task list and mark it as complete
        taskList.addTask(new ToDo("Buy Food"));
        taskList.getTask(0).markAsDone();

        // Unmark the task
        String input = "unmark 1";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(1)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();
        assertEquals("Unmarked task - [T][ ] Buy Food", capturedMessages.get(0));
    }

    @Test
    public void testDeleteCommand() throws IOException {
        // Add a task to the task list
        taskList.addTask(new ToDo("Buy Food"));

        // Delete the task
        String input = "delete 1";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(2)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();
        assertEquals("Deleted task - Buy Food", capturedMessages.get(0));
        assertEquals("You now have 0 task(s) in your list.", capturedMessages.get(1));
    }

    @Test
    public void testListDayCommand() throws IOException {
        // Add some tasks to the task list
        taskList.addTask(new Deadline("Submit report", "25/12/2023 2359"));
        taskList.addTask(new Event("Christmas Party", "25/12/2023 1800", "25/12/2023 2100"));

        String input = "list_day 25/12/2023";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(3)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();

        assertEquals("Tasks for 2023-12-25:", capturedMessages.get(0));
        assertEquals("[D][ ] Submit report (by: Dec 25 2023, 11:59pm)", capturedMessages.get(1));
        assertEquals("[E][ ] Christmas Party (from: Dec 25 2023, 6:00pm to: Dec 25 2023, 9:00pm)", capturedMessages.get(2));
    }

    @Test
    public void testFindCommand() throws IOException {
        // Add some tasks to the task list
        taskList.addTask(new ToDo("Get Breakfast"));
        taskList.addTask(new ToDo("Buy Bread"));

        String input = "find Bread";
        String commandType = CommandIdentifier.identify(input);
        Command command = CommandParser.parse(input, commandType, storage);

        command.execute(taskList, ui);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ui, times(2)).showMessage(captor.capture());
        List<String> capturedMessages = captor.getAllValues();

        assertEquals("Tasks found with the keyword: Bread", capturedMessages.get(0));
        assertEquals("[T][ ] Buy Bread", capturedMessages.get(1));
    }
}
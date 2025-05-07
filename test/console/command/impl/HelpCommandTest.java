package console.command.impl;

import console.command.Command;
import console.command.impl.HelpCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.UserStorage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class HelpCommandTest {

    private UserStorage userStorage;
    private Map<String, Command> commands;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        userStorage = new UserStorage();
        commands = new HashMap<>();

        commands.put("test1", new TestCommand("Test Command 1"));
        commands.put("test2", new TestCommand("Test Command 2"));

        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testHelpCommand() {
        HelpCommand command = new HelpCommand(new Scanner(""), userStorage, commands);
        command.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Available commands:"));
        assertTrue(output.contains("test1"));
        assertTrue(output.contains("Test Command 1"));
        assertTrue(output.contains("test2"));
        assertTrue(output.contains("Test Command 2"));
    }

    @Test
    void testEmptyCommandsList() {
        HelpCommand command = new HelpCommand(new Scanner(""), userStorage, new HashMap<>());
        command.execute();

        assertTrue(outputStream.toString().contains("Available commands:"));
    }

    @Test
    void testGetDescription() {
        HelpCommand command = new HelpCommand(new Scanner(""), userStorage, commands);
        assertNotNull(command.getDescription());
        assertFalse(command.getDescription().isEmpty());
    }

    private static class TestCommand implements Command {
        private final String description;

        public TestCommand(String description) {
            this.description = description;
        }

        @Override
        public void execute() {
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}

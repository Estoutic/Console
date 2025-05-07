package console.command;

import console.command.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.UserStorage;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class CommandFactoryTest {

    private CommandFactory commandFactory;

    @BeforeEach
    void setUp() {
        Scanner scanner = new Scanner("");
        UserStorage userStorage = new UserStorage();
        commandFactory = new CommandFactory(scanner, userStorage);
    }

    @Test
    void testGetExistingCommand() {
        Command command = commandFactory.getCommand("add");
        assertNotNull(command);
        assertTrue(command instanceof AddUserCommand);

        command = commandFactory.getCommand("list");
        assertNotNull(command);
        assertTrue(command instanceof ListUsersCommand);

        command = commandFactory.getCommand("send");
        assertNotNull(command);
        assertTrue(command instanceof SendMessageCommand);

        command = commandFactory.getCommand("inbox");
        assertNotNull(command);
        assertTrue(command instanceof InboxCommand);

        command = commandFactory.getCommand("spam");
        assertNotNull(command);
        assertTrue(command instanceof SpamCommand);

        command = commandFactory.getCommand("outbox");
        assertNotNull(command);
        assertTrue(command instanceof OutboxCommand);

        command = commandFactory.getCommand("setfilter");
        assertNotNull(command);
        assertTrue(command instanceof SetFilterCommand);

        command = commandFactory.getCommand("help");
        assertNotNull(command);
        assertTrue(command instanceof HelpCommand);

        command = commandFactory.getCommand("exit");
        assertNotNull(command);
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    void testGetUnknownCommand() {
        Command command = commandFactory.getCommand("nonexistent");
        assertNotNull(command);
        assertTrue(command instanceof UnknownCommand);
    }

    @Test
    void testGetAllCommands() {
        var allCommands = commandFactory.getAllCommands();

        assertEquals(9, allCommands.size());
        assertTrue(allCommands.containsKey("add"));
        assertTrue(allCommands.containsKey("list"));
        assertTrue(allCommands.containsKey("send"));
        assertTrue(allCommands.containsKey("inbox"));
        assertTrue(allCommands.containsKey("spam"));
        assertTrue(allCommands.containsKey("outbox"));
        assertTrue(allCommands.containsKey("setfilter"));
        assertTrue(allCommands.containsKey("help"));
        assertTrue(allCommands.containsKey("exit"));
    }
}
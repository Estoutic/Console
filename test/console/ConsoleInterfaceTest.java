package console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleInterfaceTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testStartWithExitCommand() {
        String input = "exit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Mail Server Console v1.0"));
        assertTrue(output.contains("Type 'help' for available commands"));
        assertTrue(output.contains("Mail Server terminated"));
    }

    @Test
    void testStartWithHelpCommand() {
        String input = "help\nexit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Available commands:"));
    }

    @Test
    void testEmptyInput() {
        String input = "\nexit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Mail Server terminated"));
    }
}
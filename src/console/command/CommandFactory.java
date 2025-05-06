package console.command;

import console.command.impl.*;
import storage.UserStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandFactory {
    private final Map<String, Command> commands;

    public CommandFactory(Scanner scanner, UserStorage userStorage) {
        commands = new HashMap<>();

        commands.put("add", new AddUserCommand(scanner, userStorage));
        commands.put("list", new ListUsersCommand(scanner, userStorage));
        commands.put("send", new SendMessageCommand(scanner, userStorage));
        commands.put("inbox", new InboxCommand(scanner, userStorage));
        commands.put("spam", new SpamCommand(scanner, userStorage));
        commands.put("outbox", new OutboxCommand(scanner, userStorage));
        commands.put("setfilter", new SetFilterCommand(scanner, userStorage));
        commands.put("help", new HelpCommand(scanner, userStorage, commands));
        commands.put("exit", new ExitCommand(scanner, userStorage));
    }

    public Command getCommand(String commandName) {
        return commands.getOrDefault(commandName, new UnknownCommand(commandName));
    }

    public Map<String, Command> getAllCommands() {
        return new HashMap<>(commands);
    }
}
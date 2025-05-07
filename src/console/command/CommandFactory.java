package console.command;

import console.command.impl.*;
import storage.UserStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandFactory {
    private Map<String, Command> commands;
    private Scanner scanner;
    private UserStorage storage;

    public CommandFactory(Scanner scanner, UserStorage userStorage) {
        this.scanner = scanner;
        this.storage = userStorage;
        commands = new HashMap<>();

        registerCommands();
    }

    private void registerCommands() {
        commands.put("add", new AddUserCommand(scanner, storage));

        commands.put("list", new ListUsersCommand(scanner, storage));

        commands.put("send", new SendMessageCommand(scanner, storage));

        commands.put("inbox", new InboxCommand(scanner, storage));
        commands.put("spam", new SpamCommand(scanner, storage));
        commands.put("outbox", new OutboxCommand(scanner, storage));

        commands.put("setfilter", new SetFilterCommand(scanner, storage));

        commands.put("help", new HelpCommand(scanner, storage, commands));
        commands.put("exit", new ExitCommand(scanner, storage));
    }


    public Command getCommand(String commandName) {
        Command cmd = commands.get(commandName);

        if (cmd == null) {
            return new UnknownCommand(commandName);
        }

        return cmd;
    }

    public Map<String, Command> getAllCommands() {
        return commands;
    }
}
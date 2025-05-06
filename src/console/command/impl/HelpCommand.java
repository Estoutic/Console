package console.command.impl;

import console.command.AbstractCommand;
import console.command.Command;
import storage.UserStorage;

import java.util.Map;
import java.util.Scanner;

public class HelpCommand extends AbstractCommand {
    private final Map<String, Command> commands;

    public HelpCommand(Scanner scanner, UserStorage userStorage, Map<String, Command> commands) {
        super(scanner, userStorage);
        this.commands = commands;
    }

    @Override
    public void execute() {
        System.out.println("Available commands:");

        commands.forEach((name, command) -> {
            System.out.printf("  %-10s - %s%n", name, command.getDescription());
        });
    }

    @Override
    public String getDescription() {
        return "Show available commands";
    }
}
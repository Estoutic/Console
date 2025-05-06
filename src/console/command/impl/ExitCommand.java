package console.command.impl;

import console.command.AbstractCommand;
import storage.UserStorage;

import java.util.Scanner;

public class ExitCommand extends AbstractCommand {

    public ExitCommand(Scanner scanner, UserStorage userStorage) {
        super(scanner, userStorage);
    }

    @Override
    public void execute() {
        System.out.println("Exiting mail server...");
    }

    @Override
    public String getDescription() {
        return "Exit the application";
    }
}
package console.command;

import storage.UserStorage;

import java.util.Scanner;

public abstract class AbstractCommand implements Command {
    protected final Scanner scanner;
    protected final UserStorage userStorage;

    public AbstractCommand(Scanner scanner, UserStorage userStorage) {
        this.scanner = scanner;
        this.userStorage = userStorage;
    }
}
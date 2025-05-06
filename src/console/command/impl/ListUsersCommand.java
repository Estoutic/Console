package console.command.impl;

import console.command.AbstractCommand;
import storage.UserStorage;

import java.util.Scanner;
import java.util.Set;

public class ListUsersCommand extends AbstractCommand {

    public ListUsersCommand(Scanner scanner, UserStorage userStorage) {
        super(scanner, userStorage);
    }

    @Override
    public void execute() {
        Set<String> userNames = userStorage.getAllUserNames();

        if (userNames.isEmpty()) {
            System.out.println("No users found");
            return;
        }

        System.out.println("Users:");
        for (String userName : userNames) {
            System.out.println("* " + userName);
        }

        System.out.println("Total: " + userStorage.getUserCount() + " users");
    }

    @Override
    public String getDescription() {
        return "List all users";
    }
}
package console.command.impl;

import console.command.AbstractCommand;
import model.User;
import storage.UserStorage;

import java.util.Scanner;

public class AddUserCommand extends AbstractCommand {

    public AddUserCommand(Scanner scanner, UserStorage userStorage) {
        super(scanner, userStorage);
    }

    @Override
    public void execute() {
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine().trim();

        if (userStorage.hasUser(userName)) {
            System.out.println("User '" + userName + "' already exists");
        } else {
            User user = new User(userName);
            userStorage.addUser(user);
            System.out.println("User '" + userName + "' added");
        }
    }

    @Override
    public String getDescription() {
        return "Add a new user";
    }
}
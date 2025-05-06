package console.command.impl;

import console.command.AbstractCommand;
import exceptions.UserNotFoundException;
import model.User;
import storage.UserStorage;

import java.util.Scanner;

public class SendMessageCommand extends AbstractCommand {

    public SendMessageCommand(Scanner scanner, UserStorage userStorage) {
        super(scanner, userStorage);
    }

    @Override
    public void execute() {
        try {
            System.out.print("Enter sender name: ");
            String senderName = scanner.nextLine().trim();
            User sender = userStorage.getUser(senderName);

            System.out.print("Enter receiver name: ");
            String receiverName = scanner.nextLine().trim();
            User receiver = userStorage.getUser(receiverName);

            System.out.print("Caption: ");
            String caption = scanner.nextLine().trim();

            System.out.print("Text: ");
            String text = scanner.nextLine().trim();

            sender.sendMessage(caption, text, receiver);
            System.out.println("Message sent");

        } catch (UserNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Send a message from one user to another";
    }
}
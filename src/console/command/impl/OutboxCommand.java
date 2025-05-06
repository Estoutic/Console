package console.command.impl;

import console.command.AbstractCommand;
import exceptions.UserNotFoundException;
import model.Message;
import model.User;
import storage.UserStorage;

import java.util.List;
import java.util.Scanner;

public class OutboxCommand extends AbstractCommand {

    public OutboxCommand(Scanner scanner, UserStorage userStorage) {
        super(scanner, userStorage);
    }

    @Override
    public void execute() {
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine().trim();

        try {
            User user = userStorage.getUser(userName);
            List<Message> outbox = user.getOutbox();

            if (outbox.isEmpty()) {
                System.out.println("No messages in outbox");
                return;
            }

            System.out.println("=======================================");
            for (Message message : outbox) {
                System.out.println("Заголовок");
                System.out.println(message.getCaption());
                System.out.println("=======================================");
                System.out.println("Text:");
                System.out.println(message.getText());
                System.out.println("=======================================");
                System.out.println("To: " + message.getReceiver().getUserName());
                System.out.println("=======================================");
            }
        } catch (UserNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Show outbox messages for a specific user";
    }
}
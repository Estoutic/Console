package console.command.impl;

import console.command.AbstractCommand;
import exceptions.UserNotFoundException;
import filter.impl.*;
import model.User;
import storage.UserStorage;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SetFilterCommand extends AbstractCommand {

    public SetFilterCommand(Scanner scanner, UserStorage userStorage) {
        super(scanner, userStorage);
    }

    @Override
    public void execute() {
        try {
            System.out.print("Enter user name: ");
            String userName = scanner.nextLine().trim();
            User user = userStorage.getUser(userName);

            CompositeSpamFilter compositeFilter = new CompositeSpamFilter();
            boolean reading = true;

            while (reading) {
                System.out.print("Enter filter: ");
                String filterType = scanner.nextLine().trim().toLowerCase();

                if ("done".equals(filterType)) {
                    reading = false;
                    continue;
                }

                switch (filterType) {
                    case "simple":
                        compositeFilter.addFilter(new SimpleSpamFilter());
                        break;

                    case "keywords":
                        System.out.print("Enter keywords: ");
                        String keywordsLine = scanner.nextLine().trim();
                        List<String> keywords = Arrays.stream(keywordsLine.split("\\s+")).toList();
                        KeywordsSpamFilter filter = new KeywordsSpamFilter();
                        filter.setKeywords(keywords);
                        compositeFilter.addFilter(filter);
                        break;

                    case "repetition":
                        System.out.print("Enter max repetitions: ");
                        try {
                            int maxRepetitions = Integer.parseInt(scanner.nextLine().trim());
                            compositeFilter.addFilter(new RepetitionSpamFilter(maxRepetitions));
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid number format");
                        }
                        break;

                    case "sender":
                        System.out.print("Enter sender name: ");
                        String senderName = scanner.nextLine().trim();
                        try {
                            User sender = userStorage.getUser(senderName);
                            SenderSpamFilter senderFilter = new SenderSpamFilter();
                            senderFilter.addUser(sender.getUserName());
                            compositeFilter.addFilter(senderFilter);
                        } catch (UserNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    default:
                        System.out.println("Unknown filter type: " + filterType);
                        System.out.println("Available types: simple, keywords, repetition, sender");
                        break;
                }
            }

            user.setSpamFilter(compositeFilter);
            System.out.println("Filter is set");

        } catch (UserNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Set spam filter for a specific user";
    }
}
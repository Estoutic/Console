package console;

import console.command.Command;
import console.command.CommandFactory;
import storage.UserStorage;

import java.util.Scanner;

public class ConsoleInterface {
    private final Scanner scanner;
    private final UserStorage userStorage;
    private final CommandFactory commandFactory;
    private boolean running;

    public ConsoleInterface() {
        this.scanner = new Scanner(System.in);
        this.userStorage = new UserStorage();
        this.commandFactory = new CommandFactory(scanner, userStorage);
        this.running = true;
    }

    public void start() {
        System.out.println("Mail Server Console v1.0");
        System.out.println("Type 'help' for available commands");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            try {
                Command command = commandFactory.getCommand(input);
                command.execute();

                if (input.equals("exit")) {
                    running = false;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Mail Server terminated");
    }

    public void stop() {
        running = false;
    }
}
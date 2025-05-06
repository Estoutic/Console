package console.command.impl;

import console.command.Command;

public class UnknownCommand implements Command {
    private final String commandName;

    public UnknownCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public void execute() {
        System.out.println("Unknown command: " + commandName);
        System.out.println("Type 'help' for available commands");
    }

    @Override
    public String getDescription() {
        return "Unknown command";
    }
}
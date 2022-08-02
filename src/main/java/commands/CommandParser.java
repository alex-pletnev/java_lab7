package commands;

import commands.com.ExecuteScript;
import commands.com.History;
import exception.ArgumentException;
import exception.AuthorizerException;
import exception.CommandException;
import exception.UnknownCommandException;
import util.CommandManager;
import util.Manager;
import util.Reply;
import util.User;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandParser {


    public CommandInterface parseCommand(CommandManager manager, Scanner input, User user) {
            try {
                String str = input.nextLine();
                if (!str.equals("")) {
                    String[] splitStr = str.split(" ");

                    if (splitStr[0].equals("execute_script")) {

                        ExecuteScript executeScript = new ExecuteScript();
                        executeScript.setUser(user);
                        try {
                            executeScript.setArgList(splitStr);
                            executeScript.checkArguments();
                            System.out.println(executeScript.execute(user));
                        } catch (ArgumentException | CommandException ex) {
                            System.err.println(ex.getMessage());
                        }

                        return null;
                    }

                    if (manager.getCommandMap().get(splitStr[0]) == null) {
                        try {
                            throw new UnknownCommandException("Введена неизвестная команда!");
                        } catch (UnknownCommandException ex) {
                            System.err.println(ex.getMessage());
                            return null;
                        }
                    }

                    if (splitStr[0].equals("exit")) {
                        System.out.println("Сеанс завершенн");
                        System.exit(1);
                    }

                    manager.getCommandMap().get(splitStr[0]).setArgList(splitStr);
                    manager.getCommandMap().get(splitStr[0]).setUser(user);
                    CommandInterface command = manager.getCommandMap().get(splitStr[0]);
                    try {
                        command.checkArguments(input, 0);
                    } catch (ArgumentException ex) {
                        System.err.println(ex.getMessage());
                        return null;
                    }



                    return command;

                } else {
                    System.err.print("Enter your command...(plz)\n");
                    return null;
                }
            } catch (NoSuchElementException ex) {
                System.err.println("Обнорженно прирывание!!");
                System.exit(1);
                return null;
            }
    }
    public Reply executeCommand(Manager manager, CommandInterface command, User user) {
        try {
            Reply reply = command.execute(manager, user);
            History.getHistoryList().add(command.toString());
            return reply;
        } catch (CommandException ex) {
            return new Reply(ex.getMessage());
        } catch (AuthorizerException ex) {
            Reply reply = new Reply(ex.getMessage());
            reply.setStatus(false);
            return reply;
        }
    }
}

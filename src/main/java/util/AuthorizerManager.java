package util;

import commands.CommandAbstract;
import exception.ArgumentException;

import java.util.Scanner;

public class AuthorizerManager {

    public CommandAbstract initAuthorizer(Scanner scanner)  {
        CommandAbstract commandAbstract;
        System.out.println("Вход в систему:\n" +
                "1. Войти\n" +
                "2. Создать аккаунт");
        while(true) {
            String input = scanner.nextLine();
            if (input == null || input.equals("") || !input.matches("1|2|[Вв]ойти|[Сс]оздать")) {
                System.err.println("Допустимый инпут:\n" +
                        "1 или [Вв]ойти\n" +
                        "2 или [Сс]оздать");
                continue;
            } else if (input.matches("1|[Вв]ойти")) {
                commandAbstract = new SignIn();
                try {
                    commandAbstract.checkArguments(scanner, 0);
                } catch (ArgumentException ex) {
                    ex.printStackTrace();
                }
                return commandAbstract;
            }
            commandAbstract = new CreateAnAccount();
            try {
                commandAbstract.checkArguments(scanner, 0);
            } catch (ArgumentException ex) {
                ex.printStackTrace();
            }
            return commandAbstract;
            }
        }
    }


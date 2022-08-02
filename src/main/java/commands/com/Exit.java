package commands.com;

import commands.CommandAbstract;
import exception.ArgumentException;
import util.Manager;
import util.Reply;
import util.User;

import java.util.List;
import java.util.Scanner;

public class Exit extends CommandAbstract {
    private final String name = "exit";
    @Override
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        if (getArgList().size() != 0) {
            throw new ArgumentException("Аргумент введен неверно!");
        }
        return getArgList();
    }
    @Override
    public boolean getNewEl() {
        return false;
    }
    @Override
    public Reply execute(Manager manager, User user) {
        System.exit(1);
        return new Reply("Сессия завершенна");
    }
}

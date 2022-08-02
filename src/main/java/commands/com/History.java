package commands.com;

import commands.CommandAbstract;
import exception.ArgumentException;
import exception.CommandException;
import util.Manager;
import util.Reply;
import util.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class History extends CommandAbstract {
    private final String name = "history";
    private static final List<String> historyList = new ArrayList<>();
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
    public Reply execute(Manager manager, User user) throws CommandException {
        StringBuilder ans = new StringBuilder();
        ans.append("---------------------\n");
        ans.append("---------------------\n");
        while (historyList.size() > 14) {
            historyList.remove(0);
        }
        for (String com : historyList) {
            ans.append(com + "\n");
        }
        ans.append("---------------------\n");
        ans.append("---------------------\n");

        return new Reply(ans.toString());
    }

    public static List<String> getHistoryList() {
        return historyList;
    }

    @Override
    public String toString() {
        return "history";
    }
}

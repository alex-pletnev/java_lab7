package commands.com;

import commands.CommandAbstract;
import exception.ArgumentException;
import util.Manager;
import util.Reply;
import util.User;

import java.util.List;
import java.util.Scanner;

public class Info extends CommandAbstract {
    private final String name = "info";
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
        String ans;
        synchronized (manager.getCollectionManager().getCollection()) {
            ans = "Type: " + manager.getCollectionManager().getCollection().getClass() + "\n" +
                    "Date-Time of Init: " + manager.getCollectionManager().getCollectionInitTime() + "\n" +
                    "Element in the collection: " + manager.getCollectionManager().getCollection().size();
        }
        return new Reply(ans);

    }

    @Override
    public String toString() {
        return "info";
    }
}

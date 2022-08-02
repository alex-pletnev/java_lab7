package commands.com;

import SQLutil.SQLManager;
import commands.CommandAbstract;
import data.City;
import exception.ArgumentException;
import exception.CommandException;
import util.Manager;
import util.Reply;
import util.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Clear extends CommandAbstract {
    private final String name = "clear";
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
        try {
            SQLManager.clear(getUser());
        } catch (SQLException ex) {
            throw new CommandException("Ошибка при работе с БД!");
        }
        synchronized (manager.getCollectionManager().getCollection()) {
            TreeSet<City> col = new TreeSet<>();
            for (City city : manager.getCollectionManager().getCollection()) {
                if (!city.getUsername().equals(user.getUsername())) {
                    col.add(city);
                }
            }
            manager.getCollectionManager().setCollection(col);
            return new Reply("Коллекция очищенна");
        }
    }

    @Override
    public String toString() {
        return "clear";

    }
}

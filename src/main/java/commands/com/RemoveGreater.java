package commands.com;

import SQLutil.SQLManager;
import commands.CommandAbstract;
import commands.ElementInput;
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

public class RemoveGreater extends CommandAbstract {
    private final String name = "remove_greater";
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        if (getArgList().size() != 0) {
            throw new ArgumentException("Аргумент введен неверно!");
        }
        City newCity = ElementInput.getNewElement(scanner, mode);
        if (newCity == null) {
            throw new ArgumentException("Ошибка в файле");
        }
        getArgList().add(newCity);

        return getArgList();
    }
    @Override
    public boolean getNewEl() {
        return true;
    }
    @Override
    public Reply execute(Manager manager, User user) throws CommandException {
        City testCity = (City) getArgList().get(0);
        testCity.setUsername(user.getUsername());
        try {
            SQLManager.removeGreater(user, testCity);
        } catch (SQLException ex) {
            throw new CommandException("Ошибка при работе с БД");
        }
        synchronized (manager.getCollectionManager().getCollection()) {
            TreeSet<City> col = new TreeSet<>();
            for (City city : manager.getCollectionManager().getCollection()) {
                if (!(city.compareTo(testCity) > 0 && city.getUsername().equals(user.getUsername()))) {
                    col.add(city);
                }
            }
            manager.getCollectionManager().setCollection(col);
        }
        return new Reply("Комманда выполненна!");
    }

    @Override
    public String toString() {
        return "remove_greater";
    }
}

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
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AddIfMax extends CommandAbstract {
    private final String name = "add_if_max";

    @Override
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        if (getArgList().size() != 0) {
            throw new ArgumentException("Аргумент введен неверно!");
        }
        City newCity = ElementInput.getNewElement(scanner, mode);
        if (newCity == null) {
            throw new ArgumentException("Ошибка в файле");
        }        getArgList().add(newCity);

        return getArgList();
    }

    @Override
    public boolean getNewEl() {
        return true;
    }
    @Override
    public Reply execute(Manager manager, User user) throws CommandException{
        City newCity = (City) getArgList().get(0);
        newCity.setUsername(user.getUsername());
        City maxCity;
        synchronized (manager.getCollectionManager().getCollection()) {
            maxCity = manager.getCollectionManager().getCollection()
                    .stream()
                    .max(Comparator.comparing(City::getCoordinates))
                    .orElse(null);
        }
        if (maxCity == null) {
            try {
                newCity.setId(SQLManager.getNextId());
                SQLManager.add(newCity, getUser());
            } catch (SQLException ex) {
                throw new CommandException("Ошибка при каботе с БД!, элемент не добавлен");
            }
            synchronized (manager.getCollectionManager().getCollection()){
                manager.getCollectionManager().getCollection().add(newCity);
            }
            return new Reply("Элемент добавлен!");
        }
        if (newCity.compareTo(maxCity) < 0) {
                throw new CommandException("Новый элемент не добавлет т.к. не максимальный");
        }
        try {
            SQLManager.add(newCity, getUser());
        } catch (SQLException ex) {
            throw new CommandException("Ошибка при каботе с БД!, элемент не добавлен");
        }
        synchronized (manager.getCollectionManager().getCollection()) {
            manager.getCollectionManager().getCollection().add(newCity);
        }
        return new Reply("Элемент добавлен!");
    }

    @Override
    public String toString() {
        return "add_if_max";
    }
}

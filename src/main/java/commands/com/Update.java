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

public class Update extends CommandAbstract {
    private final String name = "update";
    @Override
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        if (getArgList().size() != 1) {
            throw new ArgumentException("Неверное количество аргументов повторите ввод команды!");
        }
        try {
            Long.parseLong(((String) getArgList().get(0)));
        } catch (NumberFormatException exception) {
            throw new ArgumentException("Неверный тип данных аргументов(тип данных long)");
        }
        City newCity = ElementInput.getNewElement(scanner, mode);
        if (newCity == null) {
            throw new ArgumentException("Ошибка в файле");
        }
        newCity.setId(Long.parseLong(((String) getArgList().get(0))));
        getArgList().add(newCity);
        return getArgList();
    }
    @Override
    public boolean getNewEl() {
        return true;
    }
    @Override
    public Reply execute(Manager manager, User user) throws CommandException {
        long id = Long.parseLong(((String) getArgList().get(0)));

        try {
            if (!SQLManager.removeById(user, id)) {
                throw new CommandException("Элемент с данным id не найден");
            }
            City newCity = (City) getArgList().get(1);
            newCity.setUsername(user.getUsername());
            newCity.setId(id);
            SQLManager.add(newCity, user);
            synchronized (manager.getCollectionManager().getCollection()) {
        for (City city : manager.getCollectionManager().getCollection()) {
            if (city.getId() == id) {
                manager.getCollectionManager().getCollection().remove(city);
                manager.getCollectionManager().getCollection().add(newCity);
                return new Reply("Элемент обновлен");
            }
        }
            }

        throw new CommandException("Элемент с данным id не найден");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new CommandException("Ошибка при работе с БД");
        }
    }

    @Override
    public String toString() {
        return "show";
    }
}

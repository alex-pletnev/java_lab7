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

public class RemoveById extends CommandAbstract {
    private final String name = "remove_by_id";

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
        return getArgList();
    }

    @Override
    public boolean getNewEl() {
        return false;
    }
    @Override
    public Reply execute(Manager manager, User user) throws CommandException {
        long id = Long.parseLong(((String) getArgList().get(0)));
        City correctCity;
        synchronized (manager.getCollectionManager().getCollection()) {
            correctCity = manager.getCollectionManager().getCollection()
                    .stream()
                    .filter(city -> city.getId() == id)
                    .filter(city -> city.getUsername().equals(user.getUsername()))
                    .findAny()
                    .orElse(null);
        }
        if (correctCity == null) {
            throw new CommandException("Элемент с данным id не найден");
        }
        try {
            SQLManager.removeById(user, id);
        } catch (SQLException ex) {
            throw new CommandException("Ошибка при работе с БД, элемент не удаллен!");
        }
        synchronized (manager.getCollectionManager().getCollection()) {
            manager.getCollectionManager().getCollection().remove(correctCity);
        }
        return new Reply("Элемент удален!");
    }
}

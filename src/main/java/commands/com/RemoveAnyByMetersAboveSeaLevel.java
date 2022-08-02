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

public class RemoveAnyByMetersAboveSeaLevel extends CommandAbstract {
    private final String name = "remove_any_by_meters_above_sea_level";
    @Override
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        if (getArgList().size() != 1) {
            throw new ArgumentException("Неверное количество аргументов повторите ввод команды!");
        }
        try {
            Double.parseDouble(((String) getArgList().get(0)));
        } catch (NumberFormatException exception) {
            throw new ArgumentException("Неверный тип данных аргументов(тип данных double)");
        }
        return getArgList();
    }
    @Override
    public boolean getNewEl() {
        return false;
    }
    @Override
    public Reply execute(Manager manager, User user) throws CommandException {
        City anyCorrectCity;
        synchronized (manager.getCollectionManager().getCollection()) {
            anyCorrectCity = manager.getCollectionManager().getCollection()
                    .stream()
                    .filter(city -> city.getMetersAboveSeaLevel() == Double.parseDouble(((String) getArgList().get(0))))
                    .filter(city -> city.getUsername().equals(user.getUsername()))
                    .findAny()
                    .orElse(null);
        }

        if (anyCorrectCity == null) {
            throw new CommandException("Подходящего элемента не найденно!");
        }


        try {
            SQLManager.removeById(user, anyCorrectCity.getId());

        } catch (SQLException ex) {
            throw new CommandException("Ошибка при каботе с БД!, элемент не добавлен");
        }
        synchronized (manager.getCollectionManager().getCollection()) {
            manager.getCollectionManager().getCollection().remove(anyCorrectCity);
        }
        return new Reply("Элемент удален");

    }
}

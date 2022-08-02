package commands.com;

import commands.CommandAbstract;
import exception.ArgumentException;
import util.Manager;
import util.Reply;
import util.User;

import java.util.List;
import java.util.Scanner;

public class Help extends CommandAbstract {
    private final String name = "help";
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
        return new Reply ("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "history : вывести последние 14 команд (без их аргументов)\n" +
                "remove_any_by_meters_above_sea_level metersAboveSeaLevel : удалить из коллекции один элемент, значение поля metersAboveSeaLevel которого эквивалентно заданному\n" +
                "group_counting_by_meters_above_sea_level : сгруппировать элементы коллекции по значению поля metersAboveSeaLevel, вывести количество элементов в каждой группе\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания");
    }

    @Override
    public String toString() {
        return "help";
    }
}

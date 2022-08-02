package commands.com;

import commands.CommandAbstract;
import data.City;
import exception.ArgumentException;
import util.Manager;
import util.Reply;
import util.User;

import java.util.*;
import java.util.stream.Collectors;

public class GroupCountingByMetersAboveSeaLevel extends CommandAbstract {
    private final transient String name = "group_counting_by_meters_above_sea_level";
    private int i = 0;
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
        StringBuilder ans = new StringBuilder();
        synchronized (manager.getCollectionManager().getCollection()) {
            Set<Double> metersSet = manager.getCollectionManager().getCollection()
                    .stream()
                    .map(City::getMetersAboveSeaLevel)
                    .filter(c -> c > 10)
                    .sorted()
                    .limit(10)
                    .collect(Collectors.toSet());

            for (Double key : metersSet) {
                i++;
                ans.append("Группа ")
                        .append(i)
                        .append(": ")
                        .append(manager.getCollectionManager().getCollection()
                                .stream()
                                .filter(city -> city.getMetersAboveSeaLevel() == key)
                                .count()
                        )
                        .append("\n");
            }
        }
        return new Reply(ans.toString());
    }

    @Override
    public String toString() {
        return "group_counting_by_meters_above_sea_level";

    }
}

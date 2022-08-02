package util;

import commands.CommandInterface;
import commands.com.*;

import java.util.Map;
import java.util.TreeMap;

public class CommandManager {
    private final Map<String , CommandInterface> commandMap = new TreeMap<>();
    {
        commandMap.put("help", new Help());
        commandMap.put("info", new Info());
        commandMap.put("show", new Show());
        commandMap.put("add", new Add());
        commandMap.put("update", new Update());
        commandMap.put("remove_by_id", new RemoveById());
        commandMap.put("clear", new Clear());
        commandMap.put("exit", new Exit());
        commandMap.put("add_if_max", new AddIfMax());
        commandMap.put("remove_greater", new RemoveGreater());
        commandMap.put("history", new History());
        commandMap.put("remove_any_by_meters_above_sea_level", new RemoveAnyByMetersAboveSeaLevel());
        commandMap.put("group_counting_by_meters_above_sea_level", new GroupCountingByMetersAboveSeaLevel());
        commandMap.put("print_ascending", new PrintAscending());

    }

    public Map<String, CommandInterface> getCommandMap() {
        return commandMap;
    }
}

package commands;

import util.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandAbstract implements CommandInterface {
    private List<Object> argList = new ArrayList<>();
    private String name;
    private User user;
    @Override
    public User getUser() {
        return user;
    }
    @Override
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public List<Object> getArgList() {
        return argList;
    }
    @Override
    public void setArgList(String[] argArr) {
        argList.clear();
        argList.addAll(Arrays.asList(argArr));
        argList.remove(0);

    }

    @Override
    public String toString() {
        return getClass().toString();
    }
}

package util;

import SQLutil.SQLManager;
import commands.CommandAbstract;
import exception.ArgumentException;
import exception.AuthorizerException;
import exception.CommandException;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SignIn extends CommandAbstract {
    @Override
    public boolean getNewEl() {
        return false;
    }

    @Override
    public Reply execute(Manager manager, User user) throws CommandException, AuthorizerException {
        User aUser = (User) getArgList().get(0);
        try {
            SQLManager.singIn(aUser);
            setUser(aUser);
            return new Reply("Авторизация завершенна   " + aUser.getUsername() + "   " + aUser.getPassword());
        } catch (SQLException ex) {
            throw new AuthorizerException("Ошибка при взаимодействии с БД");
        }
    }

    @Override
    public List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException {
        Authorizer authorizer = new Authorizer();
        getArgList().add(authorizer.authorize(scanner, mode));
        return getArgList();
    }

    @Override
    public String toString() {
        return "sign_in";
    }
}

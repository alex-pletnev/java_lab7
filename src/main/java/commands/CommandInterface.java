package commands;

import exception.ArgumentException;
import exception.AuthorizerException;
import exception.CommandException;
import util.Manager;
import util.Reply;
import util.User;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public interface CommandInterface extends Serializable {
    boolean getNewEl();
    String getName();
    Reply execute(Manager manager, User user) throws CommandException, AuthorizerException;
    List<Object> getArgList();
    void setArgList(String[] argArr);
    User getUser();
    void setUser(User user);
    //mode == 0 - чтение из System.in
    //mode == 1 - чтение из файла (ExecuteScript)
    List<Object> checkArguments(Scanner scanner, int mode) throws ArgumentException;
}

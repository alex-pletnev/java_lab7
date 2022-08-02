package util;

import java.util.Scanner;

public class Authorizer {



    public User authorize(Scanner scanner,int mode) {
        String username = "";
        String password = "";
        User user = null;
        if (mode == 0) System.out.println("Введите имя пользователя:");
        boolean usernameFlag = false;
        while (!usernameFlag) {
            username = scanner.nextLine();
            if (username == null || username.equals("") || username.length() > 50) {
                if (mode == 0) System.err.println("Некоректное имя пользователя, повтрите ввод:");
                if (mode == 1) return null;
                continue;
            }
            usernameFlag = true;
        }
        if (mode == 0) System.out.println("Введите пароль:");
        boolean passwordFlag = false;
        while (!passwordFlag) {
            password = scanner.nextLine();
            if (password == null || password.equals("")) {
                if (mode == 0) System.out.println("Некоректный пароль, повтрите ввод:");
                if (mode == 1) return null;
                continue;
            }
            passwordFlag = true;
        }
            return new User(username, password);
    }
}

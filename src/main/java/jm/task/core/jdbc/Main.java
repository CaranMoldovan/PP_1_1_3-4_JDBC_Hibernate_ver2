package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Василий", "Петрович", (byte) 25);
        userService.saveUser("Дмитрий", "Бурсович", (byte) 25);
        userService.saveUser("Злата", "Марковна", (byte) 71);
        userService.saveUser("Елена","Подпивко",(byte) 34);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}

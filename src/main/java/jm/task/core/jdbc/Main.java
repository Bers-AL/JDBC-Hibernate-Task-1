package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Logger root = Logger.getLogger("");
        root.setLevel(Level.INFO);
        for (Handler h : root.getHandlers()) {
            h.setLevel(Level.INFO);
        }

        log.info("Запуск программы");
        UserService userService = new UserServiceImpl();

        log.info("Создание таблицы");
        userService.createUsersTable();

        log.info("Повторное создание таблицы");
        userService.createUsersTable();

        log.info("Создание 4-х пользователей");
        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Petr", "Petrov", (byte) 25);
        userService.saveUser("Sidor", "Sidorov", (byte) 30);
        userService.saveUser("Maxim", "Maximov", (byte) 35);

        log.info("Получение всех пользователей");
        for (User user : userService.getAllUsers()) {
            log.info(user.toString());
        }

        log.info("Очистка таблицы");
        userService.cleanUsersTable();

        log.info("Получение всех пользователей после очистки");
        if (userService.getAllUsers().isEmpty()) {
            log.info("Таблица пуста");
        } else {
            log.warning("Таблица не очищена");
        }

        log.info("Удаление таблицы");
        userService.dropUsersTable();

        log.info("Повторное удаление таблицы");
        userService.dropUsersTable();

        log.info("Завершение программы");
    }
}

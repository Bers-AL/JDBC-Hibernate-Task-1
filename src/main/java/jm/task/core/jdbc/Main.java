package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Logger root = Logger.getLogger("");
        root.setLevel(Level.INFO);
        for (Handler h : root.getHandlers()) {
            h.setLevel(Level.INFO);
        }

        logger.info("Запуск программы");
        UserService userService = new UserServiceImpl();

        logger.info("Создание таблицы");
        userService.createUsersTable();

        logger.info("Повторное создание таблицы");
        userService.createUsersTable();

        logger.info("Создание 4-х пользователей");
        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Petr", "Petrov", (byte) 25);
        userService.saveUser("Sidor", "Sidorov", (byte) 30);
        userService.saveUser("Maxim", "Maximov", (byte) 35);

        logger.info("Получение всех пользователей");
        for (User user : userService.getAllUsers()) {
            logger.info(user.toString());
        }

        logger.info("Очистка таблицы");
        userService.cleanUsersTable();

        logger.info("Получение всех пользователей после очистки");
        if (userService.getAllUsers().isEmpty()) {
            logger.info("Таблица пуста");
        } else {
            logger.warning("Таблица не очищена");
        }

        logger.info("Удаление таблицы");
        userService.dropUsersTable();

        logger.info("Повторное удаление таблицы");
        userService.dropUsersTable();

        logger.info("Завершение программы");
    }
}

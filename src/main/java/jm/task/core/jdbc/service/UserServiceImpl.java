package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl_ps;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl_sp;
import jm.task.core.jdbc.model.User;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

//    UserDao userDao = new UserDaoJDBCImpl();
//    UserDao userDao = new UserDaoJDBCImpl_ps();
    UserDao userDao = new UserDaoJDBCImpl_sp();

    public void createUsersTable() {
        try {
            userDao.createUsersTable();
            logger.info("Таблица Users создана");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при создании таблицы Users", e);
        }
    }

    public void dropUsersTable() {
        try {
            userDao.dropUsersTable();
            logger.info("Таблица Users удалена");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при удалении таблицы Users", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            userDao.saveUser(name, lastName, age);
            logger.info("User с именем - " + name + " " + lastName + " добавлен в базу данных");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при добавлении пользователя в базу данных", e);
        }
    }

    public void removeUserById(long id) {
        try {
            userDao.removeUserById(id);
            logger.info("User с id = " + id + " удален из базы данных");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при удалении пользователя из базы данных", e);
        }
    }

    public User getUserById(long id) {
        User user = null;
        try {
            user = userDao.getUserById(id);
            if (user == null) logger.warning("Пользователь с id=" + id + " не найден");
            else logger.info("Получен пользователь с id=" + id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при получении пользователя из базы данных", e);
        }
        return user;
    }


    public List<User> getAllUsers() {
        try {
            List<User> users = userDao.getAllUsers();
            logger.info("Получены все пользователи из бд Users");
            return users;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при получении всех пользователей из базы данных", e);
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() {
        try {
            userDao.cleanUsersTable();
            logger.info("База Users очищена");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при очистке базы Users", e);
        }
    }
}

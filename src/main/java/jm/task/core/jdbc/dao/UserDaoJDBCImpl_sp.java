package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl_sp implements UserDao {

    public UserDaoJDBCImpl_sp() {
        Util.installStoredApi();
    }

    @Override
    public void createUsersTable() {
        String sql = "CALL sp_create_users_table()";
        try (Connection connection = Util.getConnection_JDBC();
             Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблицы users", e);
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "CALL sp_drop_users_table()";
        try (Connection connection = Util.getConnection_JDBC();
             Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы users", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "CALL sp_save_user(?, ?, ?)";
        try (Connection connection = Util.getConnection_JDBC();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setShort(3, (short) age);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении пользователя в таблицу users", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "CALL sp_remove_user_by_id(?)";
        try (Connection connection = Util.getConnection_JDBC();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя из таблицы users", e);
        }
    }

    @Override
    public User getUserById(long id) {
        String sql = "SELECT * FROM fn_get_user_by_id(?)";

        try (Connection connection = Util.getConnection_JDBC();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getByte("age"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пользователя по id из таблицы users", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM fn_get_all_users()";
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection_JDBC();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех пользователей из таблицы users", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        String sql = "CALL sp_clean_users_table()";
        try (Connection connection = Util.getConnection_JDBC();
             Statement st = connection.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при очистке таблицы users", e);
        }
    }
}

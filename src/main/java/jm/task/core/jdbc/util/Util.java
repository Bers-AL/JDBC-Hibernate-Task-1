package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    public static Connection getConnection_JDBC() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/db",
                    "user",
                    "password");
            logger.info("Соединение с базой данных через JDBC установлено");
            return connection;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Не удалось установить соединение с базой данных через JDBC", e);
            throw new RuntimeException("JDBC connection failed", e);
        }
    }

    public static void installStoredApi() {
        try (Connection con = Util.getConnection_JDBC()) {
            SqlScriptRunner.runClasspathSql(con, "db/stored.sql");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при инициализации Stored Procedures API (stored.sql)", e);
        }
    }
}
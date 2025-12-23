package jm.task.core.jdbc.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class SqlScriptRunner {
    private SqlScriptRunner() {}

    private static final Logger logger = Logger.getLogger(SqlScriptRunner.class.getName());

    public static void runClasspathSql(Connection connection, String resourcePath) {
        String sql = readClasspathResource(resourcePath);

        Boolean oldAutoCommit = null;
        try {
            oldAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(true);
            try (Statement st = connection.createStatement()) {
                st.execute(sql);
            }
            logger.info("SQL скрипт : " + resourcePath + " выполнен успешно.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка выполнения SQL скрипта: " + resourcePath, e);
            throw new RuntimeException("Ошибка выполнения SQL скрипта: " + resourcePath, e);
        } finally {
            if (oldAutoCommit != null) {
                try {
                    connection.setAutoCommit(oldAutoCommit);
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Ошибка восстановления autoCommit после скрипта: " + resourcePath, e);
                }
            }
        }
    }

    private static String readClasspathResource(String resourcePath) {
        InputStream is = SqlScriptRunner.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IllegalArgumentException("SQL ресурс не найден: " + resourcePath);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка чтения SQL : " + resourcePath, e);
            throw new RuntimeException("Ошибка чтения SQL : " + resourcePath, e);
        }
    }
}

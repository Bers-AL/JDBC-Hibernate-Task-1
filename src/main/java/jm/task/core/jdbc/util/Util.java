package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    // Hibernate Connection with XML configuration (hibernate.cfg.xml)
    public static SessionFactory getConnection_hibernate_xml() {
        try {
            SessionFactory factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

            logger.info("SessionFactory создан (XML).");
            return factory;

        } catch (MappingException e) {
            logger.log(Level.SEVERE, "Ошибка конфигурации Hibernate (XML).", e);
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка инициализации SessionFactory (XML).", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Неожиданная ошибка при создании SessionFactory (XML).", e);
        }
        return null;
    }

    // Hibernate Connection without XML configuration
    public static SessionFactory getConnection_hibernate_without_xml() {
        try {
            Properties settings = new Properties();
            settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            settings.put("hibernate.hbm2ddl.auto", "update");
            settings.put("hibernate.show_sql", "false");
            settings.put("hibernate.format_sql", "true");

            settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/db");
            settings.put("hibernate.connection.username", "user");
            settings.put("hibernate.connection.password", "password");
            settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");

            SessionFactory factory = new Configuration()
                    .addProperties(settings)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

            logger.info("SessionFactory создан (без XML).");
            return factory;

        } catch (MappingException e) {
            logger.log(Level.SEVERE, "Ошибка конфигурации Hibernate (без XML).", e);
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Ошибка инициализации SessionFactory (без XML).", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Неожиданная ошибка при создании SessionFactory (без XML).", e);
        }
        return null;
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory factory = Util.getConnection_hibernate_without_xml();

    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(50)," +
                    "last_name VARCHAR(50)," +
                    "age INT" +
                    ")";
            session.createNativeQuery(createTableSQL).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            String dropTableSQL = "DROP TABLE IF EXISTS users";
            session.createNativeQuery(dropTableSQL).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public User getUserById(long id) {
        Transaction tx = null;
        User user;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            user = session.get(User.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        List<User> users;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            users = session.createQuery("from User", User.class).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}

package service;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserServiceImplimentation implements UserService {

    @Override
    public boolean createUser(User user) {
        boolean result = false;
        Transaction transaction = null;
        User existing = getUser(user.getUserId());
        if (existing == null)
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.save(user);
                transaction.commit();
                result = true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        return result;
    }

    @Override
    public User getUser(String userId) {
        User user = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, userId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        boolean result = false;
        Transaction transaction = null;
        if (getUser(user.getUserId()) != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.update(user);
                transaction.commit();
                result = true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean deleteUser(User user) {
        boolean result = false;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            result = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteUser(String userId) {
        boolean result = false;
        User user;
        Transaction transaction = null;
        if ((user = getUser(userId)) != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.delete(user);
                transaction.commit();
                result = true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
        return result;
    }

}

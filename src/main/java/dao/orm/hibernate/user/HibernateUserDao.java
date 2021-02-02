package dao.orm.hibernate.user;

import dao.orm.OrmCommentDao;
import dao.orm.OrmUserDao;
import entity.Comment;
import entity.User;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
import hibernate.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Repository("hibernateUserDao")
public class HibernateUserDao implements OrmUserDao {

    private final OrmCommentDao ormCommentDao;

    @Autowired
    public HibernateUserDao(@Qualifier("hibernateCommentDao") OrmCommentDao ormCommentDao) {
        this.ormCommentDao = ormCommentDao;
    }

    @Override
    public User getById(Long id) throws FetchException {
        Objects.requireNonNull(id);
        User out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(User.class, id);
            if (out != null) {
                Collection<Comment> comments = ormCommentDao.commentsWithUserId(id);
                out.setComments(comments);
            }
            transaction.commit();
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        if (out == null) {
            throw new FetchException("No such user with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<User> allEntities() throws FetchException {
        Collection<User> out = new HashSet<>();
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Collection<Long> ids = session
                    .createQuery("SELECT u.id FROM entity.User u", Long.class).list();
            for (Long id : ids) {
                User u = session.get(User.class, id);
                Collection<Comment> comments = ormCommentDao.commentsWithUserId(id);
                u.setComments(comments);
                out.add(u);
            }
            transaction.commit();
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(User user) throws StoreException {
        Objects.requireNonNull(user);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Long id, User user) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(user);
        Long prevId = user.getId();
        user.setId(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            throw new UpdateException(e);
        } finally {
            user.setId(prevId);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM entity.User u WHERE u.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new DeleteException(e);
        }
    }
}

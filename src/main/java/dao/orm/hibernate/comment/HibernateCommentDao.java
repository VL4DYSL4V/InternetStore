package dao.orm.hibernate.comment;

import dao.orm.OrmCommentDao;
import entity.Comment;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
import hibernate.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Objects;

@Repository("hibernateCommentDao")
public final class HibernateCommentDao implements OrmCommentDao {

    @Override
    public Collection<Comment> commentsWithUserId(Long userId) throws FetchException{
        Objects.requireNonNull(userId);
        Collection<Comment> out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Comment> query = session
                    .createSQLQuery("SELECT comment_id, comment_text, time_of_post," +
                            "       comment.user_id, item.item_id " +
                            "FROM comment INNER JOIN item USING(item_id) WHERE user_id = ?;")
                    .addEntity(Comment.class);
            query.setParameter(1, userId);
            out = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public Collection<Comment> commentsWithItemId(Long itemId) throws FetchException{
        Objects.requireNonNull(itemId);
        Collection<Comment> out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Comment> query = session
                    .createSQLQuery("SELECT comment_id, comment_text, time_of_post," +
                            "       comment.user_id, item.item_id " +
                            "FROM comment INNER JOIN item" +
                            "       ON item.item_id = ?" +
                            "            AND comment.item_id = item.item_id;")
                    .addEntity(Comment.class);
            query.setParameter(1, itemId);
            out = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public Comment getById(Long id) throws FetchException {
        Objects.requireNonNull(id);
        Comment out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(Comment.class, id);
            transaction.commit();
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        if (out == null) {
            throw new FetchException("No such comment with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<Comment> allEntities() throws FetchException {
        Collection<Comment> out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.createQuery("SELECT c FROM entity.Comment c", Comment.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(Comment comment) throws StoreException {
        Objects.requireNonNull(comment);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(comment);
            transaction.commit();
        } catch (HibernateException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Long id, Comment comment) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(comment);
        Long prevId = comment.getId();
        comment.setId(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(comment);
            transaction.commit();
        } catch (HibernateException e) {
            throw new UpdateException(e);
        } finally {
            comment.setId(prevId);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM entity.Comment c WHERE c.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new DeleteException(e);
        }
    }

}

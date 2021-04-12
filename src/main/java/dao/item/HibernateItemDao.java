package dao.item;

import dao.comment.CommentDao;
import dao.country.CountryDao;
import dao.utils.SimilarNameCreator;
import entity.Country;
import entity.Item;
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
import java.util.LinkedList;
import java.util.Objects;

@Repository("itemDao")
public final class HibernateItemDao implements ItemDao {

    private final CommentDao commentDao;
    private final CountryDao countryDao;

    public HibernateItemDao(CommentDao commentDao, CountryDao countryDao) {
        this.commentDao = commentDao;
        this.countryDao = countryDao;
    }

    @Override
    public Item getById(Long id) throws FetchException {
        Objects.requireNonNull(id);
        Item out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(Item.class, id);
            out.setComments(commentDao.commentsWithItemId(id));

            transaction.commit();
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        if (out == null) {
            throw new FetchException("No such item with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<Item> allEntities() throws FetchException {
        Collection<Item> out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.createQuery("SELECT i FROM entity.Item i", Item.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(Item item) throws StoreException {
        Objects.requireNonNull(item);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        } catch (HibernateException e) {
            throw new StoreException(e);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void update(Long id, Item item) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(item);
        Long prevId = item.getId();
        item.setId(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        } catch (HibernateException e) {
            throw new UpdateException(e);
        } finally {
            item.setId(prevId);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM entity.Item WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new DeleteException(e);
        }
    }

    @Override
    public Collection<Item> itemsWithSimilarName(String name) throws FetchException {
        Objects.requireNonNull(name);
        Collection<Item> out = new LinkedList<>();
//        try (Session session = HibernateUtils.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            Query<Item> query = session.createQuery("SELECT i FROM entity.Item i WHERE i.name LIKE :name", Item.class);
//            Collection<String> similarStrings = SimilarNameCreator.createSimilarStrings(name);
//            for (String s : similarStrings) {
//                query.setParameter("name", s);
//                out.addAll(query.list());
//            }
//            transaction.commit();
//        } catch (HibernateException e) {
//            throw new FetchException(e);
//        }
        return out;
    }


}

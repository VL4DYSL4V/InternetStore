package dao.ban;

import entity.Ban;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
import hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

@Repository("banDao")
public final class HibernateBanDao implements BanDao {

    @Override
    @Nullable
    public Ban getById(Long id) throws FetchException {
        Ban out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(Ban.class, id);
            transaction.commit();
        } catch (Throwable e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public Collection<Ban> allEntities() throws FetchException {
        Collection<Ban> out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Ban> query = session.createQuery("SELECT b FROM entity.Ban b", Ban.class);
            out = query.list();
            transaction.commit();
        } catch (Throwable throwable) {
            throw new FetchException(throwable);
        }
        return out;
    }

    @Override
    public void save(Ban ban) throws StoreException {
        Objects.requireNonNull(ban);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(ban);
            transaction.commit();
        } catch (Throwable t) {
            throw new StoreException(t);
        }
    }

    @Override
    public void update(Long id, Ban ban) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(ban);
        Long oldId = ban.getId();
        ban.setId(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(ban);
            transaction.commit();
        } catch (Throwable throwable) {
            throw new UpdateException(throwable);
        } finally {
            ban.setId(oldId);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM entity.Ban b WHERE b.id = :id", Ban.class);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Throwable throwable) {
            throw new DeleteException(throwable);
        }
    }
}

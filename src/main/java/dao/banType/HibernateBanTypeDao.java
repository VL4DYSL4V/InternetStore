package dao.banType;

import entity.BanType;
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

@Repository("banTypeDao")
public final class HibernateBanTypeDao implements BanTypeDao {

    @Override
    @Nullable
    public BanType getById(Integer id) throws FetchException {
        Objects.requireNonNull(id);
        BanType out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(BanType.class, id);
            transaction.commit();
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        return out;
    }

    @Override
    public Collection<BanType> allEntities() throws FetchException {
        Collection<BanType> banTypes;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            banTypes = session.createQuery("SELECT banType FROM entity.BanType banType", BanType.class).list();
            transaction.commit();
        } catch (Throwable throwable) {
            throw new FetchException(throwable);
        }
        return banTypes;
    }

    @Override
    public void save(BanType banType) throws StoreException {
        Objects.requireNonNull(banType);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(banType);
            transaction.commit();
        } catch (Throwable t) {
            throw new StoreException(t);
        }
    }

    @Override
    public void update(Integer id, BanType banType) throws UpdateException {
        Objects.requireNonNull(banType);
        Objects.requireNonNull(id);
        Integer oldId = banType.getId();
        banType.setId(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(banType);
            transaction.commit();
        } catch (Throwable t) {
            throw new UpdateException(t);
        } finally {
            banType.setId(oldId);
        }
    }

    @Override
    public void delete(Integer id) throws DeleteException {
        Objects.requireNonNull(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM entity.BanType bt WHERE bt.id = :id", BanType.class);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Throwable throwable) {
            throw new DeleteException(throwable);
        }
    }
}

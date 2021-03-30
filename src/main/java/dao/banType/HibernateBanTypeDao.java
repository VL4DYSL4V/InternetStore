package dao.banType;

import entity.BanType;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
import hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

@Repository("banTypeDao")
public final class HibernateBanTypeDao implements BanTypeDao {

    @Override
    @Nullable
    public BanType getById(Long id) throws FetchException {
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
        return null;
    }

    @Override
    public void save(BanType banType) throws StoreException {

    }

    @Override
    public void update(Long id, BanType banType) throws UpdateException {

    }

    @Override
    public void delete(Long id) throws DeleteException {

    }
}

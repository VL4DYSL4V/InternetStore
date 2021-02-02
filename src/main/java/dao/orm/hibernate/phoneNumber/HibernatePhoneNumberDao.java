package dao.orm.hibernate.phoneNumber;

import dao.orm.OrmPhoneNumberDao;
import entity.PhoneNumber;
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

@Repository("hibernatePhoneNumberDao")
public final class HibernatePhoneNumberDao implements OrmPhoneNumberDao {

    @Override
    public PhoneNumber getById(Long id) throws FetchException {
        Objects.requireNonNull(id);
        PhoneNumber out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(PhoneNumber.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        if (out == null) {
            throw new FetchException("No such phone number with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<PhoneNumber> allEntities() throws FetchException {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Collection<PhoneNumber> out =
                    session.createQuery("SELECT ph FROM entity.PhoneNumber ph", PhoneNumber.class).list();
            transaction.commit();
            return out;
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
    }

    @Override
    public void save(PhoneNumber phoneNumber) throws StoreException {
        Objects.requireNonNull(phoneNumber);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(phoneNumber);
            transaction.commit();
        } catch (HibernateException e) {
            throw new StoreException(e);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void update(Long id, PhoneNumber phoneNumber) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(phoneNumber);
        Long prevId = phoneNumber.getId();
        phoneNumber.setId(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(phoneNumber);
            transaction.commit();
        } catch (HibernateException e) {
            throw new UpdateException(e);
        }finally {
            phoneNumber.setId(prevId);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> q = session.createQuery("DELETE FROM entity.PhoneNumber WHERE id = :id");
            q.setParameter("id", id);
            q.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new DeleteException(e);
        }
    }
}

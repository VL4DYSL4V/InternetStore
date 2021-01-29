package dao.currency;

import entity.Currency;
import exception.DeleteException;
import exception.FetchException;
import exception.StoreException;
import exception.UpdateException;
import hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Objects;

@Repository("hibernateCurrencyDao")
@NotThreadSafe
public final class HibernateCurrencyDao implements CurrencyDao {

    @Override
    public Currency getById(Integer id) throws FetchException {
        Objects.requireNonNull(id);
        Currency out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(Currency.class, id);
            transaction.commit();
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        if(out == null){
            throw new FetchException("No such currency with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<Currency> allEntities() throws FetchException {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Collection<Currency> out =
                    session.createQuery("SELECT c FROM entity.Currency c", Currency.class).list();
            transaction.commit();
            return out;
        } catch (Throwable t) {
            throw new FetchException(t);
        }
    }

    @Override
    public void save(Currency currency) throws StoreException {
        Objects.requireNonNull(currency);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(currency);
            transaction.commit();
        } catch (Throwable t) {
            throw new StoreException(t);
        }
    }

    @Override
    public void update(Integer id, Currency currency) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(currency);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> q = session.createQuery("UPDATE entity.Currency SET currencyName = :currencyName WHERE id = :id");
            q.setParameter("currencyName", currency.getCurrencyName());
            q.setParameter("id", id);
            q.executeUpdate();
            transaction.commit();
        } catch (Throwable t) {
            throw new UpdateException(t);
        }
    }

    @Override
    public void delete(Integer id) throws DeleteException {
        Objects.requireNonNull(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> q = session.createQuery("DELETE FROM entity.Currency WHERE id = :id");
            q.setParameter("id", id);
            q.executeUpdate();
            transaction.commit();
        } catch (Throwable t) {
            throw new DeleteException(t);
        }
    }

    @Override
    public void insertIgnore(Currency currency) throws StoreException {
        Objects.requireNonNull(currency);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> q = session.createSQLQuery("INSERT IGNORE INTO is_currency(currency_id, currency_name) VALUES (?, ?)");
            q.setParameter(1, currency.getId());
            q.setParameter(2, currency.getCurrencyName());
            q.executeUpdate();
            transaction.commit();
        } catch (Throwable t) {
            throw new StoreException(t);
        }
    }

    @Override
    public Currency getByName(String name) throws FetchException {
        Objects.requireNonNull(name);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> q = session.createQuery("SELECT c FROM entity.Currency c WHERE c.currencyName = :currencyName");
            q.setParameter("currencyName", name);
            Object out = q.uniqueResult();
            transaction.commit();
            if (out != null) {
                return (Currency) out;
            }
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        throw new FetchException("No such currency: " + name);
    }
}

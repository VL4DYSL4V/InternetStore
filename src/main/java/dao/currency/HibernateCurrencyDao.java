package dao.currency;

import entity.Currency;
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
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        if (out == null) {
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
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
    }

    @Override
    public void save(Currency currency) throws StoreException {
        Objects.requireNonNull(currency);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(currency);
            transaction.commit();
        } catch (HibernateException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void saveIgnoreId(Currency currency) throws StoreException {
        Objects.requireNonNull(currency);
        try(Session session = HibernateUtils.openSession()){
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createSQLQuery("INSERT INTO is_currency (currency_name) VALUES (?);");
            query.setParameter(1, currency.getCurrencyName());
            transaction.commit();
        }catch (HibernateException e){
            throw new StoreException(e);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void update(Integer id, Currency currency) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(currency);
        Integer prevId = currency.getId();
        currency.setId(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(currency);
            transaction.commit();
        } catch (HibernateException e) {
            throw new UpdateException(e);
        }finally {
            currency.setId(prevId);
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
        } catch (HibernateException e) {
            throw new DeleteException(e);
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
        } catch (HibernateException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public Currency getByName(String name) throws FetchException {
        Objects.requireNonNull(name);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Currency> q = session.createQuery("SELECT c FROM entity.Currency c WHERE c.currencyName = :currencyName", Currency.class);
            q.setParameter("currencyName", name);
            Currency out = q.uniqueResult();
            transaction.commit();
            if (out != null) {
                return out;
            }
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        throw new FetchException("No such currency: " + name);
    }

    @Override
    public void deleteByName(String name) throws DeleteException {
        Objects.requireNonNull(name);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM entity.Currency c WHERE c.currencyName = :currencyName");
            query.setParameter("currencyName", name);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new DeleteException(e);
        }
    }
}

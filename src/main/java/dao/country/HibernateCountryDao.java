package dao.country;

import entity.Country;
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

@Repository("hibernateCountryDao")
@NotThreadSafe
public final class HibernateCountryDao implements CountryDao {

    @Override
    public Country getByName(String countryName) throws FetchException {
        Country out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Country> query = session.createQuery("SELECT c FROM entity.Country c WHERE c.countryName = :name", Country.class);
            query.setParameter("name", countryName);
            out = query.uniqueResult();
            transaction.commit();
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        if (out == null) {
            throw new FetchException("No such country with name = " + countryName);
        }
        return out;
    }

    @Override
    public Country getById(Integer id) throws FetchException {
        Country out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(Country.class, id);
            transaction.commit();
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        if (out == null) {
            throw new FetchException("No such country with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<Country> allEntities() throws FetchException {
        Collection<Country> out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.createQuery("SELECT c FROM entity.Country c", Country.class).list();
            transaction.commit();
        } catch (Throwable t) {
            throw new FetchException(t);
        }
        return out;
    }

    @Override
    public void save(Country country) throws StoreException {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(country);
            transaction.commit();
        } catch (Throwable t) {
            throw new StoreException(t);
        }
    }

    @Override
    public void update(Integer id, Country country) throws UpdateException {
        country.setId(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(country);
            transaction.commit();
        } catch (Throwable t) {
            throw new UpdateException(t);
        }
    }

    @Override
    public void delete(Integer id) throws DeleteException {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM entity.Country c WHERE c.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Throwable t) {
            throw new DeleteException(t);
        }
    }
}

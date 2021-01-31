package dao.bankAccount;

import entity.BankAccount;
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

@Repository("hibernateBankAccountDao")
@NotThreadSafe
public final class HibernateBankAccountDao implements BankAccountDao {

    @Override
    public BankAccount getById(Long id) throws FetchException {
        Objects.requireNonNull(id);
        BankAccount out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.get(BankAccount.class, id);
            transaction.commit();
        } catch (Throwable throwable) {
            throw new FetchException(throwable);
        }
        if (out == null) {
            throw new FetchException("No bank account with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<BankAccount> allEntities() throws FetchException {
        Collection<BankAccount> out;
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            out = session.createQuery("SELECT ba FROM entity.BankAccount ba", BankAccount.class).list();
            transaction.commit();
        } catch (HibernateException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(BankAccount bankAccount) throws StoreException {
        Objects.requireNonNull(bankAccount);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(bankAccount);
            transaction.commit();
        } catch (HibernateException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void saveIgnoreId(BankAccount bankAccount) throws StoreException {
        Objects.requireNonNull(bankAccount);
        Objects.requireNonNull(bankAccount.getCurrency());
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createSQLQuery("INSERT INTO is_bank_account(funds, currency_id)" +
                    " VALUES (?, (SELECT currency_id FROM is_currency WHERE BINARY currency_name = ?))");
            query.setParameter(1, bankAccount.getFunds());
            query.setParameter(2, bankAccount.getCurrency().getCurrencyName());
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Long id, BankAccount bankAccount) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(bankAccount);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createSQLQuery("UPDATE is_bank_account SET funds = ? WHERE bank_account_id = ?;");
            query.setParameter(1, bankAccount.getFunds());
            query.setParameter(2, id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM entity.BankAccount ba WHERE ba.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new DeleteException(e);
        }
    }
}
